package org.chagolchana.chagol.api.identity.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a local pseudonym is added.
 */
@Immutable
@NotNullByDefault
public class LocalAuthorAddedEvent extends Event {

	private final AuthorId authorId;

	public LocalAuthorAddedEvent(AuthorId authorId) {
		this.authorId = authorId;
	}

	public AuthorId getAuthorId() {
		return authorId;
	}
}
