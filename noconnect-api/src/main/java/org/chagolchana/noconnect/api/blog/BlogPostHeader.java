package org.chagolchana.noconnect.api.blog;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.PostHeader;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class BlogPostHeader extends PostHeader {

	private final MessageType type;
	private final GroupId groupId;
	private final long timeReceived;
	private final boolean rssFeed;

	public BlogPostHeader(MessageType type, GroupId groupId, MessageId id,
			@Nullable MessageId parentId, long timestamp, long timeReceived,
			Author author, Status authorStatus, boolean rssFeed, boolean read) {
		super(id, parentId, timestamp, author, authorStatus, read);
		this.type = type;
		this.groupId = groupId;
		this.timeReceived = timeReceived;
		this.rssFeed = rssFeed;
	}

	public BlogPostHeader(MessageType type, GroupId groupId, MessageId id,
			long timestamp, long timeReceived, Author author,
			Status authorStatus, boolean rssFeed, boolean read) {
		this(type, groupId, id, null, timestamp, timeReceived, author,
				authorStatus, rssFeed, read);
	}

	public MessageType getType() {
		return type;
	}

	public GroupId getGroupId() {
		return groupId;
	}

	public long getTimeReceived() {
		return timeReceived;
	}

	public boolean isRssFeed() {
		return rssFeed;
	}

}
