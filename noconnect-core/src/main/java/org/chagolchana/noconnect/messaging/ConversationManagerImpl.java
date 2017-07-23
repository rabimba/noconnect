package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.client.MessageTracker.GroupCount;
import org.chagolchana.noconnect.api.messaging.ConversationManager;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

@ThreadSafe
@NotNullByDefault
class ConversationManagerImpl implements ConversationManager {

	private final DatabaseComponent db;
	private final Set<ConversationClient> clients;

	@Inject
	ConversationManagerImpl(DatabaseComponent db) {
		this.db = db;
		clients = new CopyOnWriteArraySet<ConversationClient>();
	}

	@Override
	public void registerConversationClient(ConversationClient client) {
		if (!clients.add(client))
			throw new IllegalStateException("Client is already registered");
	}

	@Override
	public GroupCount getGroupCount(ContactId contactId) throws DbException {
		int msgCount = 0, unreadCount = 0;
		long latestTime = 0;
		Transaction txn = db.startTransaction(true);
		try {
			for (ConversationClient client : clients) {
				GroupCount count = client.getGroupCount(txn, contactId);
				msgCount += count.getMsgCount();
				unreadCount += count.getUnreadCount();
				if (count.getLatestMsgTime() > latestTime)
					latestTime = count.getLatestMsgTime();
			}
			db.commitTransaction(txn);
		} finally {
			db.endTransaction(txn);
		}
		return new GroupCount(msgCount, unreadCount, latestTime);
	}

}
