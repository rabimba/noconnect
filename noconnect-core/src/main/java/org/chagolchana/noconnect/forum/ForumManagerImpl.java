package org.chagolchana.noconnect.forum;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataParser;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.Author.Status;
import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.client.MessageTracker.GroupCount;
import org.chagolchana.noconnect.api.forum.Forum;
import org.chagolchana.noconnect.api.forum.ForumFactory;
import org.chagolchana.noconnect.api.forum.ForumManager;
import org.chagolchana.noconnect.api.forum.ForumPost;
import org.chagolchana.noconnect.api.forum.ForumPostFactory;
import org.chagolchana.noconnect.api.forum.ForumPostHeader;
import org.chagolchana.noconnect.api.forum.event.ForumPostReceivedEvent;
import org.chagolchana.noconnect.client.BdfIncomingMessageHook;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

import static org.chagolchana.chagol.api.identity.Author.Status.ANONYMOUS;
import static org.chagolchana.chagol.api.identity.Author.Status.OURSELVES;
import static org.chagolchana.noconnect.api.forum.ForumConstants.KEY_AUTHOR;
import static org.chagolchana.noconnect.api.forum.ForumConstants.KEY_ID;
import static org.chagolchana.noconnect.api.forum.ForumConstants.KEY_LOCAL;
import static org.chagolchana.noconnect.api.forum.ForumConstants.KEY_NAME;
import static org.chagolchana.noconnect.api.forum.ForumConstants.KEY_PARENT;
import static org.chagolchana.noconnect.api.forum.ForumConstants.KEY_PUBLIC_NAME;
import static org.chagolchana.noconnect.api.forum.ForumConstants.KEY_TIMESTAMP;
import static org.chagolchana.noconnect.client.MessageTrackerConstants.MSG_KEY_READ;

@ThreadSafe
@NotNullByDefault
class ForumManagerImpl extends BdfIncomingMessageHook implements ForumManager {

	private final IdentityManager identityManager;
	private final ForumFactory forumFactory;
	private final ForumPostFactory forumPostFactory;
	private final MessageTracker messageTracker;
	private final List<RemoveForumHook> removeHooks;

	@Inject
	ForumManagerImpl(DatabaseComponent db, IdentityManager identityManager,
			ClientHelper clientHelper, MetadataParser metadataParser,
			ForumFactory forumFactory, ForumPostFactory forumPostFactory,
			MessageTracker messageTracker) {
		super(db, clientHelper, metadataParser);
		this.identityManager = identityManager;
		this.forumFactory = forumFactory;
		this.forumPostFactory = forumPostFactory;
		this.messageTracker = messageTracker;
		removeHooks = new CopyOnWriteArrayList<RemoveForumHook>();
	}

	@Override
	protected boolean incomingMessage(Transaction txn, Message m, BdfList body,
			BdfDictionary meta) throws DbException, FormatException {

		messageTracker.trackIncomingMessage(txn, m);

		ForumPostHeader post = getForumPostHeader(txn, m.getId(), meta);
		ForumPostReceivedEvent event =
				new ForumPostReceivedEvent(post, m.getGroupId());
		txn.attach(event);

		// share message
		return true;
	}

	@Override
	public Forum addForum(String name) throws DbException {
		Forum f = forumFactory.createForum(name);

		Transaction txn = db.startTransaction(false);
		try {
			db.addGroup(txn, f.getGroup());
			db.commitTransaction(txn);
		} finally {
			db.endTransaction(txn);
		}
		return f;
	}

	@Override
	public void addForum(Transaction txn, Forum f) throws DbException {
		db.addGroup(txn, f.getGroup());
	}

	@Override
	public void removeForum(Forum f) throws DbException {
		Transaction txn = db.startTransaction(false);
		try {
			for (RemoveForumHook hook : removeHooks)
				hook.removingForum(txn, f);
			db.removeGroup(txn, f.getGroup());
			db.commitTransaction(txn);
		} finally {
			db.endTransaction(txn);
		}
	}

	@Override
	public ForumPost createLocalPost(final GroupId groupId, final String body,
			final long timestamp, final @Nullable MessageId parentId,
			final LocalAuthor author) {
		ForumPost p;
		try {
			p = forumPostFactory.createPost(groupId, timestamp, parentId,
					author, body);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		} catch (FormatException e) {
			throw new RuntimeException(e);
		}
		return p;
	}

	@Override
	public ForumPostHeader addLocalPost(ForumPost p) throws DbException {
		Transaction txn = db.startTransaction(false);
		try {
			BdfDictionary meta = new BdfDictionary();
			meta.put(KEY_TIMESTAMP, p.getMessage().getTimestamp());
			if (p.getParent() != null) meta.put(KEY_PARENT, p.getParent());
			Author a = p.getAuthor();
			BdfDictionary authorMeta = new BdfDictionary();
			authorMeta.put(KEY_ID, a.getId());
			authorMeta.put(KEY_NAME, a.getName());
			authorMeta.put(KEY_PUBLIC_NAME, a.getPublicKey());
			meta.put(KEY_AUTHOR, authorMeta);
			meta.put(KEY_LOCAL, true);
			meta.put(MSG_KEY_READ, true);
			clientHelper.addLocalMessage(txn, p.getMessage(), meta, true);
			messageTracker.trackOutgoingMessage(txn, p.getMessage());
			db.commitTransaction(txn);
		} catch (FormatException e) {
			throw new RuntimeException(e);
		} finally {
			db.endTransaction(txn);
		}
		return new ForumPostHeader(p.getMessage().getId(), p.getParent(),
				p.getMessage().getTimestamp(), p.getAuthor(), OURSELVES, true);
	}

