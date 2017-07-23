package org.chagolchana.chagol.api.crypto;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

/**
 * The public half of a public/private {@link KeyPair}.
 */
@NotNullByDefault
public interface PublicKey {

	/**
	 * Returns the encoded representation of this key.
	 */
	byte[] getEncoded();
}
