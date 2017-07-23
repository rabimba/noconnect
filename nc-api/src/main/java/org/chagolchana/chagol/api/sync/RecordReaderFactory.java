package org.chagolchana.chagol.api.sync;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.InputStream;

@NotNullByDefault
public interface RecordReaderFactory {

	RecordReader createRecordReader(InputStream in);
}
