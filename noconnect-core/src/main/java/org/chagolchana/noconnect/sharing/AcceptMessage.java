package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
class AcceptMessage extends SharingMessage {

	AcceptMessage(MessageId id, @Nullable MessageId previousMessageId,
			GroupId contactGroupId, GroupId shareableId, long timestamp) {
		super(id, contactGroupId, shareableId, timestamp, previousMessageId);
	}

}
