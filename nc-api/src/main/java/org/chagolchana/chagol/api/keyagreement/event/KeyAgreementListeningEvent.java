package org.chagolchana.chagol.api.keyagreement.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.keyagreement.Payload;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a BQP task is listening.
 */
@Immutable
@NotNullByDefault
public class KeyAgreementListeningEvent extends Event {

	private final Payload localPayload;

	public KeyAgreementListeningEvent(Payload localPayload) {
		this.localPayload = localPayload;
	}

	public Payload getLocalPayload() {
		return localPayload;
	}
}
