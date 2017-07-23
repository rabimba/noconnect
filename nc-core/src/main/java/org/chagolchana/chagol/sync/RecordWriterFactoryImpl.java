package org.chagolchana.chagol.sync;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.RecordWriter;
import org.chagolchana.chagol.api.sync.RecordWriterFactory;

import java.io.OutputStream;

@NotNullByDefault
class RecordWriterFactoryImpl implements RecordWriterFactory {

	@Override
	public RecordWriter createRecordWriter(OutputStream out) {
		return new RecordWriterImpl(out);
	}
}
