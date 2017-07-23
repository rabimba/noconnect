package org.chagolchana.chagol.api.data;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.InputStream;

@NotNullByDefault
public interface BdfReaderFactory {

	BdfReader createReader(InputStream in);

	BdfReader createReader(InputStream in, int nestedLimit);
}
