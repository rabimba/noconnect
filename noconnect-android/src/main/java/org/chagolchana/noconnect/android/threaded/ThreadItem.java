package org.chagolchana.noconnect.android.threaded;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.MessageTree.MessageNode;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import static org.chagolchana.noconnect.android.threaded.ThreadItemAdapter.UNDEFINED;

@NotThreadSafe
@NotNullByDefault
public abstract class ThreadItem implements MessageNode {

	private final MessageId messageId;
	@Nullable
	private final MessageId parentId;
	private final String text;
	private final long timestamp;
	private final Author author;
	private final Status status;
	private int level = UNDEFINED;
	private boolean isRead, highlighted;

	public ThreadItem(MessageId messageId, @Nullable MessageId parentId,
			String text, long timestamp, Author author, Status status,
			boolean isRead) {
		this.messageId = messageId;
		this.parentId = parentId;
		this.text = text;
		this.timestamp = timestamp;
		this.author = author;
		this.status = status;
		this.isRead = isRead;
		this.highlighted = false;
	}

	public String getText() {
		return text;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public MessageId getId() {
		return messageId;
	}

	@Override
	@Nullable
	public MessageId getParentId() {
		return parentId;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	public Author getAuthor() {
		return author;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

}
