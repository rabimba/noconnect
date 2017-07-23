package org.chagolchana.noconnect.feed;

import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.contact.ContactModule;
import org.chagolchana.chagol.crypto.CryptoModule;
import org.chagolchana.chagol.identity.IdentityModule;
import org.chagolchana.chagol.lifecycle.LifecycleModule;
import org.chagolchana.chagol.sync.SyncModule;
import org.chagolchana.chagol.system.SystemModule;
import org.chagolchana.chagol.test.TestDatabaseModule;
import org.chagolchana.chagol.test.TestUtils;
import org.chagolchana.chagol.transport.TransportModule;
import org.chagolchana.noconnect.api.blog.Blog;
import org.chagolchana.noconnect.api.blog.BlogManager;
import org.chagolchana.noconnect.api.blog.BlogPostHeader;
import org.chagolchana.noconnect.api.feed.Feed;
import org.chagolchana.noconnect.api.feed.FeedManager;
import org.chagolchana.noconnect.blog.BlogModule;
import org.chagolchana.noconnect.test.BriarTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FeedManagerIntegrationTest extends BriarTestCase {

	private LifecycleManager lifecycleManager;
	private FeedManager feedManager;
	private BlogManager blogManager;
	private final File testDir = TestUtils.getTestDirectory();
	private final File testFile = new File(testDir, "feedTest");

	@Before
	public void setUp() throws Exception {
		assertTrue(testDir.mkdirs());
		FeedManagerIntegrationTestComponent component =
				DaggerFeedManagerIntegrationTestComponent.builder()
						.testDatabaseModule(new TestDatabaseModule(testFile))
						.build();
		component.inject(this);
		injectEagerSingletons(component);

		lifecycleManager = component.getLifecycleManager();
		lifecycleManager.startServices("feedTest");
		lifecycleManager.waitForStartup();

		feedManager = component.getFeedManager();
		blogManager = component.getBlogManager();
	}

	@Test
	public void testFeedImportAndRemoval() throws Exception {
		// initially, there's only the one personal blog
		Collection<Blog> blogs = blogManager.getBlogs();
		assertEquals(1, blogs.size());
		Blog personalBlog = blogs.iterator().next();

		// add feed into a dedicated blog
		String url = "https://www.schneier.com/blog/atom.xml";
		feedManager.addFeed(url);

		// then there's the feed's blog now
		blogs = blogManager.getBlogs();
		assertEquals(2, blogs.size());
		Blog feedBlog = null;
		for (Blog blog : blogs) {
			if (!blog.equals(personalBlog)) feedBlog = blog;
		}
		assertNotNull(feedBlog);

		// check the feed got saved as expected
		Collection<Feed> feeds = feedManager.getFeeds();
		assertEquals(1, feeds.size());
		Feed feed = feeds.iterator().next();
		assertTrue(feed.getLastEntryTime() > 0);
		assertTrue(feed.getAdded() > 0);
		assertTrue(feed.getUpdated() > 0);
		assertEquals(url, feed.getUrl());
		assertEquals(feedBlog, feed.getBlog());
		assertEquals("Schneier on Security", feed.getTitle());
		assertEquals("A blog covering security and security technology.",
				feed.getDescription());
		assertEquals(feed.getTitle(), feed.getBlog().getName());
		assertEquals(feed.getTitle(), feed.getLocalAuthor().getName());

		// check the feed entries have been added to the blog as expected
		Collection<BlogPostHeader> headers =
				blogManager.getPostHeaders(feedBlog.getId());
		for (BlogPostHeader header : headers) {
			assertTrue(header.isRssFeed());
		}

		// now let's remove the feed's blog again
		blogManager.removeBlog(feedBlog);
		blogs = blogManager.getBlogs();
		assertEquals(1, blogs.size());
		assertEquals(personalBlog, blogs.iterator().next());
		assertEquals(0, feedManager.getFeeds().size());
	}

	@After
	public void tearDown() throws Exception {
		lifecycleManager.stopServices();
		lifecycleManager.waitForShutdown();
		TestUtils.deleteTestDirectory(testDir);
	}

	protected void injectEagerSingletons(
			FeedManagerIntegrationTestComponent component) {
		component.inject(new FeedModule.EagerSingletons());
		component.inject(new BlogModule.EagerSingletons());
		component.inject(new ContactModule.EagerSingletons());
		component.inject(new CryptoModule.EagerSingletons());
		component.inject(new IdentityModule.EagerSingletons());
		component.inject(new LifecycleModule.EagerSingletons());
		component.inject(new SyncModule.EagerSingletons());
		component.inject(new SystemModule.EagerSingletons());
		component.inject(new TransportModule.EagerSingletons());
	}

}
