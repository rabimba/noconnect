package org.chagolchana.chagol.api.crypto;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

/**
 * A deterministic pseudo-random number generator.
 */
@NotNullByDefault
public interface PseudoRandom {

	byte[] nextBytes(int bytes);
}
