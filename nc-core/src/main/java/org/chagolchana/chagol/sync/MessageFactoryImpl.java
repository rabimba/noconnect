package org.chagolchana.chagol.sync;

import org.chagolchana.chagol.api.UniqueId;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageFactory;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.util.ByteUtils;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static org.chagolchana.chagol.api.sync.SyncConstants.MAX_MESSAGE_BODY_LENGTH;
import static org.chagolchana.chagol.api.sync.SyncConstants.MESSAGE_HEADER_LENGTH;

@Immutable
@NotNullByDefault
class MessageFactoryImpl implements MessageFactory {

	private final CryptoComponent crypto;

	@Inject
	MessageFactoryImpl(CryptoComponent crypto) {
		this.crypto = crypto;
	}

	@Override
	public Message createMessage(GroupId g, long timestamp, byte[] body) {
		if (body.length > MAX_MESSAGE_BODY_LENGTH)
			throw new IllegalArgumentException();
		byte[] timeBytes = new byte[ByteUtils.INT_64_BYTES];
		ByteUtils.writeUint64(timestamp, timeBytes, 0);
		byte[] idHash =
				crypto.hash(MessageId.LABEL, g.getBytes(), timeBytes, body);
		MessageId id = new MessageId(idHash);
		byte[] raw = new byte[MESSAGE_HEADER_LENGTH + body.length];
		System.arraycopy(g.getBytes(), 0, raw, 0, UniqueId.LENGTH);
		ByteUtils.writeUint64(timestamp, raw, UniqueId.LENGTH);
		System.arraycopy(body, 0, raw, MESSAGE_HEADER_LENGTH, body.length);
		return new Message(id, g, timestamp, raw);
	}

	@Override
	public Message createMessage(MessageId m, byte[] raw) {
		if (raw.length < MESSAGE_HEADER_LENGTH)
			throw new IllegalArgumentException();
		byte[] groupId = new byte[UniqueId.LENGTH];
		System.arraycopy(raw, 0, groupId, 0, UniqueId.LENGTH);
		long timestamp = ByteUtils.readUint64(raw, UniqueId.LENGTH);
		return new Message(m, new GroupId(groupId), timestamp, raw);
	}
}
