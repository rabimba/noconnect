package org.chagolchana.noconnect.api.forum.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.forum.ForumInvitationResponse;
import org.chagolchana.noconnect.api.sharing.event.InvitationResponseReceivedEvent;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ForumInvitationResponseReceivedEvent extends
		InvitationResponseReceivedEvent {

	public ForumInvitationResponseReceivedEvent(ContactId contactId,
			ForumInvitationResponse response) {
		super(contactId, response);
	}

}
