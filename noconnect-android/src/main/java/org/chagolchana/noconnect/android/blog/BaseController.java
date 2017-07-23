package org.chagolchana.noconnect.android.blog;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.android.DestroyableContext;
import org.chagolchana.noconnect.android.controller.handler.ExceptionHandler;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;
import org.chagolchana.noconnect.api.blog.BlogPostHeader;

import java.util.Collection;

import javax.annotation.Nullable;

@NotNullByDefault
interface BaseController {

	@UiThread
	void onStart();

	@UiThread
	void onStop();

	void loadBlogPosts(GroupId g,
			ResultExceptionHandler<Collection<BlogPostItem>, DbException> handler);

	void loadBlogPost(BlogPostHeader header,
			ResultExceptionHandler<BlogPostItem, DbException> handler);

	void loadBlogPost(GroupId g, MessageId m,
			ResultExceptionHandler<BlogPostItem, DbException> handler);

	void repeatPost(BlogPostItem item, @Nullable String comment,
			ExceptionHandler<DbException> handler);

	void setBlogListener(BlogListener listener);

	@NotNullByDefault
	interface BlogListener extends DestroyableContext {

		@UiThread
		void onBlogPostAdded(BlogPostHeader header, boolean local);

		@UiThread
		void onBlogRemoved();
	}

}
