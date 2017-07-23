package org.chagolchana.noconnect.api.messaging;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.MessageTracker.GroupCount;

@NotNullByDefault
public interface ConversationManager {

	/**
	 * Clients that present messages in a private conversation need to
	 * register themselves here.
	 */
	void registerConversationClient(ConversationClient client);

	/**
	 * Returns the unified group count for all private conversation messages.
	 */
	GroupCount getGroupCount(ContactId c) throws DbException;

	@NotNullByDefault
	interface ConversationClient {

		Group getContactGroup(Contact c);

		GroupCount getGroupCount(Transaction txn, ContactId c)
				throws DbException;

		void setReadFlag(GroupId g, MessageId m, boolean read)
				throws DbException;
	}

}
