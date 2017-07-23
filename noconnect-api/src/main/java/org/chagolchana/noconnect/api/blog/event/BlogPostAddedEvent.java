package org.chagolchana.noconnect.api.blog.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.api.blog.BlogPostHeader;

import javax.annotation.concurrent.Immutable;

/**
 * An event that is broadcast when a blog post is added to the database.
 */
@Immutable
@NotNullByDefault
public class BlogPostAddedEvent extends Event {

	private final GroupId groupId;
	private final BlogPostHeader header;
	private final boolean local;

	public BlogPostAddedEvent(GroupId groupId, BlogPostHeader header,
			boolean local) {

		this.groupId = groupId;
		this.header = header;
		this.local = local;
	}

	public GroupId getGroupId() {
		return groupId;
	}

	public BlogPostHeader getHeader() {
		return header;
	}

	public boolean isLocal() {
		return local;
	}
}
