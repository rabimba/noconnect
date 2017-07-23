package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.blog.Blog;
import org.chagolchana.noconnect.api.blog.BlogInvitationRequest;
import org.chagolchana.noconnect.api.blog.BlogInvitationResponse;
import org.chagolchana.noconnect.api.client.SessionId;

import javax.inject.Inject;

public class BlogInvitationFactoryImpl
		implements InvitationFactory<Blog, BlogInvitationResponse> {

	@Inject
	BlogInvitationFactoryImpl() {
	}

	@Override
	public BlogInvitationRequest createInvitationRequest(boolean local,
			boolean sent, boolean seen, boolean read, InviteMessage<Blog> m,
			ContactId c, boolean available, boolean canBeOpened) {
		SessionId sessionId = new SessionId(m.getShareableId().getBytes());
		return new BlogInvitationRequest(m.getId(), m.getContactGroupId(),
				m.getTimestamp(), local, sent, seen, read, sessionId,
				m.getShareable(), c, m.getMessage(), available, canBeOpened);
	}

	@Override
	public BlogInvitationResponse createInvitationResponse(MessageId id,
			GroupId contactGroupId, long time, boolean local, boolean sent,
			boolean seen, boolean read, GroupId shareableId,
			ContactId contactId, boolean accept) {
		SessionId sessionId = new SessionId(shareableId.getBytes());
		return new BlogInvitationResponse(id, contactGroupId, time, local,
				sent, seen, read, sessionId, shareableId, contactId, accept);
	}

}
