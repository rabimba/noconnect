package org.chagolchana.chagol.api.sync;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface GroupFactory {

	/**
	 * Creates a group with the given client ID and descriptor.
	 */
	Group createGroup(ClientId c, byte[] descriptor);
}
