package org.chagolchana.chagol.api.transport;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.plugin.TransportId;

import javax.annotation.Nullable;

/**
 * Responsible for managing transport keys and recognising the pseudo-random
 * tags of incoming streams.
 */
public interface KeyManager {

	/**
	 * Informs the key manager that a new contact has been added. Derives and
	 * stores transport keys for communicating with the contact.
	 * {@link StreamContext StreamContexts} for the contact can be created
	 * after this method has returned.
	 */
	void addContact(Transaction txn, ContactId c, SecretKey master,
			long timestamp, boolean alice) throws DbException;

	/**
	 * Returns a {@link StreamContext} for sending a stream to the given
	 * contact over the given transport, or null if an error occurs or the
	 * contact does not support the transport.
	 */
	@Nullable
	StreamContext getStreamContext(ContactId c, TransportId t)
			throws DbException;

	/**
	 * Looks up the given tag and returns a {@link StreamContext} for reading
	 * from the corresponding stream, or null if an error occurs or the tag was
	 * unexpected.
	 */
	@Nullable
	StreamContext getStreamContext(TransportId t, byte[] tag)
			throws DbException;
}
