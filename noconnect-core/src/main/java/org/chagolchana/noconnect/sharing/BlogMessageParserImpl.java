package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.blog.Blog;
import org.chagolchana.noconnect.api.blog.BlogFactory;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class BlogMessageParserImpl extends MessageParserImpl<Blog> {

	private final BlogFactory blogFactory;
	private final AuthorFactory authorFactory;

	@Inject
	BlogMessageParserImpl(ClientHelper clientHelper, BlogFactory blogFactory,
			AuthorFactory authorFactory) {
		super(clientHelper);
		this.blogFactory = blogFactory;
		this.authorFactory = authorFactory;
	}

	@Override
	protected Blog createShareable(BdfList descriptor)
			throws FormatException {
		String name = descriptor.getString(0);
		byte[] publicKey = descriptor.getRaw(1);
		boolean rssFeed = descriptor.getBoolean(2);
		Author author = authorFactory.createAuthor(name, publicKey);
		if (rssFeed) return blogFactory.createFeedBlog(author);
		else return blogFactory.createBlog(author);
	}

}
