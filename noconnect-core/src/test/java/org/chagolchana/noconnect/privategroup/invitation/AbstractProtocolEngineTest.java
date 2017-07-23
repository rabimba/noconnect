package org.chagolchana.noconnect.privategroup.invitation;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfEntry;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.chagol.test.BrambleMockTestCase;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.privategroup.GroupMessageFactory;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;
import org.chagolchana.noconnect.api.privategroup.PrivateGroupFactory;
import org.chagolchana.noconnect.api.privategroup.PrivateGroupManager;
import org.jmock.Expectations;

import static org.chagolchana.chagol.test.TestUtils.getRandomBytes;
import static org.chagolchana.chagol.test.TestUtils.getRandomId;
import static org.chagolchana.noconnect.api.privategroup.PrivateGroupManager.CLIENT_ID;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.GROUP_KEY_CONTACT_ID;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.ABORT;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.INVITE;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.JOIN;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.LEAVE;
import static org.junit.Assert.assertEquals;

public abstract class AbstractProtocolEngineTest extends BrambleMockTestCase {

	protected final DatabaseComponent db =
			context.mock(DatabaseComponent.class);
	protected final ClientHelper clientHelper =
			context.mock(ClientHelper.class);
	protected final PrivateGroupFactory privateGroupFactory =
			context.mock(PrivateGroupFactory.class);
	protected final PrivateGroupManager privateGroupManager =
			context.mock(PrivateGroupManager.class);
	protected final MessageParser messageParser =
			context.mock(MessageParser.class);
	protected final GroupMessageFactory groupMessageFactory =
			context.mock(GroupMessageFactory.class);
	protected final IdentityManager identityManager =
			context.mock(IdentityManager.class);
	protected final MessageEncoder messageEncoder =
			context.mock(MessageEncoder.class);
	protected final MessageTracker messageTracker =
			context.mock(MessageTracker.class);
	protected final Clock clock = context.mock(Clock.class);

	protected final Transaction txn = new Transaction(null, false);
	protected final GroupId contactGroupId = new GroupId(getRandomId());
	protected final GroupId privateGroupId = new GroupId(getRandomId());
	protected final Group privateGroupGroup =
			new Group(privateGroupId, CLIENT_ID, getRandomBytes(5));
	private final AuthorId authorId = new AuthorId(getRandomId());
	protected final Author author =
			new Author(authorId, "Author", getRandomBytes(12));
	protected final PrivateGroup privateGroup =
			new PrivateGroup(privateGroupGroup, "Private Group", author,
					getRandomBytes(8));
	protected final byte[] signature = getRandomBytes(42);
	protected final MessageId lastLocalMessageId = new MessageId(getRandomId());
	protected final MessageId lastRemoteMessageId =
			new MessageId(getRandomId());
	protected final long localTimestamp = 3L;
	protected final long inviteTimestamp = 6L;
	protected final long messageTimestamp = inviteTimestamp + 1;
	protected final MessageId messageId = new MessageId(getRandomId());
	protected final Message message =
			new Message(messageId, contactGroupId, messageTimestamp,
					getRandomBytes(42));
	private final BdfDictionary meta =
			BdfDictionary.of(new BdfEntry("me", "ta"));
	protected final ContactId contactId = new ContactId(5);
	protected final Contact contact =
			new Contact(contactId, author, new AuthorId(getRandomId()), true,
					true);

	protected final InviteMessage inviteMessage =
			new InviteMessage(new MessageId(getRandomId()), contactGroupId,
					privateGroupId, 0L, privateGroup.getName(),
					privateGroup.getCreator(), privateGroup.getSalt(), "msg",
					signature);
	protected final JoinMessage joinMessage =
			new JoinMessage(new MessageId(getRandomId()), contactGroupId,
					privateGroupId, 0L, lastRemoteMessageId);
	protected final LeaveMessage leaveMessage =
			new LeaveMessage(new MessageId(getRandomId()), contactGroupId,
					privateGroupId, 0L, lastRemoteMessageId);
	protected final AbortMessage abortMessage =
			new AbortMessage(messageId, contactGroupId, privateGroupId,
					inviteTimestamp + 1);

	protected void assertSessionConstantsUnchanged(Session s1, Session s2) {
		assertEquals(s1.getRole(), s2.getRole());
		assertEquals(s1.getContactGroupId(), s2.getContactGroupId());
		assertEquals(s1.getPrivateGroupId(), s2.getPrivateGroupId());
	}

