package org.chagolchana.chagol.api.sync;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.OutputStream;

@NotNullByDefault
public interface RecordWriterFactory {

	RecordWriter createRecordWriter(OutputStream out);
}
