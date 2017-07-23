package org.chagolchana.chagol.api.identity;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface IdentityManager {

	/**
	 * Stores the local pseudonym.
	 */
	void registerLocalAuthor(LocalAuthor a) throws DbException;

	/**
	 * Returns the cached main local identity, non-blocking, or loads it from
	 * the db, blocking
	 */
	LocalAuthor getLocalAuthor() throws DbException;

	/**
	 * Returns the cached main local identity, non-blocking, or loads it from
	 * the db, blocking, within the given Transaction.
	 */
	LocalAuthor getLocalAuthor(Transaction txn) throws DbException;

	/**
	 * Returns the trust-level status of the author
	 */
	Status getAuthorStatus(AuthorId a) throws DbException;

	/**
	 * Returns the trust-level status of the author
	 */
	Status getAuthorStatus(Transaction txn, AuthorId a) throws DbException;

}
