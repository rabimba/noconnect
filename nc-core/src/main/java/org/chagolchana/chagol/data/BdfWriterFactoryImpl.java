package org.chagolchana.chagol.data;

import org.chagolchana.chagol.api.data.BdfWriter;
import org.chagolchana.chagol.api.data.BdfWriterFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
class BdfWriterFactoryImpl implements BdfWriterFactory {

	@Override
	public BdfWriter createWriter(OutputStream out) {
		return new BdfWriterImpl(out);
	}
}
