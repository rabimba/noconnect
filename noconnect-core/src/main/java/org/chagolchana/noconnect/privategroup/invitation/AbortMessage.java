package org.chagolchana.noconnect.privategroup.invitation;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
class AbortMessage extends GroupInvitationMessage {

	AbortMessage(MessageId id, GroupId contactGroupId, GroupId privateGroupId,
			long timestamp) {
		super(id, contactGroupId, privateGroupId, timestamp);
	}
}
