package org.chagolchana.noconnect.introduction;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfEntry;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Metadata;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.chagol.test.TestUtils;
import org.chagolchana.noconnect.api.client.MessageQueueManager;
import org.chagolchana.noconnect.api.client.SessionId;
import org.chagolchana.noconnect.test.BriarTestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_SIGNATURE_LENGTH;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.GROUP_ID;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MAC;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.SESSION_ID;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.SIGNATURE;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE_ACK;
import static org.junit.Assert.assertFalse;

public class MessageSenderTest extends BriarTestCase {

	private final Mockery context;
	private final MessageSender messageSender;
	private final DatabaseComponent db;
	private final ClientHelper clientHelper;
	private final MetadataEncoder metadataEncoder;
	private final MessageQueueManager messageQueueManager;
	private final Clock clock;

	public MessageSenderTest() {
		context = new Mockery();
		db = context.mock(DatabaseComponent.class);
		clientHelper = context.mock(ClientHelper.class);
		metadataEncoder =
				context.mock(MetadataEncoder.class);
		messageQueueManager =
				context.mock(MessageQueueManager.class);
		clock = context.mock(Clock.class);

		messageSender = new MessageSender(db, clientHelper, clock,
				metadataEncoder, messageQueueManager);
	}

	@Test
	public void testSendMessage() throws DbException, FormatException {
		final Transaction txn = new Transaction(null, false);
		final Group privateGroup =
				new Group(new GroupId(TestUtils.getRandomId()),
						new ClientId(TestUtils.getRandomString(5)),
						new byte[0]);
		final SessionId sessionId = new SessionId(TestUtils.getRandomId());
		byte[] mac = TestUtils.getRandomBytes(42);
		byte[] sig = TestUtils.getRandomBytes(MAX_SIGNATURE_LENGTH);
		final long time = 42L;
		final BdfDictionary msg = BdfDictionary.of(
				new BdfEntry(TYPE, TYPE_ACK),
				new BdfEntry(GROUP_ID, privateGroup.getId()),
				new BdfEntry(SESSION_ID, sessionId),
				new BdfEntry(MAC, mac),
				new BdfEntry(SIGNATURE, sig)
		);
		final BdfList bodyList =
				BdfList.of(TYPE_ACK, sessionId.getBytes(), mac, sig);
		final byte[] body = TestUtils.getRandomBytes(8);
		final Metadata metadata = new Metadata();

		context.checking(new Expectations() {{
			oneOf(clientHelper).toByteArray(bodyList);
			will(returnValue(body));
			oneOf(db).getGroup(txn, privateGroup.getId());
			will(returnValue(privateGroup));
			oneOf(metadataEncoder).encode(msg);
			will(returnValue(metadata));
			oneOf(clock).currentTimeMillis();
			will(returnValue(time));
			oneOf(messageQueueManager)
					.sendMessage(txn, privateGroup, time, body, metadata);
		}});

		messageSender.sendMessage(txn, msg);

		context.assertIsSatisfied();
		assertFalse(txn.isCommitted());
	}

}
