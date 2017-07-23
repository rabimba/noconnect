package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.client.ContactGroupFactory;
import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.contact.ContactManager.AddContactHook;
import org.chagolchana.chagol.api.contact.ContactManager.RemoveContactHook;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataParser;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Client;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.sync.MessageStatus;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.messaging.MessagingManager;
import org.chagolchana.noconnect.api.messaging.PrivateMessage;
import org.chagolchana.noconnect.api.messaging.PrivateMessageHeader;
import org.chagolchana.noconnect.api.messaging.event.PrivateMessageReceivedEvent;
import org.chagolchana.noconnect.client.ConversationClientImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static org.chagolchana.chagol.api.sync.Group.Visibility.SHARED;
import static org.chagolchana.noconnect.client.MessageTrackerConstants.MSG_KEY_READ;

@Immutable
@NotNullByDefault
class MessagingManagerImpl extends ConversationClientImpl
		implements MessagingManager, Client, AddContactHook, RemoveContactHook {

	private final ContactGroupFactory contactGroupFactory;

	@Inject
	MessagingManagerImpl(DatabaseComponent db, ClientHelper clientHelper,
			MetadataParser metadataParser, MessageTracker messageTracker,
			ContactGroupFactory contactGroupFactory) {
		super(db, clientHelper, metadataParser, messageTracker);
		this.contactGroupFactory = contactGroupFactory;
	}

	@Override
	public void createLocalState(Transaction txn) throws DbException {
		// Ensure we've set things up for any pre-existing contacts
		for (Contact c : db.getContacts(txn)) addingContact(txn, c);
	}

	@Override
	public void addingContact(Transaction txn, Contact c) throws DbException {
		try {
			// Create a group to share with the contact
			Group g = getContactGroup(c);
			// Return if we've already set things up for this contact
			if (db.containsGroup(txn, g.getId())) return;
			// Store the group and share it with the contact
			db.addGroup(txn, g);
			db.setGroupVisibility(txn, c.getId(), g.getId(), SHARED);
			// Attach the contact ID to the group
			BdfDictionary d = new BdfDictionary();
			d.put("contactId", c.getId().getInt());
			clientHelper.mergeGroupMetadata(txn, g.getId(), d);
		} catch (FormatException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Group getContactGroup(Contact c) {
		return contactGroupFactory.createContactGroup(CLIENT_ID, c);
	}

	@Override
	public void removingContact(Transaction txn, Contact c) throws DbException {
		db.removeGroup(txn, getContactGroup(c));
	}

	@Override
	protected boolean incomingMessage(Transaction txn, Message m, BdfList body,
			BdfDictionary meta) throws DbException, FormatException {

		GroupId groupId = m.getGroupId();
		long timestamp = meta.getLong("timestamp");
		boolean local = meta.getBoolean("local");
		boolean read = meta.getBoolean(MSG_KEY_READ);
		PrivateMessageHeader header = new PrivateMessageHeader(
				m.getId(), groupId, timestamp, local, read, false, false);
		ContactId contactId = getContactId(txn, groupId);
		PrivateMessageReceivedEvent event = new PrivateMessageReceivedEvent(
				header, contactId, groupId);
		txn.attach(event);
		messageTracker.trackIncomingMessage(txn, m);

		// don't share message
		return false;
	}

	@Override
	public void addLocalMessage(PrivateMessage m) throws DbException {
		Transaction txn = db.startTransaction(false);
		try {
			BdfDictionary meta = new BdfDictionary();
			meta.put("timestamp", m.getMessage().getTimestamp());
			meta.put("local", true);
			meta.put("read", true);
			clientHelper.addLocalMessage(txn, m.getMessage(), meta, true);
			messageTracker.trackOutgoingMessage(txn, m.getMessage());
			db.commitTransaction(txn);
		} catch (FormatException e) {
			throw new RuntimeException(e);
		} finally {
			db.endTransaction(txn);
		}
	}

	private ContactId getContactId(Transaction txn, GroupId g)
			throws DbException {
		try {
			BdfDictionary meta =
					clientHelper.getGroupMetadataAsDictionary(txn, g);
			return new ContactId(meta.getLong("contactId").intValue());
		} catch (FormatException e) {
			throw new DbException(e);
		}
	}

	@Override
	public ContactId getContactId(GroupId g) throws DbException {
		try {
			BdfDictionary meta = clientHelper.getGroupMetadataAsDictionary(g);
			return new ContactId(meta.getLong("contactId").intValue());
		} catch (FormatException e) {
			throw new DbException(e);
		}
	}

	@Override
	public GroupId getConversationId(ContactId c) throws DbException {
		Contact contact;
		Transaction txn = db.startTransaction(true);
		try {
			contact = db.getContact(txn, c);
			db.commitTransaction(txn);
		} finally {
			db.endTransaction(txn);
		}
		return getContactGroup(contact).getId();
	}

	@Override
	public Collection<PrivateMessageHeader> getMessageHeaders(ContactId c)
			throws DbException {
		Map<MessageId, BdfDictionary> metadata;
		Collection<MessageStatus> statuses;
		GroupId g;
		Transaction txn = db.startTransaction(true);
		try {
			g = getContactGroup(db.getContact(txn, c)).getId();
			metadata = clientHelper.getMessageMetadataAsDictionary(txn, g);
			statuses = db.getMessageStatus(txn, c, g);
			db.commitTransaction(txn);
		} catch (FormatException e) {
			throw new DbException(e);
		} finally {
			db.endTransaction(txn);
		}
		Collection<PrivateMessageHeader> headers =
				new ArrayList<PrivateMessageHeader>();
		for (MessageStatus s : statuses) {
			MessageId id = s.getMessageId();
			BdfDictionary meta = metadata.get(id);
			if (meta == null) continue;
			try {
				long timestamp = meta.getLong("timestamp");
				boolean local = meta.getBoolean("local");
				boolean read = meta.getBoolean("read");
				headers.add(
						new PrivateMessageHeader(id, g, timestamp, local, read,
								s.isSent(), s.isSeen()));
			} catch (FormatException e) {
				throw new DbException(e);
			}
		}
		return headers;
	}

	@Override
	public String getMessageBody(MessageId m) throws DbException {
		try {
			// 0: private message body
			BdfList message = clientHelper.getMessageAsList(m);
			if (message == null) throw new DbException();
			return message.getString(0);
		} catch (FormatException e) {
			throw new DbException(e);
		}
	}

}
