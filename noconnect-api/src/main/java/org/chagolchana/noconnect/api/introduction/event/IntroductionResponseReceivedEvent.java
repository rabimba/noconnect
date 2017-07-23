package org.chagolchana.noconnect.api.introduction.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.introduction.IntroductionResponse;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionResponseReceivedEvent extends Event {

	private final ContactId contactId;
	private final IntroductionResponse introductionResponse;

	public IntroductionResponseReceivedEvent(ContactId contactId,
			IntroductionResponse introductionResponse) {

		this.contactId = contactId;
		this.introductionResponse = introductionResponse;
	}

	public ContactId getContactId() {
		return contactId;
	}

	public IntroductionResponse getIntroductionResponse() {
		return introductionResponse;
	}
}
