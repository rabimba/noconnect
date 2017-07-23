package org.chagolchana.noconnect.android.contact;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
abstract class ConversationOutItem extends ConversationItem {

	private boolean sent, seen;

	ConversationOutItem(MessageId id, GroupId groupId, @Nullable String text,
			long time, boolean sent, boolean seen) {
		super(id, groupId, text, time, true);

		this.sent = sent;
		this.seen = seen;
	}

	boolean isSent() {
		return sent;
	}

	void setSent(boolean sent) {
		this.sent = sent;
	}

	boolean isSeen() {
		return seen;
	}

	void setSeen(boolean seen) {
		this.seen = seen;
	}

	@Override
	public boolean isIncoming() {
		return false;
	}

}
