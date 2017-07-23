package org.chagolchana.chagol.transport;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.IncomingKeys;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
class MutableIncomingKeys {

	private final SecretKey tagKey, headerKey;
	private final long rotationPeriod;
	private final ReorderingWindow window;

	MutableIncomingKeys(IncomingKeys in) {
		tagKey = in.getTagKey();
		headerKey = in.getHeaderKey();
		rotationPeriod = in.getRotationPeriod();
		window = new ReorderingWindow(in.getWindowBase(), in.getWindowBitmap());
	}

	IncomingKeys snapshot() {
		return new IncomingKeys(tagKey, headerKey, rotationPeriod,
				window.getBase(), window.getBitmap());
	}

	SecretKey getTagKey() {
		return tagKey;
	}

	SecretKey getHeaderKey() {
		return headerKey;
	}

	long getRotationPeriod() {
		return rotationPeriod;
	}

	ReorderingWindow getWindow() {
		return window;
	}
}
