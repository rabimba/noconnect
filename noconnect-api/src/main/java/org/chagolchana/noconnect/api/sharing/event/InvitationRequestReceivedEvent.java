package org.chagolchana.noconnect.api.sharing.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;
import org.chagolchana.noconnect.api.sharing.Shareable;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public abstract class InvitationRequestReceivedEvent<S extends Shareable>
		extends Event {

	private final S shareable;
	private final ContactId contactId;
	private final InvitationRequest request;

	protected InvitationRequestReceivedEvent(S shareable, ContactId contactId,
			InvitationRequest request) {
		this.shareable = shareable;
		this.contactId = contactId;
		this.request = request;
	}

	public ContactId getContactId() {
		return contactId;
	}

	public InvitationRequest getRequest() {
		return request;
	}

	public S getShareable() {
		return shareable;
	}
}
