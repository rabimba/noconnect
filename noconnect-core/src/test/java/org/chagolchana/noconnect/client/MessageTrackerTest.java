package org.chagolchana.noconnect.client;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfEntry;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.test.BrambleMockTestCase;
import org.chagolchana.chagol.test.TestUtils;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.jmock.Expectations;
import org.junit.Assert;
import org.junit.Test;

import static org.chagolchana.noconnect.client.MessageTrackerConstants.GROUP_KEY_STORED_MESSAGE_ID;

public class MessageTrackerTest extends BrambleMockTestCase {

	protected final GroupId groupId = new GroupId(TestUtils.getRandomId());
	protected final ClientHelper clientHelper =
			context.mock(ClientHelper.class);
	private final DatabaseComponent db = context.mock(DatabaseComponent.class);
	private final MessageId messageId = new MessageId(TestUtils.getRandomId());
	private final MessageTracker messageTracker =
			new MessageTrackerImpl(db, clientHelper);
	private final BdfDictionary dictionary = BdfDictionary.of(
			new BdfEntry(GROUP_KEY_STORED_MESSAGE_ID, messageId)
	);

	@Test
	public void testMessageStore() throws Exception {
		context.checking(new Expectations() {{
			oneOf(clientHelper).mergeGroupMetadata(groupId, dictionary);
		}});
		messageTracker.storeMessageId(groupId, messageId);
	}

	@Test
	public void testMessageLoad() throws Exception {
		context.checking(new Expectations() {{
			oneOf(clientHelper).getGroupMetadataAsDictionary(groupId);
			will(returnValue(dictionary));
		}});
		MessageId loadedId = messageTracker.loadStoredMessageId(groupId);
		Assert.assertNotNull(loadedId);
		Assert.assertTrue(messageId.equals(loadedId));
	}

}
