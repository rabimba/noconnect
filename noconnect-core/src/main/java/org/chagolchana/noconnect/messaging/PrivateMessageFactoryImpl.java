package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.noconnect.api.messaging.PrivateMessage;
import org.chagolchana.noconnect.api.messaging.PrivateMessageFactory;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static org.chagolchana.chagol.util.StringUtils.utf8IsTooLong;
import static org.chagolchana.noconnect.api.messaging.MessagingConstants.MAX_PRIVATE_MESSAGE_BODY_LENGTH;

@Immutable
@NotNullByDefault
class PrivateMessageFactoryImpl implements PrivateMessageFactory {

	private final ClientHelper clientHelper;

	@Inject
	PrivateMessageFactoryImpl(ClientHelper clientHelper) {
		this.clientHelper = clientHelper;
	}

	@Override
	public PrivateMessage createPrivateMessage(GroupId groupId, long timestamp,
			String body) throws FormatException {
		// Validate the arguments
		if (utf8IsTooLong(body, MAX_PRIVATE_MESSAGE_BODY_LENGTH))
			throw new IllegalArgumentException();
		// Serialise the message
		BdfList message = BdfList.of(body);
		Message m = clientHelper.createMessage(groupId, timestamp, message);
		return new PrivateMessage(m);
	}
}
