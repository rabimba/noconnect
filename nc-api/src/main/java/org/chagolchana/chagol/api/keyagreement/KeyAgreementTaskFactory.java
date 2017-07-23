package org.chagolchana.chagol.api.keyagreement;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

/**
 * Manages tasks for conducting key agreements with remote peers.
 */
@NotNullByDefault
public interface KeyAgreementTaskFactory {

	/**
	 * Gets the current key agreement task.
	 */
	KeyAgreementTask createTask();
}
