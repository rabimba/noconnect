package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.blog.Blog;
import org.chagolchana.noconnect.api.blog.BlogInvitationResponse;
import org.chagolchana.noconnect.api.blog.BlogManager;
import org.chagolchana.noconnect.api.blog.event.BlogInvitationRequestReceivedEvent;
import org.chagolchana.noconnect.api.blog.event.BlogInvitationResponseReceivedEvent;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class BlogProtocolEngineImpl extends ProtocolEngineImpl<Blog> {

	private final BlogManager blogManager;
	private final InvitationFactory<Blog, BlogInvitationResponse>
			invitationFactory;

	@Inject
	BlogProtocolEngineImpl(DatabaseComponent db,
			ClientHelper clientHelper, MessageEncoder messageEncoder,
			MessageParser<Blog> messageParser, MessageTracker messageTracker,
			Clock clock, BlogManager blogManager,
			InvitationFactory<Blog, BlogInvitationResponse> invitationFactory) {
		super(db, clientHelper, messageEncoder, messageParser, messageTracker,
				clock);
		this.blogManager = blogManager;
		this.invitationFactory = invitationFactory;
	}

	@Override
	Event getInvitationRequestReceivedEvent(InviteMessage<Blog> m,
			ContactId contactId, boolean available, boolean canBeOpened) {
		InvitationRequest<Blog> request = invitationFactory
						.createInvitationRequest(false, false, true, false, m,
								contactId, available, canBeOpened);
		return new BlogInvitationRequestReceivedEvent(m.getShareable(),
				contactId, request);
	}

	@Override
	Event getInvitationResponseReceivedEvent(AcceptMessage m,
			ContactId contactId) {
		BlogInvitationResponse response = invitationFactory
				.createInvitationResponse(m.getId(), m.getContactGroupId(),
						m.getTimestamp(), false, false, true, false,
						m.getShareableId(), contactId, true);
		return new BlogInvitationResponseReceivedEvent(contactId, response);
	}

	@Override
	Event getInvitationResponseReceivedEvent(DeclineMessage m,
			ContactId contactId) {
		BlogInvitationResponse response = invitationFactory
				.createInvitationResponse(m.getId(), m.getContactGroupId(),
						m.getTimestamp(), false, false, true, false,
						m.getShareableId(), contactId, true);
		return new BlogInvitationResponseReceivedEvent(contactId, response);
	}

	@Override
	protected ClientId getShareableClientId() {
		return BlogManager.CLIENT_ID;
	}

	@Override
	protected void addShareable(Transaction txn, MessageId inviteId)
			throws DbException, FormatException {
		InviteMessage<Blog> invite =
				messageParser.getInviteMessage(txn, inviteId);
		blogManager.addBlog(txn, invite.getShareable());
	}

}
