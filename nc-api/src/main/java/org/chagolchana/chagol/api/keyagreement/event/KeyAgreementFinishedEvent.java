package org.chagolchana.chagol.api.keyagreement.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.keyagreement.KeyAgreementResult;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a BQP protocol completes.
 */
@Immutable
@NotNullByDefault
public class KeyAgreementFinishedEvent extends Event {

	private final KeyAgreementResult result;

	public KeyAgreementFinishedEvent(KeyAgreementResult result) {
		this.result = result;
	}

	public KeyAgreementResult getResult() {
		return result;
	}
}
