package org.chagolchana.noconnect.android.blog;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;
import org.chagolchana.noconnect.api.blog.Blog;

import java.util.Collection;

@NotNullByDefault
public interface FeedController extends BaseController {

	void loadBlogPosts(
			ResultExceptionHandler<Collection<BlogPostItem>, DbException> handler);

	void loadPersonalBlog(ResultExceptionHandler<Blog, DbException> handler);

	void setFeedListener(FeedListener listener);

	@NotNullByDefault
	interface FeedListener extends BlogListener {

		@UiThread
		void onBlogAdded();
	}
}
