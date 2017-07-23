package org.chagolchana.chagol.sync;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.MessageFactory;
import org.chagolchana.chagol.api.sync.RecordReader;
import org.chagolchana.chagol.api.sync.RecordReaderFactory;

import java.io.InputStream;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class RecordReaderFactoryImpl implements RecordReaderFactory {

	private final MessageFactory messageFactory;

	@Inject
	RecordReaderFactoryImpl(MessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}

	@Override
	public RecordReader createRecordReader(InputStream in) {
		return new RecordReaderImpl(messageFactory, in);
	}
}
