package org.chagolchana.noconnect.api.blog;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static org.chagolchana.noconnect.api.blog.MessageType.COMMENT;
import static org.chagolchana.noconnect.api.blog.MessageType.WRAPPED_COMMENT;

@Immutable
@NotNullByDefault
public class BlogCommentHeader extends BlogPostHeader {

	@Nullable
	private final String comment;
	private final BlogPostHeader parent;

	public BlogCommentHeader(MessageType type, GroupId groupId,
			@Nullable String comment, BlogPostHeader parent, MessageId id,
			long timestamp, long timeReceived, Author author,
			Status authorStatus, boolean read) {

		super(type, groupId, id, parent.getId(), timestamp,
				timeReceived, author, authorStatus, false, read);

		if (type != COMMENT && type != WRAPPED_COMMENT)
			throw new IllegalArgumentException("Incompatible Message Type");

		this.comment = comment;
		this.parent = parent;
	}

	@Nullable
	public String getComment() {
		return comment;
	}

	public BlogPostHeader getParent() {
		return parent;
	}

	public BlogPostHeader getRootPost() {
		if (parent instanceof BlogCommentHeader)
			return ((BlogCommentHeader) parent).getRootPost();
		return parent;
	}

}
