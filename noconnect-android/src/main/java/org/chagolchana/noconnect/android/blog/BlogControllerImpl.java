package org.chagolchana.noconnect.android.blog;

import android.app.Activity;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.event.EventListener;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.sync.event.GroupRemovedEvent;
import org.chagolchana.noconnect.android.controller.ActivityLifecycleController;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;
import org.chagolchana.noconnect.api.android.AndroidNotificationManager;
import org.chagolchana.noconnect.api.blog.Blog;
import org.chagolchana.noconnect.api.blog.BlogManager;
import org.chagolchana.noconnect.api.blog.BlogSharingManager;
import org.chagolchana.noconnect.api.blog.event.BlogInvitationResponseReceivedEvent;
import org.chagolchana.noconnect.api.blog.event.BlogPostAddedEvent;
import org.chagolchana.noconnect.api.sharing.InvitationResponse;
import org.chagolchana.noconnect.api.sharing.event.ContactLeftShareableEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.inject.Inject;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
class BlogControllerImpl extends BaseControllerImpl
		implements ActivityLifecycleController, BlogController, EventListener {

	private static final Logger LOG =
			Logger.getLogger(BlogControllerImpl.class.getName());

	private final BlogSharingManager blogSharingManager;
	private volatile GroupId groupId = null;
	private volatile BlogSharingListener listener;

	@Inject
	BlogControllerImpl(@DatabaseExecutor Executor dbExecutor,
			LifecycleManager lifecycleManager, EventBus eventBus,
			AndroidNotificationManager notificationManager,
			IdentityManager identityManager, BlogManager blogManager,
			BlogSharingManager blogSharingManager) {
		super(dbExecutor, lifecycleManager, eventBus, notificationManager,
				identityManager, blogManager);
		this.blogSharingManager = blogSharingManager;
	}

	@Override
	public void onActivityCreate(Activity activity) {
	}

	@Override
	public void onActivityStart() {
		super.onStart();
		notificationManager.blockNotification(groupId);
		notificationManager.clearBlogPostNotification(groupId);
	}

	@Override
	public void onActivityStop() {
		super.onStop();
		notificationManager.unblockNotification(groupId);
	}

	@Override
	public void onActivityDestroy() {
	}

	@Override
	public void setGroupId(GroupId g) {
		groupId = g;
	}

	@Override
	public void setBlogSharingListener(BlogSharingListener listener) {
		super.setBlogListener(listener);
		this.listener = listener;
	}

	@Override
	public void eventOccurred(Event e) {
		if (groupId == null) throw new IllegalStateException();
		if (e instanceof BlogPostAddedEvent) {
			BlogPostAddedEvent b = (BlogPostAddedEvent) e;
			if (b.getGroupId().equals(groupId)) {
				LOG.info("Blog post added");
				onBlogPostAdded(b.getHeader(), b.isLocal());
			}
		} else if (e instanceof BlogInvitationResponseReceivedEvent) {
			BlogInvitationResponseReceivedEvent b =
					(BlogInvitationResponseReceivedEvent) e;
			InvitationResponse r = b.getResponse();
			if (r.getGroupId().equals(groupId) && r.wasAccepted()) {
				LOG.info("Blog invitation accepted");
				onBlogInvitationAccepted(b.getContactId());
			}
		} else if (e instanceof ContactLeftShareableEvent) {
			ContactLeftShareableEvent s = (ContactLeftShareableEvent) e;
			if (s.getGroupId().equals(groupId)) {
				LOG.info("Blog left by contact");
				onBlogLeft(s.getContactId());
			}
		} else if (e instanceof GroupRemovedEvent) {
			GroupRemovedEvent g = (GroupRemovedEvent) e;
			if (g.getGroup().getId().equals(groupId)) {
				LOG.info("Blog removed");
				onBlogRemoved();
			}
		}
	}

	private void onBlogInvitationAccepted(final ContactId c) {
		listener.runOnUiThreadUnlessDestroyed(new Runnable() {
			@Override
			public void run() {
				listener.onBlogInvitationAccepted(c);
			}
		});
	}

	private void onBlogLeft(final ContactId c) {
		listener.runOnUiThreadUnlessDestroyed(new Runnable() {
			@Override
			public void run() {
				listener.onBlogLeft(c);
			}
		});
	}

	@Override
	public void loadBlogPosts(
			final ResultExceptionHandler<Collection<BlogPostItem>, DbException> handler) {
		if (groupId == null) throw new IllegalStateException();
		loadBlogPosts(groupId, handler);
	}

	@Override
	public void loadBlogPost(final MessageId m,
			final ResultExceptionHandler<BlogPostItem, DbException> handler) {
		if (groupId == null) throw new IllegalStateException();
		loadBlogPost(groupId, m, handler);
	}

	@Override
	public void loadBlog(
			final ResultExceptionHandler<BlogItem, DbException> handler) {
		if (groupId == null) throw new IllegalStateException();
		runOnDbThread(new Runnable() {
			@Override
			public void run() {
				try {
					long now = System.currentTimeMillis();
					LocalAuthor a = identityManager.getLocalAuthor();
					Blog b = blogManager.getBlog(groupId);
					boolean ours = a.getId().equals(b.getAuthor().getId());
					boolean removable = blogManager.canBeRemoved(b);
					BlogItem blog = new BlogItem(b, ours, removable);
					long duration = System.currentTimeMillis() - now;
					if (LOG.isLoggable(INFO))
						LOG.info("Loading blog took " + duration + " ms");
					handler.onResult(blog);
				} catch (DbException e) {
					if (LOG.isLoggable(WARNING))
						LOG.log(WARNING, e.toString(), e);
					handler.onException(e);
				}
			}
		});
	}

	@Override
	public void deleteBlog(
			final ResultExceptionHandler<Void, DbException> handler) {
		if (groupId == null) throw new IllegalStateException();
		runOnDbThread(new Runnable() {
			@Override
			public void run() {
				try {
					long now = System.currentTimeMillis();
					Blog b = blogManager.getBlog(groupId);
					blogManager.removeBlog(b);
					long duration = System.currentTimeMillis() - now;
					if (LOG.isLoggable(INFO))
						LOG.info("Removing blog took " + duration + " ms");
					handler.onResult(null);
				} catch (DbException e) {
					if (LOG.isLoggable(WARNING))
						LOG.log(WARNING, e.toString(), e);
					handler.onException(e);
				}
			}
		});
	}

	@Override
	public void loadSharingContacts(
			final ResultExceptionHandler<Collection<ContactId>, DbException> handler) {
		if (groupId == null) throw new IllegalStateException();
		runOnDbThread(new Runnable() {
			@Override
			public void run() {
				try {
					Collection<Contact> contacts =
							blogSharingManager.getSharedWith(groupId);
					Collection<ContactId> contactIds =
							new ArrayList<>(contacts.size());
					for (Contact c : contacts) contactIds.add(c.getId());
					handler.onResult(contactIds);
				} catch (DbException e) {
					if (LOG.isLoggable(WARNING))
						LOG.log(WARNING, e.toString(), e);
					handler.onException(e);
				}
			}
		});
	}

}
