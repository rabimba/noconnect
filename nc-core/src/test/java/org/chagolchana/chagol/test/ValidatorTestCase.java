package org.chagolchana.chagol.test;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.system.Clock;

public abstract class ValidatorTestCase extends BrambleMockTestCase {

	protected final ClientHelper clientHelper =
			context.mock(ClientHelper.class);
	protected final MetadataEncoder metadataEncoder =
			context.mock(MetadataEncoder.class);
	protected final Clock clock = context.mock(Clock.class);
	protected final AuthorFactory authorFactory =
			context.mock(AuthorFactory.class);

	protected final MessageId messageId =
			new MessageId(TestUtils.getRandomId());
	protected final GroupId groupId = new GroupId(TestUtils.getRandomId());
	protected final long timestamp = 1234567890 * 1000L;
	protected final byte[] raw = TestUtils.getRandomBytes(123);
	protected final Message message =
			new Message(messageId, groupId, timestamp, raw);
	protected final ClientId clientId =
			new ClientId(TestUtils.getRandomString(123));
	protected final byte[] descriptor = TestUtils.getRandomBytes(123);
	protected final Group group = new Group(groupId, clientId, descriptor);

}
