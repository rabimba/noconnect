package org.chagolchana.noconnect.api.messaging;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;

@NotNullByDefault
public interface PrivateMessageFactory {

	PrivateMessage createPrivateMessage(GroupId groupId, long timestamp,
			String body) throws FormatException;

}
