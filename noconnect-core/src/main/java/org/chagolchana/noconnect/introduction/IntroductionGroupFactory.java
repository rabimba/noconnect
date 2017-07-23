package org.chagolchana.noconnect.introduction;

import org.chagolchana.chagol.api.client.ContactGroupFactory;
import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.sync.Group;

import javax.inject.Inject;

import static org.chagolchana.noconnect.api.introduction.IntroductionManager.CLIENT_ID;

class IntroductionGroupFactory {

	private final ContactGroupFactory contactGroupFactory;
	private final Group localGroup;

	@Inject
	IntroductionGroupFactory(ContactGroupFactory contactGroupFactory) {
		this.contactGroupFactory = contactGroupFactory;
		localGroup = contactGroupFactory.createLocalGroup(CLIENT_ID);
	}

	Group createIntroductionGroup(Contact c) {
		return contactGroupFactory.createContactGroup(CLIENT_ID, c);
	}

	public Group createLocalGroup() {
		return localGroup;
	}

}
