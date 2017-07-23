package org.chagolchana.noconnect.android.contact;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.contact.ConversationRequestItem.RequestType;
import org.chagolchana.noconnect.api.blog.BlogInvitationRequest;
import org.chagolchana.noconnect.api.blog.BlogInvitationResponse;
import org.chagolchana.noconnect.api.client.BaseMessageHeader;
import org.chagolchana.noconnect.api.forum.ForumInvitationRequest;
import org.chagolchana.noconnect.api.forum.ForumInvitationResponse;
import org.chagolchana.noconnect.api.introduction.IntroductionRequest;
import org.chagolchana.noconnect.api.introduction.IntroductionResponse;
import org.chagolchana.noconnect.api.messaging.PrivateMessageHeader;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationRequest;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationResponse;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;
import org.chagolchana.noconnect.api.sharing.InvitationResponse;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import static org.chagolchana.noconnect.android.contact.ConversationRequestItem.RequestType.BLOG;
import static org.chagolchana.noconnect.android.contact.ConversationRequestItem.RequestType.FORUM;
import static org.chagolchana.noconnect.android.contact.ConversationRequestItem.RequestType.GROUP;
import static org.chagolchana.noconnect.android.contact.ConversationRequestItem.RequestType.INTRODUCTION;

@NotThreadSafe
@NotNullByDefault
abstract class ConversationItem {

	protected @Nullable String body;
	private final MessageId id;
	private final GroupId groupId;
	private final long time;
	private boolean read;

	ConversationItem(MessageId id, GroupId groupId, @Nullable String body,
			long time, boolean read) {
		this.id = id;
		this.groupId = groupId;
		this.body = body;
		this.time = time;
		this.read = read;
	}

	MessageId getId() {
		return id;
	}

	GroupId getGroupId() {
		return groupId;
	}

	void setBody(String body) {
		this.body = body;
	}

	@Nullable
	public String getBody() {
		return body;
	}

	long getTime() {
		return time;
	}

	public boolean isRead() {
		return read;
	}

	abstract public boolean isIncoming();

	@LayoutRes
	abstract public int getLayout();

	static ConversationItem from(PrivateMessageHeader h) {
		if (h.isLocal()) {
			return new ConversationMessageOutItem(h);
		} else {
			return new ConversationMessageInItem(h);
		}
	}

	static ConversationItem from(Context ctx, String contactName,
			IntroductionRequest ir) {
		if (ir.isLocal()) {
			String text = ctx.getString(R.string.introduction_request_sent,
					contactName, ir.getName());
			return new ConversationNoticeOutItem(ir.getMessageId(),
					ir.getGroupId(), text, ir.getMessage(), ir.getTimestamp(),
					ir.isSent(), ir.isSeen());
		} else {
			String text;
			if (ir.wasAnswered()) {
				text = ctx.getString(
						R.string.introduction_request_answered_received,
						contactName, ir.getName());
			} else if (ir.contactExists()){
				text = ctx.getString(
						R.string.introduction_request_exists_received,
						contactName, ir.getName());
			} else {
				text = ctx.getString(R.string.introduction_request_received,
						contactName, ir.getName());
			}
			return new ConversationRequestItem(ir.getMessageId(),
					ir.getGroupId(), INTRODUCTION, ir.getSessionId(), text,
					ir.getMessage(), ir.getTimestamp(), ir.isRead(), null,
					ir.wasAnswered(), false);
		}
	}

	static ConversationItem from(Context ctx, String contactName,
			IntroductionResponse ir) {
		if (ir.isLocal()) {
			String text;
			if (ir.wasAccepted()) {
				text = ctx.getString(
						R.string.introduction_response_accepted_sent,
						ir.getName());
			} else {
				text = ctx.getString(
						R.string.introduction_response_declined_sent,
						ir.getName());
			}
			return new ConversationNoticeOutItem(ir.getMessageId(),
					ir.getGroupId(), text, null, ir.getTimestamp(), ir.isSent(),
					ir.isSeen());
		} else {
			String text;
			if (ir.wasAccepted()) {
				text = ctx.getString(
						R.string.introduction_response_accepted_received,
						contactName, ir.getName());
			} else {
				if (ir.isIntroducer()) {
					text = ctx.getString(
							R.string.introduction_response_declined_received,
							contactName, ir.getName());
				} else {
					text = ctx.getString(
							R.string.introduction_response_declined_received_by_introducee,
							contactName, ir.getName());
				}
			}
			return new ConversationNoticeInItem(ir.getMessageId(),
					ir.getGroupId(), text, null, ir.getTimestamp(),
					ir.isRead());
		}
	}

