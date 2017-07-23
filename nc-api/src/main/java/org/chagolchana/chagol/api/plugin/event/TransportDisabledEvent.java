package org.chagolchana.chagol.api.plugin.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.TransportId;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a transport is disabled.
 */
@Immutable
@NotNullByDefault
public class TransportDisabledEvent extends Event {

	private final TransportId transportId;

	public TransportDisabledEvent(TransportId transportId) {
		this.transportId = transportId;
	}

	public TransportId getTransportId() {
		return transportId;
	}
}
