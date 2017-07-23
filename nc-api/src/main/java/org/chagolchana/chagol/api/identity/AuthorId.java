package org.chagolchana.chagol.api.identity;

import org.chagolchana.chagol.api.UniqueId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Type-safe wrapper for a byte array that uniquely identifies an
 * {@link Author}.
 */
@ThreadSafe
@NotNullByDefault
public class AuthorId extends UniqueId {

	/**
	 * Label for hashing authors to calculate their identities.
	 */
	public static final String LABEL = "org.briarproject.bramble.AUTHOR_ID";

	public AuthorId(byte[] id) {
		super(id);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof AuthorId && super.equals(o);
	}
}
