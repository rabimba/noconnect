package org.chagolchana.chagol.crypto;

import org.chagolchana.chagol.api.crypto.PrivateKey;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
class Sec1PrivateKey implements PrivateKey {

	private final ECPrivateKeyParameters key;
	private final int bytesPerInt;

	Sec1PrivateKey(ECPrivateKeyParameters key, int keyBits) {
		this.key = key;
		bytesPerInt = (keyBits + 7) / 8;
	}

	@Override
	public byte[] getEncoded() {
		byte[] encodedKey = new byte[bytesPerInt];
		byte[] d = key.getD().toByteArray();
		Sec1Utils.convertToFixedLength(d, encodedKey, 0, bytesPerInt);
		return encodedKey;
	}

	ECPrivateKeyParameters getKey() {
		return key;
	}
}
