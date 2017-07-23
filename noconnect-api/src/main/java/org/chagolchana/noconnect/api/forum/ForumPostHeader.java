package org.chagolchana.noconnect.api.forum;

import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.PostHeader;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ForumPostHeader extends PostHeader {

	public ForumPostHeader(MessageId id, @Nullable MessageId parentId,
			long timestamp, Author author, Author.Status authorStatus,
			boolean read) {
		super(id, parentId, timestamp, author, authorStatus, read);
	}

}
