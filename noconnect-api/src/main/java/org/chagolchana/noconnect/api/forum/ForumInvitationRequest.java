package org.chagolchana.noconnect.api.forum;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.SessionId;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ForumInvitationRequest extends InvitationRequest<Forum> {

	public ForumInvitationRequest(MessageId id, GroupId groupId, long time,
			boolean local, boolean sent, boolean seen, boolean read,
			SessionId sessionId, Forum forum, ContactId contactId,
			@Nullable String message, boolean available, boolean canBeOpened) {
		super(id, groupId, time, local, sent, seen, read, sessionId, forum,
				contactId, message, available, canBeOpened);
	}

	public String getForumName() {
		return getShareable().getName();
	}

}
