package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;
import org.chagolchana.noconnect.api.sharing.InvitationResponse;
import org.chagolchana.noconnect.api.sharing.Shareable;

public interface InvitationFactory<S extends Shareable, I extends InvitationResponse> {

	InvitationRequest<S> createInvitationRequest(boolean local, boolean sent,
			boolean seen, boolean read, InviteMessage<S> m, ContactId c,
			boolean available, boolean canBeOpened);

	I createInvitationResponse(MessageId id,
			GroupId contactGroupId, long time, boolean local, boolean sent,
			boolean seen, boolean read, GroupId shareableId,
			ContactId contactId, boolean accept);

}
