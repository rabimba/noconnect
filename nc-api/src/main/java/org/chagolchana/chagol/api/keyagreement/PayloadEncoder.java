package org.chagolchana.chagol.api.keyagreement;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface PayloadEncoder {

	byte[] encode(Payload p);
}
