package org.chagolchana.chagol.api.keyagreement.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a BQP protocol aborts.
 */
@Immutable
@NotNullByDefault
public class KeyAgreementAbortedEvent extends Event {

	private final boolean remoteAborted;

	public KeyAgreementAbortedEvent(boolean remoteAborted) {
		this.remoteAborted = remoteAborted;
	}

	public boolean didRemoteAbort() {
		return remoteAborted;
	}
}
