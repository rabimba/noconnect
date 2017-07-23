package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.BdfMessageContext;
import org.chagolchana.chagol.api.client.BdfMessageValidator;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.system.Clock;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.util.ValidationUtils.checkLength;
import static org.chagolchana.chagol.util.ValidationUtils.checkSize;
import static org.chagolchana.noconnect.api.messaging.MessagingConstants.MAX_PRIVATE_MESSAGE_BODY_LENGTH;
import static org.chagolchana.noconnect.client.MessageTrackerConstants.MSG_KEY_READ;

@Immutable
@NotNullByDefault
class PrivateMessageValidator extends BdfMessageValidator {

	PrivateMessageValidator(ClientHelper clientHelper,
			MetadataEncoder metadataEncoder, Clock clock) {
		super(clientHelper, metadataEncoder, clock);
	}

	@Override
	protected BdfMessageContext validateMessage(Message m, Group g,
			BdfList body) throws FormatException {
		// private message body
		checkSize(body, 1);
		// Private message body
		String privateMessageBody = body.getString(0);
		checkLength(privateMessageBody, 0, MAX_PRIVATE_MESSAGE_BODY_LENGTH);
		// Return the metadata
		BdfDictionary meta = new BdfDictionary();
		meta.put("timestamp", m.getTimestamp());
		meta.put("local", false);
		meta.put(MSG_KEY_READ, false);
		return new BdfMessageContext(meta);
	}
}
