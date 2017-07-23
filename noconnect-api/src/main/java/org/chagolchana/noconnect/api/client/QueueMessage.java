package org.chagolchana.noconnect.api.client;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;

import static org.chagolchana.chagol.api.sync.SyncConstants.MAX_MESSAGE_BODY_LENGTH;
import static org.chagolchana.chagol.api.sync.SyncConstants.MESSAGE_HEADER_LENGTH;

@Deprecated
@NotNullByDefault
public class QueueMessage extends Message {

	public static final int QUEUE_MESSAGE_HEADER_LENGTH =
			MESSAGE_HEADER_LENGTH + 8;
	public static final int MAX_QUEUE_MESSAGE_BODY_LENGTH =
			MAX_MESSAGE_BODY_LENGTH - 8;

	private final long queuePosition;

	public QueueMessage(MessageId id, GroupId groupId, long timestamp,
			long queuePosition, byte[] raw) {
		super(id, groupId, timestamp, raw);
		this.queuePosition = queuePosition;
	}

	public long getQueuePosition() {
		return queuePosition;
	}
}
