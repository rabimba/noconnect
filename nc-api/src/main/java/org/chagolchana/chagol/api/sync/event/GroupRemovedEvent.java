package org.chagolchana.chagol.api.sync.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a group is removed from the database.
 */
@Immutable
@NotNullByDefault
public class GroupRemovedEvent extends Event {

	private final Group group;

	public GroupRemovedEvent(Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}
}
