package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.BdfMessageContext;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.test.TestUtils;
import org.chagolchana.noconnect.api.forum.Forum;
import org.jmock.Expectations;
import org.junit.Test;

import static org.chagolchana.noconnect.api.forum.ForumConstants.FORUM_SALT_LENGTH;
import static org.chagolchana.noconnect.api.forum.ForumConstants.MAX_FORUM_NAME_LENGTH;
import static org.chagolchana.noconnect.api.sharing.SharingConstants.MAX_INVITATION_MESSAGE_LENGTH;
import static org.chagolchana.noconnect.sharing.MessageType.INVITE;

public class ForumSharingValidatorTest extends SharingValidatorTest {

	private final String forumName =
			TestUtils.getRandomString(MAX_FORUM_NAME_LENGTH);
	private final byte[] salt = TestUtils.getRandomBytes(FORUM_SALT_LENGTH);
	private final Forum forum = new Forum(group, forumName, salt);
	private final BdfList descriptor = BdfList.of(forumName, salt);
	private final String content =
			TestUtils.getRandomString(MAX_INVITATION_MESSAGE_LENGTH);

	@Override
	SharingValidator getValidator() {
		return new ForumSharingValidator(messageEncoder, clientHelper,
				metadataEncoder, clock, forumFactory);
	}

	@Test
	public void testAcceptsInvitationWithContent() throws Exception {
		expectCreateForum(forumName);
		expectEncodeMetadata(INVITE);
		BdfMessageContext messageContext = v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, descriptor,
						content));
		assertExpectedContext(messageContext, previousMsgId);
	}

	@Test
	public void testAcceptsInvitationWithNullContent() throws Exception {
		expectCreateForum(forumName);
		expectEncodeMetadata(INVITE);
		BdfMessageContext messageContext = v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, descriptor, null));
		assertExpectedContext(messageContext, previousMsgId);
	}

	@Test
	public void testAcceptsInvitationWithNullPreviousMsgId() throws Exception {
		expectCreateForum(forumName);
		expectEncodeMetadata(INVITE);
		BdfMessageContext messageContext = v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), null, descriptor, null));
		assertExpectedContext(messageContext, null);
	}

	@Test(expected = FormatException.class)
	public void testRejectsNullForumName() throws Exception {
		BdfList invalidDescriptor = BdfList.of(null, salt);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test(expected = FormatException.class)
	public void testRejectsNonStringForumName() throws Exception {
		BdfList invalidDescriptor = BdfList.of(123, salt);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test(expected = FormatException.class)
	public void testRejectsTooShortForumName() throws Exception {
		BdfList invalidDescriptor = BdfList.of("", salt);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test
	public void testAcceptsMinLengthForumName() throws Exception {
		String shortForumName = TestUtils.getRandomString(1);
		BdfList validDescriptor = BdfList.of(shortForumName, salt);
		expectCreateForum(shortForumName);
		expectEncodeMetadata(INVITE);
		BdfMessageContext messageContext = v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, validDescriptor,
						null));
		assertExpectedContext(messageContext, previousMsgId);
	}

	@Test(expected = FormatException.class)
	public void testRejectsTooLongForumName() throws Exception {
		String invalidForumName =
				TestUtils.getRandomString(MAX_FORUM_NAME_LENGTH + 1);
		BdfList invalidDescriptor = BdfList.of(invalidForumName, salt);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test(expected = FormatException.class)
	public void testRejectsNullSalt() throws Exception {
		BdfList invalidDescriptor = BdfList.of(forumName, null);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test(expected = FormatException.class)
	public void testRejectsNonRawSalt() throws Exception {
		BdfList invalidDescriptor = BdfList.of(forumName, 123);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test(expected = FormatException.class)
	public void testRejectsTooShortSalt() throws Exception {
		byte[] invalidSalt = TestUtils.getRandomBytes(FORUM_SALT_LENGTH - 1);
		BdfList invalidDescriptor = BdfList.of(forumName, invalidSalt);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test(expected = FormatException.class)
	public void testRejectsTooLongSalt() throws Exception {
		byte[] invalidSalt = TestUtils.getRandomBytes(FORUM_SALT_LENGTH + 1);
		BdfList invalidDescriptor = BdfList.of(forumName, invalidSalt);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, invalidDescriptor,
						null));
	}

	@Test(expected = FormatException.class)
	public void testRejectsNonStringContent() throws Exception {
		expectCreateForum(forumName);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, descriptor,
						123));
	}

	@Test
	public void testAcceptsMinLengthContent() throws Exception {
		expectCreateForum(forumName);
		expectEncodeMetadata(INVITE);
		BdfMessageContext messageContext = v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, descriptor, "1"));
		assertExpectedContext(messageContext, previousMsgId);
	}

	@Test(expected = FormatException.class)
	public void testRejectsTooLongContent() throws Exception {
		String invalidContent =
				TestUtils.getRandomString(MAX_INVITATION_MESSAGE_LENGTH + 1);
		expectCreateForum(forumName);
		v.validateMessage(message, group,
				BdfList.of(INVITE.getValue(), previousMsgId, descriptor,
						invalidContent));
	}

	private void expectCreateForum(final String name) {
		context.checking(new Expectations() {{
			oneOf(forumFactory).createForum(name, salt);
			will(returnValue(forum));
		}});
	}

}
