package org.chagolchana.chagol.api.sync.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.sync.ValidationManager.State;

/**
 * An event that is broadcast when a message state changed.
 */
@Immutable
@NotNullByDefault
public class MessageStateChangedEvent extends Event {

	private final MessageId messageId;
	private final boolean local;
	private final State state;

	public MessageStateChangedEvent(MessageId messageId, boolean local,
			State state) {
		this.messageId = messageId;
		this.local = local;
		this.state = state;
	}

	public MessageId getMessageId() {
		return messageId;
	}

	public boolean isLocal() {
		return local;
	}

	public State getState() {
		return state;
	}

}
