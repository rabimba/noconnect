package org.chagolchana.noconnect.api.privategroup.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationRequest;
import org.chagolchana.noconnect.api.sharing.event.InvitationRequestReceivedEvent;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class GroupInvitationRequestReceivedEvent extends
		InvitationRequestReceivedEvent<PrivateGroup> {

	public GroupInvitationRequestReceivedEvent(PrivateGroup group,
			ContactId contactId, GroupInvitationRequest request) {
		super(group, contactId, request);
	}

}
