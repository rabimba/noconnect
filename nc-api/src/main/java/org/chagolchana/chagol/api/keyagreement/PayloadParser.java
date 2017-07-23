package org.chagolchana.chagol.api.keyagreement;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.IOException;

@NotNullByDefault
public interface PayloadParser {

	Payload parse(byte[] raw) throws IOException;
}
