package org.chagolchana.noconnect.api.blog.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.blog.BlogInvitationResponse;
import org.chagolchana.noconnect.api.sharing.event.InvitationResponseReceivedEvent;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class BlogInvitationResponseReceivedEvent
		extends InvitationResponseReceivedEvent {

	public BlogInvitationResponseReceivedEvent(ContactId contactId,
			BlogInvitationResponse response) {
		super(contactId, response);
	}

}
