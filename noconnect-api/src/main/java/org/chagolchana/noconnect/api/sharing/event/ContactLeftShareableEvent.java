package org.chagolchana.noconnect.api.sharing.event;


import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ContactLeftShareableEvent extends Event {

	private final GroupId groupId;
	private final ContactId contactId;

	public ContactLeftShareableEvent(GroupId groupId, ContactId contactId) {
		this.groupId = groupId;
		this.contactId = contactId;
	}

	public GroupId getGroupId() {
		return groupId;
	}

	public ContactId getContactId() {
		return contactId;
	}

}
