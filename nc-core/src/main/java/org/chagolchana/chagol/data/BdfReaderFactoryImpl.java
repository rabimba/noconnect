package org.chagolchana.chagol.data;

import org.chagolchana.chagol.api.data.BdfReader;
import org.chagolchana.chagol.api.data.BdfReaderFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.InputStream;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.data.BdfReader.DEFAULT_NESTED_LIMIT;

@Immutable
@NotNullByDefault
class BdfReaderFactoryImpl implements BdfReaderFactory {

	@Override
	public BdfReader createReader(InputStream in) {
		return new BdfReaderImpl(in, DEFAULT_NESTED_LIMIT);
	}

	@Override
	public BdfReader createReader(InputStream in, int nestedLimit) {
		return new BdfReaderImpl(in, nestedLimit);
	}
}
