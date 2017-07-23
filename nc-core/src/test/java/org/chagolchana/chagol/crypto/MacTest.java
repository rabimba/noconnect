package org.chagolchana.chagol.crypto;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.test.BrambleTestCase;
import org.chagolchana.chagol.test.TestSecureRandomProvider;
import org.chagolchana.chagol.test.TestUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

public class MacTest extends BrambleTestCase {

	private final CryptoComponent crypto;

	private final SecretKey k = TestUtils.getSecretKey();
	private final byte[] inputBytes = TestUtils.getRandomBytes(123);
	private final byte[] inputBytes1 = TestUtils.getRandomBytes(234);
	private final byte[] inputBytes2 = new byte[0];

	public MacTest() {
		crypto = new CryptoComponentImpl(new TestSecureRandomProvider());
	}

	@Test
	public void testIdenticalKeysAndInputsProduceIdenticalMacs() {
		// Calculate the MAC twice - the results should be identical
		byte[] mac = crypto.mac(k, inputBytes, inputBytes1, inputBytes2);
		byte[] mac1 = crypto.mac(k, inputBytes, inputBytes1, inputBytes2);
		assertArrayEquals(mac, mac1);
	}

	@Test
	public void testDifferentKeysProduceDifferentMacs() {
		// Generate second random key
		SecretKey k1 = TestUtils.getSecretKey();
		// Calculate the MAC with each key - the results should be different
		byte[] mac = crypto.mac(k, inputBytes, inputBytes1, inputBytes2);
		byte[] mac1 = crypto.mac(k1, inputBytes, inputBytes1, inputBytes2);
		assertFalse(Arrays.equals(mac, mac1));
	}

	@Test
	public void testDifferentInputsProduceDifferentMacs() {
		// Calculate the MAC with the inputs in different orders - the results
		// should be different
		byte[] mac = crypto.mac(k, inputBytes, inputBytes1, inputBytes2);
		byte[] mac1 = crypto.mac(k, inputBytes2, inputBytes1, inputBytes);
		assertFalse(Arrays.equals(mac, mac1));
	}

}
