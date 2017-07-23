package org.chagolchana.chagol.api.settings.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when one or more settings are updated.
 */
@Immutable
@NotNullByDefault
public class SettingsUpdatedEvent extends Event {

	private final String namespace;

	public SettingsUpdatedEvent(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}
}
