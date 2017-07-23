package org.chagolchana.chagol.api.contact;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface ContactExchangeListener {

	void contactExchangeSucceeded(Author remoteAuthor);

	/**
	 * The exchange failed because the contact already exists.
	 */
	void duplicateContact(Author remoteAuthor);

	/**
	 * A general failure.
	 */
	void contactExchangeFailed();
}
