package org.chagolchana.chagol.api.sync;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.InputStream;
import java.io.OutputStream;

@NotNullByDefault
public interface SyncSessionFactory {

	SyncSession createIncomingSession(ContactId c, InputStream in);

	SyncSession createSimplexOutgoingSession(ContactId c, int maxLatency,
			OutputStream out);

	SyncSession createDuplexOutgoingSession(ContactId c, int maxLatency,
			int maxIdleTime, OutputStream out);
}
