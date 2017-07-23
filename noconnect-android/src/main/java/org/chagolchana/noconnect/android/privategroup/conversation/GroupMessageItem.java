package org.chagolchana.noconnect.android.privategroup.conversation;

import android.support.annotation.LayoutRes;
import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.threaded.ThreadItem;
import org.chagolchana.noconnect.api.privategroup.GroupMessageHeader;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@UiThread
@NotThreadSafe
class GroupMessageItem extends ThreadItem {

	private final GroupId groupId;

	private GroupMessageItem(MessageId messageId, GroupId groupId,
			@Nullable MessageId parentId, String text, long timestamp,
			Author author, Status status, boolean isRead) {
		super(messageId, parentId, text, timestamp, author, status, isRead);
		this.groupId = groupId;
	}

	GroupMessageItem(GroupMessageHeader h, String text) {
		this(h.getId(), h.getGroupId(), h.getParentId(), text, h.getTimestamp(),
				h.getAuthor(), h.getAuthorStatus(), h.isRead());
	}

	public GroupId getGroupId() {
		return groupId;
	}

	@LayoutRes
	public int getLayout() {
		return R.layout.list_item_thread;
	}

}