	static ConversationItem from(Context ctx, String contactName,
			InvitationRequest ir) {
		if (ir.isLocal()) {
			String text;
			if (ir instanceof ForumInvitationRequest) {
				text = ctx.getString(R.string.forum_invitation_sent,
						((ForumInvitationRequest) ir).getForumName(),
						contactName);
			} else if (ir instanceof BlogInvitationRequest) {
				text = ctx.getString(R.string.blogs_sharing_invitation_sent,
						((BlogInvitationRequest) ir).getBlogAuthorName(),
						contactName);
			} else if (ir instanceof GroupInvitationRequest) {
				text = ctx.getString(
						R.string.groups_invitations_invitation_sent,
						contactName, ir.getShareable().getName());
			} else {
				throw new IllegalArgumentException("Unknown InvitationRequest");
			}
			return new ConversationNoticeOutItem(ir.getId(), ir.getGroupId(),
					text, ir.getMessage(), ir.getTimestamp(), ir.isSent(),
					ir.isSeen());
		} else {
			String text;
			RequestType type;
			if (ir instanceof ForumInvitationRequest) {
				text = ctx.getString(R.string.forum_invitation_received,
						contactName,
						((ForumInvitationRequest) ir).getForumName());
				type = FORUM;
			} else if (ir instanceof BlogInvitationRequest) {
				text = ctx.getString(R.string.blogs_sharing_invitation_received,
						contactName,
						((BlogInvitationRequest) ir).getBlogAuthorName());
				type = BLOG;
			} else if (ir instanceof GroupInvitationRequest) {
				text = ctx.getString(
						R.string.groups_invitations_invitation_received,
						contactName, ir.getShareable().getName());
				type = GROUP;
			} else {
				throw new IllegalArgumentException("Unknown InvitationRequest");
			}
			return new ConversationRequestItem(ir.getId(),
					ir.getGroupId(), type, ir.getSessionId(), text,
					ir.getMessage(), ir.getTimestamp(), ir.isRead(),
					ir.getShareable().getId(), !ir.isAvailable(),
					ir.canBeOpened());
		}
	}

	static ConversationItem from(Context ctx, String contactName,
			InvitationResponse ir) {
		@StringRes int res;
		if (ir.isLocal()) {
			if (ir.wasAccepted()) {
				if (ir instanceof ForumInvitationResponse) {
					res = R.string.forum_invitation_response_accepted_sent;
				} else if (ir instanceof BlogInvitationResponse) {
					res = R.string.blogs_sharing_response_accepted_sent;
				} else if (ir instanceof GroupInvitationResponse) {
					res = R.string.groups_invitations_response_accepted_sent;
				} else {
					throw new IllegalArgumentException(
							"Unknown InvitationResponse");
				}
			} else {
				if (ir instanceof ForumInvitationResponse) {
					res = R.string.forum_invitation_response_declined_sent;
				} else if (ir instanceof BlogInvitationResponse) {
					res = R.string.blogs_sharing_response_declined_sent;
				} else if (ir instanceof GroupInvitationResponse) {
					res = R.string.groups_invitations_response_declined_sent;
				} else {
					throw new IllegalArgumentException(
							"Unknown InvitationResponse");
				}
			}
			String text = ctx.getString(res, contactName);
			return new ConversationNoticeOutItem(ir.getId(), ir.getGroupId(),
					text, null, ir.getTimestamp(), ir.isSent(), ir.isSeen());
		} else {
			if (ir.wasAccepted()) {
				if (ir instanceof ForumInvitationResponse) {
					res = R.string.forum_invitation_response_accepted_received;
				} else if (ir instanceof BlogInvitationResponse) {
					res = R.string.blogs_sharing_response_accepted_received;
				} else if (ir instanceof GroupInvitationResponse) {
					res = R.string.groups_invitations_response_accepted_received;
				} else {
					throw new IllegalArgumentException(
							"Unknown InvitationResponse");
				}
			} else {
					if (ir instanceof ForumInvitationResponse) {
						res = R.string.forum_invitation_response_declined_received;
					} else if (ir instanceof BlogInvitationResponse) {
						res = R.string.blogs_sharing_response_declined_received;
					} else if (ir instanceof GroupInvitationResponse) {
						res = R.string.groups_invitations_response_declined_received;
					} else {
						throw new IllegalArgumentException(
								"Unknown InvitationResponse");
					}
			}
			String text = ctx.getString(res, contactName);
			return new ConversationNoticeInItem(ir.getId(), ir.getGroupId(),
					text, null, ir.getTimestamp(), ir.isRead());
		}
	}

	/**
	 * This method should not be used to display the resulting ConversationItem
	 * in the UI, but only to update list information based on the
	 * BaseMessageHeader.
	 **/
	static ConversationItem from(Context ctx, BaseMessageHeader h) {
		if (h instanceof PrivateMessageHeader) {
			return from((PrivateMessageHeader) h);
		} else if(h instanceof IntroductionRequest) {
			return from(ctx, "", (IntroductionRequest) h);
		} else if(h instanceof IntroductionResponse) {
			return from(ctx, "", (IntroductionResponse) h);
		} else if(h instanceof InvitationRequest) {
			return from(ctx, "", (InvitationRequest) h);
		} else if(h instanceof InvitationResponse) {
			return from(ctx, "", (InvitationResponse) h);
		} else {
			throw new IllegalArgumentException("Unknown message header");
		}
	}

}
