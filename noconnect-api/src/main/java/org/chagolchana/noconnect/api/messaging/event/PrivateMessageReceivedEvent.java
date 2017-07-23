package org.chagolchana.noconnect.api.messaging.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.api.messaging.PrivateMessageHeader;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a new private message is received.
 */
@Immutable
@NotNullByDefault
public class PrivateMessageReceivedEvent extends Event {

	private final PrivateMessageHeader messageHeader;
	private final ContactId contactId;
	private final GroupId groupId;

	public PrivateMessageReceivedEvent(PrivateMessageHeader messageHeader,
			ContactId contactId, GroupId groupId) {
		this.messageHeader = messageHeader;
		this.contactId = contactId;
		this.groupId = groupId;
	}

	public PrivateMessageHeader getMessageHeader() {
		return messageHeader;
	}

	public ContactId getContactId() {
		return contactId;
	}

	public GroupId getGroupId() {
		return groupId;
	}
}
