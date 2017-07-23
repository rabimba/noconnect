package org.chagolchana.chagol.identity;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

import static org.chagolchana.chagol.api.identity.Author.Status.OURSELVES;
import static org.chagolchana.chagol.api.identity.Author.Status.UNKNOWN;
import static org.chagolchana.chagol.api.identity.Author.Status.UNVERIFIED;
import static org.chagolchana.chagol.api.identity.Author.Status.VERIFIED;

@ThreadSafe
@NotNullByDefault
class IdentityManagerImpl implements IdentityManager {

	private static final Logger LOG =
			Logger.getLogger(IdentityManagerImpl.class.getName());

	private final DatabaseComponent db;

	// The local author is immutable so we can cache it
	@Nullable
	private volatile LocalAuthor cachedAuthor;

	@Inject
	IdentityManagerImpl(DatabaseComponent db) {
		this.db = db;
	}

	@Override
	public void registerLocalAuthor(LocalAuthor localAuthor)
			throws DbException {
		Transaction txn = db.startTransaction(false);
		try {
			db.addLocalAuthor(txn, localAuthor);
			db.commitTransaction(txn);
			cachedAuthor = localAuthor;
			LOG.info("Local author registered");
		} finally {
			db.endTransaction(txn);
		}
	}

	@Override
	public LocalAuthor getLocalAuthor() throws DbException {
		if (cachedAuthor == null) {
			Transaction txn = db.startTransaction(true);
			try {
				cachedAuthor = loadLocalAuthor(txn);
				LOG.info("Local author loaded");
				db.commitTransaction(txn);
			} finally {
				db.endTransaction(txn);
			}
		}
		LocalAuthor cached = cachedAuthor;
		if (cached == null) throw new AssertionError();
		return cached;
	}


	@Override
	public LocalAuthor getLocalAuthor(Transaction txn) throws DbException {
		if (cachedAuthor == null) {
			cachedAuthor = loadLocalAuthor(txn);
			LOG.info("Local author loaded");
		}
		LocalAuthor cached = cachedAuthor;
		if (cached == null) throw new AssertionError();
		return cached;
	}

	private LocalAuthor loadLocalAuthor(Transaction txn) throws DbException {
		return db.getLocalAuthors(txn).iterator().next();
	}

	@Override
	public Status getAuthorStatus(AuthorId authorId) throws DbException {
		Transaction txn = db.startTransaction(true);
		try {
			return getAuthorStatus(txn, authorId);
		} finally {
			db.endTransaction(txn);
		}
	}

	@Override
	public Status getAuthorStatus(Transaction txn, AuthorId authorId)
			throws DbException {
		if (getLocalAuthor(txn).getId().equals(authorId)) return OURSELVES;
		Collection<Contact> contacts = db.getContactsByAuthorId(txn, authorId);
		if (contacts.isEmpty()) return UNKNOWN;
		for (Contact c : contacts) {
			if (c.isVerified()) return VERIFIED;
		}
		return UNVERIFIED;
	}

}
