package org.chagolchana.noconnect.android.privategroup.list;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.api.client.MessageTracker.GroupCount;
import org.chagolchana.noconnect.api.privategroup.GroupMessageHeader;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;

// This class is not thread-safe
@NotNullByDefault
class GroupItem {

	private final PrivateGroup privateGroup;
	private int messageCount, unreadCount;
	private long timestamp;
	private boolean dissolved;

	GroupItem(PrivateGroup privateGroup, GroupCount count, boolean dissolved) {
		this.privateGroup = privateGroup;
		this.messageCount = count.getMsgCount();
		this.unreadCount = count.getUnreadCount();
		this.timestamp = count.getLatestMsgTime();
		this.dissolved = dissolved;
	}

	void addMessageHeader(GroupMessageHeader header) {
		messageCount++;
		if (header.getTimestamp() > timestamp) {
			timestamp = header.getTimestamp();
		}
		if (!header.isRead()) {
			unreadCount++;
		}
	}

	PrivateGroup getPrivateGroup() {
		return privateGroup;
	}

	GroupId getId() {
		return privateGroup.getId();
	}

	Author getCreator() {
		return privateGroup.getCreator();
	}

	String getName() {
		return privateGroup.getName();
	}

	boolean isEmpty() {
		return messageCount == 0;
	}

	int getMessageCount() {
		return messageCount;
	}

	long getTimestamp() {
		return timestamp;
	}

	int getUnreadCount() {
		return unreadCount;
	}

	boolean isDissolved() {
		return dissolved;
	}

	void setDissolved() {
		dissolved = true;
	}

}
