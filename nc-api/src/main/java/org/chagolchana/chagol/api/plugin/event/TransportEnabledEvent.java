package org.chagolchana.chagol.api.plugin.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.TransportId;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a transport is enabled.
 */
@Immutable
@NotNullByDefault
public class TransportEnabledEvent extends Event {

	private final TransportId transportId;

	public TransportEnabledEvent(TransportId transportId) {
		this.transportId = transportId;
	}

	public TransportId getTransportId() {
		return transportId;
	}
}
