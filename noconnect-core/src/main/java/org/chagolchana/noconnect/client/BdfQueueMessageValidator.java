package org.chagolchana.noconnect.client;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.BdfMessageContext;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.db.Metadata;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.InvalidMessageException;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageContext;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.client.MessageQueueManager.QueueMessageValidator;
import org.chagolchana.noconnect.api.client.QueueMessage;

import java.util.logging.Logger;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.transport.TransportConstants.MAX_CLOCK_DIFFERENCE;
import static org.chagolchana.noconnect.api.client.QueueMessage.QUEUE_MESSAGE_HEADER_LENGTH;

@Deprecated
@Immutable
@NotNullByDefault
public abstract class BdfQueueMessageValidator
		implements QueueMessageValidator {

	protected static final Logger LOG =
			Logger.getLogger(BdfQueueMessageValidator.class.getName());

	protected final ClientHelper clientHelper;
	protected final MetadataEncoder metadataEncoder;
	protected final Clock clock;

	protected BdfQueueMessageValidator(ClientHelper clientHelper,
			MetadataEncoder metadataEncoder, Clock clock) {
		this.clientHelper = clientHelper;
		this.metadataEncoder = metadataEncoder;
		this.clock = clock;
	}

	protected abstract BdfMessageContext validateMessage(Message m, Group g,
			BdfList body) throws InvalidMessageException, FormatException;

	@Override
	public MessageContext validateMessage(QueueMessage q, Group g)
			throws InvalidMessageException {
		// Reject the message if it's too far in the future
		long now = clock.currentTimeMillis();
		if (q.getTimestamp() - now > MAX_CLOCK_DIFFERENCE) {
			throw new InvalidMessageException(
					"Timestamp is too far in the future");
		}
		byte[] raw = q.getRaw();
		if (raw.length <= QUEUE_MESSAGE_HEADER_LENGTH) {
			throw new InvalidMessageException("Message is too short");
		}
		try {
			BdfList body = clientHelper.toList(raw, QUEUE_MESSAGE_HEADER_LENGTH,
					raw.length - QUEUE_MESSAGE_HEADER_LENGTH);
			BdfMessageContext result = validateMessage(q, g, body);
			Metadata meta = metadataEncoder.encode(result.getDictionary());
			return new MessageContext(meta, result.getDependencies());
		} catch (FormatException e) {
			throw new InvalidMessageException(e);
		}
	}
}
