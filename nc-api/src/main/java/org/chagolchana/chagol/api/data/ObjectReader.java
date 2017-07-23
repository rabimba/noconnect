package org.chagolchana.chagol.api.data;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.IOException;

@NotNullByDefault
public interface ObjectReader<T> {

	T readObject(BdfReader r) throws IOException;
}
