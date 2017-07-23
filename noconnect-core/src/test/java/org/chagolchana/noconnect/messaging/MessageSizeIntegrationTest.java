package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.api.UniqueId;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.crypto.PrivateKey;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.system.SystemModule;
import org.chagolchana.chagol.test.TestUtils;
import org.chagolchana.chagol.util.StringUtils;
import org.chagolchana.noconnect.api.forum.ForumConstants;
import org.chagolchana.noconnect.api.forum.ForumPost;
import org.chagolchana.noconnect.api.forum.ForumPostFactory;
import org.chagolchana.noconnect.api.messaging.PrivateMessage;
import org.chagolchana.noconnect.api.messaging.PrivateMessageFactory;
import org.chagolchana.noconnect.test.BriarTestCase;
import org.junit.Test;

import javax.inject.Inject;

import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_AUTHOR_NAME_LENGTH;
import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_PUBLIC_KEY_LENGTH;
import static org.chagolchana.chagol.test.TestUtils.getRandomId;
import static org.chagolchana.chagol.api.sync.SyncConstants.MAX_RECORD_PAYLOAD_LENGTH;
import static org.chagolchana.noconnect.api.forum.ForumConstants.MAX_FORUM_POST_BODY_LENGTH;
import static org.chagolchana.noconnect.api.messaging.MessagingConstants.MAX_PRIVATE_MESSAGE_BODY_LENGTH;
import static org.junit.Assert.assertTrue;

public class MessageSizeIntegrationTest extends BriarTestCase {

	@Inject
	CryptoComponent crypto;
	@Inject
	AuthorFactory authorFactory;
	@Inject
	PrivateMessageFactory privateMessageFactory;
	@Inject
	ForumPostFactory forumPostFactory;

	public MessageSizeIntegrationTest() throws Exception {
		MessageSizeIntegrationTestComponent component =
				DaggerMessageSizeIntegrationTestComponent.builder().build();
		component.inject(this);
		injectEagerSingletons(component);
	}

	@Test
	public void testPrivateMessageFitsIntoPacket() throws Exception {
		// Create a maximum-length private message
		GroupId groupId = new GroupId(getRandomId());
		long timestamp = Long.MAX_VALUE;
		String body =
				StringUtils.fromUtf8(new byte[MAX_PRIVATE_MESSAGE_BODY_LENGTH]);
		PrivateMessage message = privateMessageFactory.createPrivateMessage(
				groupId, timestamp, body);
		// Check the size of the serialised message
		int length = message.getMessage().getRaw().length;
		assertTrue(
				length > UniqueId.LENGTH + 8 + MAX_PRIVATE_MESSAGE_BODY_LENGTH);
		assertTrue(length <= MAX_RECORD_PAYLOAD_LENGTH);
	}

	@Test
	public void testForumPostFitsIntoPacket() throws Exception {
		// Create a maximum-length author
		String authorName = TestUtils.getRandomString(
				MAX_AUTHOR_NAME_LENGTH);
		byte[] authorPublic = new byte[MAX_PUBLIC_KEY_LENGTH];
		PrivateKey privateKey = crypto.generateSignatureKeyPair().getPrivate();
		LocalAuthor author = authorFactory
				.createLocalAuthor(authorName, authorPublic,
						privateKey.getEncoded());
		// Create a maximum-length forum post
		GroupId groupId = new GroupId(getRandomId());
		long timestamp = Long.MAX_VALUE;
		MessageId parent = new MessageId(getRandomId());
		String body = TestUtils.getRandomString(MAX_FORUM_POST_BODY_LENGTH);
		ForumPost post = forumPostFactory.createPost(groupId,
				timestamp, parent, author, body);
		// Check the size of the serialised message
		int length = post.getMessage().getRaw().length;
		assertTrue(length > UniqueId.LENGTH + 8 + UniqueId.LENGTH
				+ MAX_AUTHOR_NAME_LENGTH + MAX_PUBLIC_KEY_LENGTH
				+ ForumConstants.MAX_CONTENT_TYPE_LENGTH
				+ MAX_FORUM_POST_BODY_LENGTH);
		assertTrue(length <= MAX_RECORD_PAYLOAD_LENGTH);
	}

	private static void injectEagerSingletons(
			MessageSizeIntegrationTestComponent component) {
		component.inject(new SystemModule.EagerSingletons());
	}
}
