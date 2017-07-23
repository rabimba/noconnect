package org.chagolchana.noconnect.android.forum;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.android.threaded.ThreadItem;
import org.chagolchana.noconnect.api.forum.ForumPostHeader;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class ForumItem extends ThreadItem {

	ForumItem(ForumPostHeader h, String body) {
		super(h.getId(), h.getParentId(), body, h.getTimestamp(), h.getAuthor(),
				h.getAuthorStatus(), h.isRead());
	}

	ForumItem(MessageId messageId, @Nullable MessageId parentId, String text,
			long timestamp, Author author, Status status) {
		super(messageId, parentId, text, timestamp, author, status, true);
	}

}
