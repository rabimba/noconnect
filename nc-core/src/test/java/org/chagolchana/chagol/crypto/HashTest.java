package org.chagolchana.chagol.crypto;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.test.BrambleTestCase;
import org.chagolchana.chagol.test.TestSecureRandomProvider;
import org.chagolchana.chagol.test.TestUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

public class HashTest extends BrambleTestCase {

	private final CryptoComponent crypto;

	private final String label = TestUtils.getRandomString(42);
	private final byte[] inputBytes = TestUtils.getRandomBytes(123);
	private final byte[] inputBytes1 = TestUtils.getRandomBytes(234);
	private final byte[] inputBytes2 = new byte[0];

	public HashTest() {
		crypto = new CryptoComponentImpl(new TestSecureRandomProvider());
	}

	@Test
	public void testIdenticalInputsProduceIdenticalHashes() {
		byte[] hash1 = crypto.hash(label, inputBytes, inputBytes1, inputBytes2);
		byte[] hash2 = crypto.hash(label, inputBytes, inputBytes1, inputBytes2);
		assertArrayEquals(hash1, hash2);
	}

	@Test
	public void testDifferentInputsProduceDifferentHashes() {
		byte[] hash1 = crypto.hash(label, inputBytes, inputBytes1, inputBytes2);
		byte[] hash2 = crypto.hash(label, inputBytes2, inputBytes1, inputBytes);
		assertFalse(Arrays.equals(hash1, hash2));
	}

	@Test
	public void testDifferentLabelsProduceDifferentHashes() {
		String label2 = TestUtils.getRandomString(42);
		byte[] hash1 = crypto.hash(label, inputBytes, inputBytes1, inputBytes2);
		byte[] hash2 =
				crypto.hash(label2, inputBytes, inputBytes1, inputBytes2);
		assertFalse(Arrays.equals(hash1, hash2));
	}

}
