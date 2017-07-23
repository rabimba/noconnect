package org.chagolchana.chagol.api.lifecycle;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface ShutdownManager {

	/**
	 * Registers a hook to be run when the JVM shuts down and returns a handle
	 * that can be used to remove the hook.
	 */
	int addShutdownHook(Runnable hook);

	/**
	 * Removes the shutdown hook identified by the given handle and returns
	 * true if the hook was removed.
	 */
	boolean removeShutdownHook(int handle);
}
