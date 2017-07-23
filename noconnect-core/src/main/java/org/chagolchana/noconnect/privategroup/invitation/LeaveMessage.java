package org.chagolchana.noconnect.privategroup.invitation;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
class LeaveMessage extends GroupInvitationMessage {

	@Nullable
	private final MessageId previousMessageId;

	LeaveMessage(MessageId id, GroupId contactGroupId, GroupId privateGroupId,
			long timestamp, @Nullable MessageId previousMessageId) {
		super(id, contactGroupId, privateGroupId, timestamp);
		this.previousMessageId = previousMessageId;
	}

	@Nullable
	MessageId getPreviousMessageId() {
		return previousMessageId;
	}
}
