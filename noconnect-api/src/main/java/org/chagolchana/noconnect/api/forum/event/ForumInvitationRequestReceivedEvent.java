package org.chagolchana.noconnect.api.forum.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.forum.Forum;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;
import org.chagolchana.noconnect.api.sharing.event.InvitationRequestReceivedEvent;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ForumInvitationRequestReceivedEvent extends
		InvitationRequestReceivedEvent<Forum> {

	public ForumInvitationRequestReceivedEvent(Forum forum, ContactId contactId,
			InvitationRequest<Forum> request) {
		super(forum, contactId, request);
	}

}
