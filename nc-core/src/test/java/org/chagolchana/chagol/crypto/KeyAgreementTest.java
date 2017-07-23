package org.chagolchana.chagol.crypto;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.crypto.KeyPair;
import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.system.SecureRandomProvider;
import org.chagolchana.chagol.test.BrambleTestCase;
import org.chagolchana.chagol.test.TestSecureRandomProvider;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class KeyAgreementTest extends BrambleTestCase {

	@Test
	public void testDeriveMasterSecret() throws Exception {
		SecureRandomProvider
				secureRandomProvider = new TestSecureRandomProvider();
		CryptoComponent crypto = new CryptoComponentImpl(secureRandomProvider);
		KeyPair aPair = crypto.generateAgreementKeyPair();
		byte[] aPub = aPair.getPublic().getEncoded();
		KeyPair bPair = crypto.generateAgreementKeyPair();
		byte[] bPub = bPair.getPublic().getEncoded();
		SecretKey aMaster = crypto.deriveMasterSecret(aPub, bPair, true);
		SecretKey bMaster = crypto.deriveMasterSecret(bPub, aPair, false);
		assertArrayEquals(aMaster.getBytes(), bMaster.getBytes());
	}

	@Test
	public void testDeriveSharedSecret() throws Exception {
		SecureRandomProvider
				secureRandomProvider = new TestSecureRandomProvider();
		CryptoComponent crypto = new CryptoComponentImpl(secureRandomProvider);
		KeyPair aPair = crypto.generateAgreementKeyPair();
		byte[] aPub = aPair.getPublic().getEncoded();
		KeyPair bPair = crypto.generateAgreementKeyPair();
		byte[] bPub = bPair.getPublic().getEncoded();
		SecretKey aShared = crypto.deriveSharedSecret(bPub, aPair, true);
		SecretKey bShared = crypto.deriveSharedSecret(aPub, bPair, false);
		assertArrayEquals(aShared.getBytes(), bShared.getBytes());
	}
}
