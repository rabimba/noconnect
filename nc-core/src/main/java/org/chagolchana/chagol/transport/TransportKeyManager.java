package org.chagolchana.chagol.transport;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.StreamContext;

import javax.annotation.Nullable;

@NotNullByDefault
interface TransportKeyManager {

	void start(Transaction txn) throws DbException;

	void addContact(Transaction txn, ContactId c, SecretKey master,
			long timestamp, boolean alice) throws DbException;

	void removeContact(ContactId c);

	@Nullable
	StreamContext getStreamContext(Transaction txn, ContactId c)
			throws DbException;

	@Nullable
	StreamContext getStreamContext(Transaction txn, byte[] tag)
			throws DbException;

}