	@Override
	public Forum getForum(GroupId g) throws DbException {
		Forum forum;
		Transaction txn = db.startTransaction(true);
		try {
			forum = getForum(txn, g);
			db.commitTransaction(txn);
		} finally {
			db.endTransaction(txn);
		}
		return forum;
	}

	@Override
	public Forum getForum(Transaction txn, GroupId g) throws DbException {
		try {
			Group group = db.getGroup(txn, g);
			return parseForum(group);
		} catch (FormatException e) {
			throw new DbException(e);
		}
	}

	@Override
	public Collection<Forum> getForums() throws DbException {
		try {
			Collection<Group> groups;
			Transaction txn = db.startTransaction(true);
			try {
				groups = db.getGroups(txn, CLIENT_ID);
				db.commitTransaction(txn);
			} finally {
				db.endTransaction(txn);
			}
			List<Forum> forums = new ArrayList<Forum>();
			for (Group g : groups) forums.add(parseForum(g));
			return forums;
		} catch (FormatException e) {
			throw new DbException(e);
		}
	}

	@Override
	public String getPostBody(MessageId m) throws DbException {
		try {
			// Parent ID, author, forum post body, signature
			BdfList message = clientHelper.getMessageAsList(m);
			if (message == null) throw new DbException();
			return message.getString(2);
		} catch (FormatException e) {
			throw new DbException(e);
		}
	}

	@Override
	public Collection<ForumPostHeader> getPostHeaders(GroupId g)
			throws DbException {

		Collection<ForumPostHeader> headers = new ArrayList<ForumPostHeader>();
		Transaction txn = db.startTransaction(true);
		try {
			Map<MessageId, BdfDictionary> metadata =
					clientHelper.getMessageMetadataAsDictionary(txn, g);
			// get all authors we need to get the status for
			Set<AuthorId> authors = new HashSet<AuthorId>();
			for (Entry<MessageId, BdfDictionary> entry : metadata.entrySet()) {
				BdfDictionary d =
						entry.getValue().getDictionary(KEY_AUTHOR, null);
				if (d != null)
					authors.add(new AuthorId(d.getRaw(KEY_ID)));
			}
			// get statuses for all authors
			Map<AuthorId, Status> statuses = new HashMap<AuthorId, Status>();
			for (AuthorId id : authors) {
				statuses.put(id, identityManager.getAuthorStatus(txn, id));
			}
			// Parse the metadata
			for (Entry<MessageId, BdfDictionary> entry : metadata.entrySet()) {
				BdfDictionary meta = entry.getValue();
				headers.add(getForumPostHeader(txn, entry.getKey(), meta,
						statuses));
			}
			db.commitTransaction(txn);
			return headers;
		} catch (FormatException e) {
			throw new DbException(e);
		} finally {
			db.endTransaction(txn);
		}
	}

	@Override
	public void registerRemoveForumHook(RemoveForumHook hook) {
		removeHooks.add(hook);
	}

	@Override
	public GroupCount getGroupCount(GroupId g) throws DbException {
		return messageTracker.getGroupCount(g);
	}

	@Override
	public void setReadFlag(GroupId g, MessageId m, boolean read)
			throws DbException {
		messageTracker.setReadFlag(g, m, read);
	}

	private Forum parseForum(Group g) throws FormatException {
		byte[] descriptor = g.getDescriptor();
		// Name, salt
		BdfList forum = clientHelper.toList(descriptor);
		return new Forum(g, forum.getString(0), forum.getRaw(1));
	}

	private ForumPostHeader getForumPostHeader(Transaction txn, MessageId id,
			BdfDictionary meta) throws DbException, FormatException {
		return getForumPostHeader(txn, id, meta,
				Collections.<AuthorId, Status>emptyMap());
	}

	private ForumPostHeader getForumPostHeader(Transaction txn, MessageId id,
			BdfDictionary meta, Map<AuthorId, Status> statuses)
			throws DbException, FormatException {

		long timestamp = meta.getLong(KEY_TIMESTAMP);
		Author author = null;
		Status status = ANONYMOUS;
		MessageId parentId = null;
		if (meta.containsKey(KEY_PARENT))
			parentId = new MessageId(meta.getRaw(KEY_PARENT));
		// TODO: Remove support for anonymous forum posts
		BdfDictionary d1 = meta.getDictionary(KEY_AUTHOR, null);
		if (d1 != null) {
			AuthorId authorId = new AuthorId(d1.getRaw(KEY_ID));
			String name = d1.getString(KEY_NAME);
			byte[] publicKey = d1.getRaw(KEY_PUBLIC_NAME);
			author = new Author(authorId, name, publicKey);
			if (statuses.containsKey(authorId)) {
				status = statuses.get(authorId);
			} else {
				status = identityManager.getAuthorStatus(txn, author.getId());
			}
		}
		boolean read = meta.getBoolean(MSG_KEY_READ);

		return new ForumPostHeader(id, parentId, timestamp, author, status,
				read);
	}

}
