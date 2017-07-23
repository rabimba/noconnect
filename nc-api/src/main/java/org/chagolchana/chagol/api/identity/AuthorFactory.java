package org.chagolchana.chagol.api.identity;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface AuthorFactory {

	Author createAuthor(String name, byte[] publicKey);

	LocalAuthor createLocalAuthor(String name, byte[] publicKey,
			byte[] privateKey);
}
