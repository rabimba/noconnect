package org.chagolchana.noconnect.api.client;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

@Deprecated
@NotNullByDefault
public interface QueueMessageFactory {

	QueueMessage createMessage(GroupId groupId, long timestamp,
			long queuePosition, byte[] body);

	QueueMessage createMessage(MessageId id, byte[] raw);
}
