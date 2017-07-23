package org.chagolchana.chagol.api.sync.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a message is shared.
 */
@Immutable
@NotNullByDefault
public class MessageSharedEvent extends Event {

	private final MessageId messageId;

	public MessageSharedEvent(MessageId message) {
		this.messageId = message;
	}

	public MessageId getMessageId() {
		return messageId;
	}
}
