package org.chagolchana.noconnect.api.introduction.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.introduction.IntroductionRequest;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionRequestReceivedEvent extends Event {

	private final ContactId contactId;
	private final IntroductionRequest introductionRequest;

	public IntroductionRequestReceivedEvent(ContactId contactId,
			IntroductionRequest introductionRequest) {

		this.contactId = contactId;
		this.introductionRequest = introductionRequest;
	}

	public ContactId getContactId() {
		return contactId;
	}

	public IntroductionRequest getIntroductionRequest() {
		return introductionRequest;
	}

}
