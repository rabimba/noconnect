package org.chagolchana.chagol.api.plugin.simplex;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.Plugin;
import org.chagolchana.chagol.api.plugin.TransportConnectionReader;
import org.chagolchana.chagol.api.plugin.TransportConnectionWriter;

import javax.annotation.Nullable;

/**
 * An interface for transport plugins that support simplex communication.
 */
@NotNullByDefault
public interface SimplexPlugin extends Plugin {

	/**
	 * Attempts to create and return a reader for the given contact using the
	 * current transport and configuration properties. Returns null if a reader
	 * cannot be created.
	 */
	@Nullable
	TransportConnectionReader createReader(ContactId c);

	/**
	 * Attempts to create and return a writer for the given contact using the
	 * current transport and configuration properties. Returns null if a writer
	 * cannot be created.
	 */
	@Nullable
	TransportConnectionWriter createWriter(ContactId c);
}
