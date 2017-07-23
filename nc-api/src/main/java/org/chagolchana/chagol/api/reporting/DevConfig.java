package org.chagolchana.chagol.api.reporting;

import org.chagolchana.chagol.api.crypto.PublicKey;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface DevConfig {

	PublicKey getDevPublicKey();

	String getDevOnionAddress();
}
