package org.chagolchana.chagol.api.reliability;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.IOException;

@NotNullByDefault
public interface WriteHandler {

	void handleWrite(byte[] b) throws IOException;
}
