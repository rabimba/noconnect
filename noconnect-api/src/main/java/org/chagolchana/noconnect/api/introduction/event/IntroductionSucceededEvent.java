package org.chagolchana.noconnect.api.introduction.event;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionSucceededEvent extends Event {

	private final Contact contact;

	public IntroductionSucceededEvent(Contact contact) {
		this.contact = contact;
	}

	public Contact getContact() {
		return contact;
	}
}
