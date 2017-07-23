package org.chagolchana.noconnect.api.sharing;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.BaseMessageHeader;
import org.chagolchana.noconnect.api.client.SessionId;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class InvitationMessage extends BaseMessageHeader {

	private final SessionId sessionId;
	private final ContactId contactId;

	public InvitationMessage(MessageId id, GroupId groupId, long time,
			boolean local, boolean sent, boolean seen, boolean read,
			SessionId sessionId, ContactId contactId) {

		super(id, groupId, time, local, sent, seen, read);
		this.sessionId = sessionId;
		this.contactId = contactId;
	}

	public SessionId getSessionId() {
		return sessionId;
	}

	public ContactId getContactId() {
		return contactId;
	}

}
