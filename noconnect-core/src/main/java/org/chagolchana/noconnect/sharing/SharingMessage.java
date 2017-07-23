package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
abstract class SharingMessage {

	private final MessageId id;
	private final GroupId contactGroupId, shareableId;
	private final long timestamp;
	@Nullable
	private final MessageId previousMessageId;

	SharingMessage(MessageId id, GroupId contactGroupId, GroupId shareableId,
			long timestamp, @Nullable MessageId previousMessageId) {
		this.id = id;
		this.previousMessageId = previousMessageId;
		this.contactGroupId = contactGroupId;
		this.shareableId = shareableId;
		this.timestamp = timestamp;
	}

	MessageId getId() {
		return id;
	}

	GroupId getContactGroupId() {
		return contactGroupId;
	}

	GroupId getShareableId() {
		return shareableId;
	}

	long getTimestamp() {
		return timestamp;
	}

	@Nullable
	public MessageId getPreviousMessageId() {
		return previousMessageId;
	}

}
