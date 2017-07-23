package org.chagolchana.noconnect.api.blog;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;

@NotNullByDefault
public interface BlogFactory {

	/**
	 * Creates a personal blog for a given author.
	 */
	Blog createBlog(Author author);

	/**
	 * Creates a RSS feed blog for a given author.
	 */
	Blog createFeedBlog(Author author);

	/**
	 * Parses a blog with the given Group
	 */
	Blog parseBlog(Group g) throws FormatException;

}
