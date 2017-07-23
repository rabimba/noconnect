package org.chagolchana.noconnect.android.blog;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;

import java.util.Collection;

@NotNullByDefault
public interface BlogController extends BaseController {

	void setGroupId(GroupId g);

	void setBlogSharingListener(BlogSharingListener listener);

	void loadBlogPosts(
			ResultExceptionHandler<Collection<BlogPostItem>, DbException> handler);

	void loadBlogPost(MessageId m,
			ResultExceptionHandler<BlogPostItem, DbException> handler);

	void loadBlog(ResultExceptionHandler<BlogItem, DbException> handler);

	void deleteBlog(ResultExceptionHandler<Void, DbException> handler);

	void loadSharingContacts(
			ResultExceptionHandler<Collection<ContactId>, DbException> handler);

	interface BlogSharingListener extends BlogListener {
		@UiThread
		void onBlogInvitationAccepted(ContactId c);

		@UiThread
		void onBlogLeft(ContactId c);
	}

}
