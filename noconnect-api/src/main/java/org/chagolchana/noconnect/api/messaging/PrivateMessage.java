package org.chagolchana.noconnect.api.messaging;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Message;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class PrivateMessage {

	private final Message message;

	public PrivateMessage(Message message) {
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}

}
