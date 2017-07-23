package org.chagolchana.chagol.api.client;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.db.Metadata;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.InvalidMessageException;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageContext;
import org.chagolchana.chagol.api.sync.ValidationManager.MessageValidator;
import org.chagolchana.chagol.api.system.Clock;

import java.util.logging.Logger;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.sync.SyncConstants.MESSAGE_HEADER_LENGTH;
import static org.chagolchana.chagol.api.transport.TransportConstants.MAX_CLOCK_DIFFERENCE;

@Immutable
@NotNullByDefault
public abstract class BdfMessageValidator implements MessageValidator {

	protected static final Logger LOG =
			Logger.getLogger(BdfMessageValidator.class.getName());

	protected final ClientHelper clientHelper;
	protected final MetadataEncoder metadataEncoder;
	protected final Clock clock;

	protected BdfMessageValidator(ClientHelper clientHelper,
			MetadataEncoder metadataEncoder, Clock clock) {
		this.clientHelper = clientHelper;
		this.metadataEncoder = metadataEncoder;
		this.clock = clock;
	}

	protected abstract BdfMessageContext validateMessage(Message m, Group g,
			BdfList body) throws InvalidMessageException, FormatException;

	@Override
	public MessageContext validateMessage(Message m, Group g)
			throws InvalidMessageException {
		// Reject the message if it's too far in the future
		long now = clock.currentTimeMillis();
		if (m.getTimestamp() - now > MAX_CLOCK_DIFFERENCE) {
			throw new InvalidMessageException(
					"Timestamp is too far in the future");
		}
		byte[] raw = m.getRaw();
		if (raw.length <= MESSAGE_HEADER_LENGTH) {
			throw new InvalidMessageException("Message is too short");
		}
		try {
			BdfList body = clientHelper.toList(raw, MESSAGE_HEADER_LENGTH,
					raw.length - MESSAGE_HEADER_LENGTH);
			BdfMessageContext result = validateMessage(m, g, body);
			Metadata meta = metadataEncoder.encode(result.getDictionary());
			return new MessageContext(meta, result.getDependencies());
		} catch (FormatException e) {
			throw new InvalidMessageException(e);
		}
	}
}
