package org.chagolchana.noconnect.api.privategroup.invitation;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.SessionId;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class GroupInvitationRequest extends InvitationRequest<PrivateGroup> {

	public GroupInvitationRequest(MessageId id, GroupId groupId, long time,
			boolean local, boolean sent, boolean seen, boolean read,
			SessionId sessionId, PrivateGroup shareable, ContactId contactId,
			@Nullable String message, boolean available, boolean canBeOpened) {
		super(id, groupId, time, local, sent, seen, read, sessionId, shareable,
				contactId, message, available, canBeOpened);
	}

}
