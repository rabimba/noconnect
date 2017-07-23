package org.chagolchana.noconnect.api.introduction.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.client.SessionId;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionAbortedEvent extends Event {

	private final ContactId contactId;
	private final SessionId sessionId;

	public IntroductionAbortedEvent(ContactId contactId, SessionId sessionId) {
		this.contactId = contactId;
		this.sessionId = sessionId;
	}

	public ContactId getContactId() {
		return contactId;
	}

	public SessionId getSessionId() {
		return sessionId;
	}
}