	protected void assertSessionRecordedSentMessage(Session s) {
		assertEquals(messageId, s.getLastLocalMessageId());
		assertEquals(lastRemoteMessageId, s.getLastRemoteMessageId());
		assertEquals(messageTimestamp, s.getLocalTimestamp());
		assertEquals(inviteTimestamp, s.getInviteTimestamp());
	}

	protected void expectGetLocalTimestamp(final long time) {
		context.checking(new Expectations() {{
			oneOf(clock).currentTimeMillis();
			will(returnValue(time));
		}});
	}

	protected void expectSendInviteMessage(final String msg)
			throws Exception {
		context.checking(new Expectations() {{
			oneOf(messageEncoder)
					.encodeInviteMessage(contactGroupId, privateGroupId,
							inviteTimestamp, privateGroup.getName(), author,
							privateGroup.getSalt(), msg, signature);
			will(returnValue(message));
		}});
		expectSendMessage(INVITE, true);
	}

	protected void expectSendJoinMessage(final JoinMessage m, boolean visible)
			throws Exception {
		expectGetLocalTimestamp(messageTimestamp);
		context.checking(new Expectations() {{
			oneOf(messageEncoder).encodeJoinMessage(m.getContactGroupId(),
					m.getPrivateGroupId(), m.getTimestamp(),
					lastLocalMessageId);
			will(returnValue(message));
		}});
		expectSendMessage(JOIN, visible);
	}

	protected void expectSendLeaveMessage(boolean visible) throws Exception {
		expectGetLocalTimestamp(messageTimestamp);
		context.checking(new Expectations() {{
			oneOf(messageEncoder)
					.encodeLeaveMessage(contactGroupId, privateGroupId,
							messageTimestamp, lastLocalMessageId);
			will(returnValue(message));
		}});
		expectSendMessage(LEAVE, visible);
	}

	protected void expectSendAbortMessage() throws Exception {
		expectGetLocalTimestamp(messageTimestamp);
		context.checking(new Expectations() {{
			oneOf(messageEncoder)
					.encodeAbortMessage(contactGroupId, privateGroupId,
							messageTimestamp);
			will(returnValue(message));
		}});
		expectSendMessage(ABORT, false);
	}

	private void expectSendMessage(final MessageType type,
			final boolean visible) throws Exception {
		context.checking(new Expectations() {{
			oneOf(messageEncoder).encodeMetadata(type, privateGroupId,
					message.getTimestamp(), true, true, visible, false, false);
			will(returnValue(meta));
			oneOf(clientHelper).addLocalMessage(txn, message, meta, true);
		}});
	}

	protected void expectSetPrivateGroupVisibility(final Group.Visibility v)
			throws Exception {
		expectGetContactId();
		context.checking(new Expectations() {{
			oneOf(db).setGroupVisibility(txn, contactId, privateGroupId, v);
		}});
	}

	protected void expectGetContactId() throws Exception {
		final BdfDictionary groupMeta = BdfDictionary
				.of(new BdfEntry(GROUP_KEY_CONTACT_ID, contactId.getInt()));
		context.checking(new Expectations() {{
			oneOf(clientHelper)
					.getGroupMetadataAsDictionary(txn, contactGroupId);
			will(returnValue(groupMeta));
		}});
	}

	protected void expectIsSubscribedPrivateGroup()
			throws Exception {
		context.checking(new Expectations() {{
			oneOf(db).containsGroup(txn, privateGroupId);
			will(returnValue(true));
			oneOf(db).getGroup(txn, privateGroupId);
			will(returnValue(privateGroupGroup));
		}});
	}

	protected void expectIsNotSubscribedPrivateGroup()
			throws Exception {
		context.checking(new Expectations() {{
			oneOf(db).containsGroup(txn, privateGroupId);
			will(returnValue(false));
		}});
	}

	protected void expectMarkMessageVisibleInUi(final MessageId m,
			final boolean visible)
			throws Exception {
		final BdfDictionary d = new BdfDictionary();
		context.checking(new Expectations() {{
			oneOf(messageEncoder).setVisibleInUi(d, visible);
			oneOf(clientHelper).mergeMessageMetadata(txn, m, d);
		}});
	}

}
