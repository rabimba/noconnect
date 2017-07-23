package org.chagolchana.chagol.api.crypto;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.security.GeneralSecurityException;

@NotNullByDefault
public interface KeyParser {

	PublicKey parsePublicKey(byte[] encodedKey) throws GeneralSecurityException;

	PrivateKey parsePrivateKey(byte[] encodedKey)
			throws GeneralSecurityException;
}
