package org.chagolchana.noconnect.api.privategroup.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.api.privategroup.GroupMessageHeader;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a private group message was added
 * to the database.
 */
@Immutable
@NotNullByDefault
public class GroupMessageAddedEvent extends Event {

	private final GroupId groupId;
	private final GroupMessageHeader header;
	private final boolean local;

	public GroupMessageAddedEvent(GroupId groupId, GroupMessageHeader header,
			boolean local) {

		this.groupId = groupId;
		this.header = header;
		this.local = local;
	}

	public GroupId getGroupId() {
		return groupId;
	}

	public GroupMessageHeader getHeader() {
		return header;
	}

	public boolean isLocal() {
		return local;
	}

}
