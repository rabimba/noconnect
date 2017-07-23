package org.chagolchana.chagol.sync;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.RecordReader;
import org.chagolchana.chagol.api.sync.RecordReaderFactory;
import org.chagolchana.chagol.api.sync.RecordWriter;
import org.chagolchana.chagol.api.sync.RecordWriterFactory;
import org.chagolchana.chagol.api.sync.SyncSession;
import org.chagolchana.chagol.api.sync.SyncSessionFactory;
import org.chagolchana.chagol.api.system.Clock;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class SyncSessionFactoryImpl implements SyncSessionFactory {

	private final DatabaseComponent db;
	private final Executor dbExecutor;
	private final EventBus eventBus;
	private final Clock clock;
	private final RecordReaderFactory recordReaderFactory;
	private final RecordWriterFactory recordWriterFactory;

	@Inject
	SyncSessionFactoryImpl(DatabaseComponent db,
			@DatabaseExecutor Executor dbExecutor, EventBus eventBus,
			Clock clock, RecordReaderFactory recordReaderFactory,
			RecordWriterFactory recordWriterFactory) {
		this.db = db;
		this.dbExecutor = dbExecutor;
		this.eventBus = eventBus;
		this.clock = clock;
		this.recordReaderFactory = recordReaderFactory;
		this.recordWriterFactory = recordWriterFactory;
	}

	@Override
	public SyncSession createIncomingSession(ContactId c, InputStream in) {
		RecordReader recordReader = recordReaderFactory.createRecordReader(in);
		return new IncomingSession(db, dbExecutor, eventBus, c, recordReader);
	}

	@Override
	public SyncSession createSimplexOutgoingSession(ContactId c,
			int maxLatency, OutputStream out) {
		RecordWriter recordWriter = recordWriterFactory.createRecordWriter(out);
		return new SimplexOutgoingSession(db, dbExecutor, eventBus, c,
				maxLatency, recordWriter);
	}

	@Override
	public SyncSession createDuplexOutgoingSession(ContactId c, int maxLatency,
			int maxIdleTime, OutputStream out) {
		RecordWriter recordWriter = recordWriterFactory.createRecordWriter(out);
		return new DuplexOutgoingSession(db, dbExecutor, eventBus, clock, c,
				maxLatency, maxIdleTime, recordWriter);
	}
}
