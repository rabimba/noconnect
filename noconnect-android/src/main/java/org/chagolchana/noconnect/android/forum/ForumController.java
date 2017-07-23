package org.chagolchana.noconnect.android.forum;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.threaded.ThreadListController;
import org.chagolchana.noconnect.api.forum.Forum;
import org.chagolchana.noconnect.api.forum.ForumPostHeader;

@NotNullByDefault
interface ForumController
		extends ThreadListController<Forum, ForumItem, ForumPostHeader> {

	interface ForumListener extends ThreadListListener<ForumPostHeader> {
		@UiThread
		void onForumLeft(ContactId c);
	}

}
