package org.chagolchana.chagol.api.sync;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface Client {

	/**
	 * Called at startup to create any local state needed by the client.
	 */
	void createLocalState(Transaction txn) throws DbException;
}
