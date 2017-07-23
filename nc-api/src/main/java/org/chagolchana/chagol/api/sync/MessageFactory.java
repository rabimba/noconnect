package org.chagolchana.chagol.api.sync;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface MessageFactory {

	Message createMessage(GroupId g, long timestamp, byte[] body);

	Message createMessage(MessageId m, byte[] raw);
}
