package org.chagolchana.noconnect.api.sharing.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.sharing.InvitationResponse;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public abstract class InvitationResponseReceivedEvent extends Event {

	private final ContactId contactId;
	private final InvitationResponse response;

	public InvitationResponseReceivedEvent(ContactId contactId,
			InvitationResponse response) {
		this.contactId = contactId;
		this.response = response;
	}

	public ContactId getContactId() {
		return contactId;
	}

	public InvitationResponse getResponse() {
		return response;
	}
}
