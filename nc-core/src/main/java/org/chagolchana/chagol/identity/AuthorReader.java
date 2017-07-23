package org.chagolchana.chagol.identity;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.data.BdfReader;
import org.chagolchana.chagol.api.data.ObjectReader;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.IOException;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_AUTHOR_NAME_LENGTH;
import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_PUBLIC_KEY_LENGTH;

@Immutable
@NotNullByDefault
class AuthorReader implements ObjectReader<Author> {

	private final AuthorFactory authorFactory;

	AuthorReader(AuthorFactory authorFactory) {
		this.authorFactory = authorFactory;
	}

	@Override
	public Author readObject(BdfReader r) throws IOException {
		r.readListStart();
		String name = r.readString(MAX_AUTHOR_NAME_LENGTH);
		if (name.length() == 0) throw new FormatException();
		byte[] publicKey = r.readRaw(MAX_PUBLIC_KEY_LENGTH);
		r.readListEnd();
		return authorFactory.createAuthor(name, publicKey);
	}
}
