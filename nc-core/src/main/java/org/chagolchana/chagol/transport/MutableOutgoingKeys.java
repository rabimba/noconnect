package org.chagolchana.chagol.transport;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.OutgoingKeys;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
class MutableOutgoingKeys {

	private final SecretKey tagKey, headerKey;
	private final long rotationPeriod;
	private long streamCounter;

	MutableOutgoingKeys(OutgoingKeys out) {
		tagKey = out.getTagKey();
		headerKey = out.getHeaderKey();
		rotationPeriod = out.getRotationPeriod();
		streamCounter = out.getStreamCounter();
	}

	OutgoingKeys snapshot() {
		return new OutgoingKeys(tagKey, headerKey, rotationPeriod,
				streamCounter);
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

	long getStreamCounter() {
		return streamCounter;
	}

	void incrementStreamCounter() {
		streamCounter++;
	}
}
