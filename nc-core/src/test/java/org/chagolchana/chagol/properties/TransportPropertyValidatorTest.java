package org.chagolchana.chagol.properties;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.plugin.TransportId;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.chagol.test.BrambleTestCase;
import org.chagolchana.chagol.test.TestUtils;
import org.jmock.Mockery;
import org.junit.Test;

import java.io.IOException;

import static org.chagolchana.chagol.api.plugin.TransportId.MAX_TRANSPORT_ID_LENGTH;
import static org.chagolchana.chagol.api.properties.TransportPropertyConstants.MAX_PROPERTIES_PER_TRANSPORT;
import static org.junit.Assert.assertEquals;

public class TransportPropertyValidatorTest extends BrambleTestCase {

	private final TransportId transportId;
	private final BdfDictionary bdfDictionary;
	private final Group group;
	private final Message message;
	private final TransportPropertyValidator tpv;

	public TransportPropertyValidatorTest() {
		transportId = new TransportId("test");
		bdfDictionary = new BdfDictionary();

		GroupId groupId = new GroupId(TestUtils.getRandomId());
		ClientId clientId = new ClientId(TestUtils.getRandomString(5));
		byte[] descriptor = TestUtils.getRandomBytes(12);
		group = new Group(groupId, clientId, descriptor);

		MessageId messageId = new MessageId(TestUtils.getRandomId());
		long timestamp = System.currentTimeMillis();
		byte[] body = TestUtils.getRandomBytes(123);
		message = new Message(messageId, groupId, timestamp, body);

		Mockery context = new Mockery();
		ClientHelper clientHelper = context.mock(ClientHelper.class);
		MetadataEncoder metadataEncoder = context.mock(MetadataEncoder.class);
		Clock clock = context.mock(Clock.class);

		tpv = new TransportPropertyValidator(clientHelper, metadataEncoder,
				clock);
	}

	@Test
	public void testValidateProperMessage() throws IOException {

		BdfList body = BdfList.of(transportId.getString(), 4, bdfDictionary);

		BdfDictionary result = tpv.validateMessage(message, group, body)
				.getDictionary();

		assertEquals("test", result.getString("transportId"));
		assertEquals(4, result.getLong("version").longValue());
	}

	@Test(expected = FormatException.class)
	public void testValidateWrongVersionValue() throws IOException {

		BdfList body = BdfList.of(transportId.getString(), -1, bdfDictionary);
		tpv.validateMessage(message, group, body);
	}

	@Test(expected = FormatException.class)
	public void testValidateWrongVersionType() throws IOException {

		BdfList body = BdfList.of(transportId.getString(), bdfDictionary,
				bdfDictionary);
		tpv.validateMessage(message, group, body);
	}

	@Test(expected = FormatException.class)
	public void testValidateLongTransportId() throws IOException {

		String wrongTransportIdString =
				TestUtils.getRandomString(MAX_TRANSPORT_ID_LENGTH + 1);
		BdfList body = BdfList.of(wrongTransportIdString, 4, bdfDictionary);
		tpv.validateMessage(message, group, body);
	}

	@Test(expected = FormatException.class)
	public void testValidateEmptyTransportId() throws IOException {

		BdfList body = BdfList.of("", 4, bdfDictionary);
		tpv.validateMessage(message, group, body);
	}

	@Test(expected = FormatException.class)
	public void testValidateTooManyProperties() throws IOException {

		BdfDictionary d = new BdfDictionary();
		for (int i = 0; i < MAX_PROPERTIES_PER_TRANSPORT + 1; i++)
			d.put(String.valueOf(i), i);
		BdfList body = BdfList.of(transportId.getString(), 4, d);
		tpv.validateMessage(message, group, body);
	}
}
