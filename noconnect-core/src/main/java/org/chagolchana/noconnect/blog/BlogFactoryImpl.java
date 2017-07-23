package org.chagolchana.noconnect.blog;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupFactory;
import org.chagolchana.noconnect.api.blog.Blog;
import org.chagolchana.noconnect.api.blog.BlogFactory;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_AUTHOR_NAME_LENGTH;
import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_PUBLIC_KEY_LENGTH;

@Immutable
@NotNullByDefault
class BlogFactoryImpl implements BlogFactory {

	private final GroupFactory groupFactory;
	private final AuthorFactory authorFactory;
	private final ClientHelper clientHelper;

	@Inject
	BlogFactoryImpl(GroupFactory groupFactory, AuthorFactory authorFactory,
			ClientHelper clientHelper) {

		this.groupFactory = groupFactory;
		this.authorFactory = authorFactory;
		this.clientHelper = clientHelper;
	}

	@Override
	public Blog createBlog(Author a) {
		return createBlog(a, false);
	}

	@Override
	public Blog createFeedBlog(Author a) {
		return createBlog(a, true);
	}

	private Blog createBlog(Author a, boolean rssFeed) {
		try {
			BdfList blog = BdfList.of(
					a.getName(),
					a.getPublicKey(),
					rssFeed
			);
			byte[] descriptor = clientHelper.toByteArray(blog);
			Group g = groupFactory
					.createGroup(BlogManagerImpl.CLIENT_ID, descriptor);
			return new Blog(g, a, rssFeed);
		} catch (FormatException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Blog parseBlog(Group group) throws FormatException {
		byte[] descriptor = group.getDescriptor();
		// Author name, public key, RSS feed
		BdfList blog = clientHelper.toList(descriptor);
		String name = blog.getString(0);
		if (name.length() > MAX_AUTHOR_NAME_LENGTH)
			throw new IllegalArgumentException();
		byte[] publicKey = blog.getRaw(1);
		if (publicKey.length > MAX_PUBLIC_KEY_LENGTH)
			throw new IllegalArgumentException();

		Author author = authorFactory.createAuthor(name, publicKey);
		boolean rssFeed = blog.getBoolean(2);
		return new Blog(group, author, rssFeed);
	}

}
