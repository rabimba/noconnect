package org.chagolchana.noconnect.api.privategroup.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.sharing.InvitationResponse;
import org.chagolchana.noconnect.api.sharing.event.InvitationResponseReceivedEvent;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class GroupInvitationResponseReceivedEvent
		extends InvitationResponseReceivedEvent {

	public GroupInvitationResponseReceivedEvent(ContactId contactId,
			InvitationResponse response) {
		super(contactId, response);
	}
}
