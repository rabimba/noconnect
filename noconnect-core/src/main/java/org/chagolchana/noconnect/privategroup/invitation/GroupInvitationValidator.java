package org.chagolchana.noconnect.privategroup.invitation;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.UniqueId;
import org.chagolchana.chagol.api.client.BdfMessageContext;
import org.chagolchana.chagol.api.client.BdfMessageValidator;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;
import org.chagolchana.noconnect.api.privategroup.PrivateGroupFactory;

import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_AUTHOR_NAME_LENGTH;
import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_PUBLIC_KEY_LENGTH;
import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_SIGNATURE_LENGTH;
import static org.chagolchana.chagol.util.ValidationUtils.checkLength;
import static org.chagolchana.chagol.util.ValidationUtils.checkSize;
import static org.chagolchana.noconnect.api.privategroup.PrivateGroupConstants.GROUP_SALT_LENGTH;
import static org.chagolchana.noconnect.api.privategroup.PrivateGroupConstants.MAX_GROUP_INVITATION_MSG_LENGTH;
import static org.chagolchana.noconnect.api.privategroup.PrivateGroupConstants.MAX_GROUP_NAME_LENGTH;
import static org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationFactory.SIGNING_LABEL_INVITE;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.ABORT;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.INVITE;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.JOIN;
import static org.chagolchana.noconnect.privategroup.invitation.MessageType.LEAVE;

@Immutable
@NotNullByDefault
class GroupInvitationValidator extends BdfMessageValidator {

	private final AuthorFactory authorFactory;
	private final PrivateGroupFactory privateGroupFactory;
	private final MessageEncoder messageEncoder;

	@Inject
	GroupInvitationValidator(ClientHelper clientHelper,
			MetadataEncoder metadataEncoder, Clock clock,
			AuthorFactory authorFactory,
			PrivateGroupFactory privateGroupFactory,
			MessageEncoder messageEncoder) {
		super(clientHelper, metadataEncoder, clock);
		this.authorFactory = authorFactory;
		this.privateGroupFactory = privateGroupFactory;
		this.messageEncoder = messageEncoder;
	}

	@Override
	protected BdfMessageContext validateMessage(Message m, Group g,
			BdfList body) throws FormatException {
		MessageType type = MessageType.fromValue(body.getLong(0).intValue());
		switch (type) {
			case INVITE:
				return validateInviteMessage(m, body);
			case JOIN:
				return validateJoinMessage(m, body);
			case LEAVE:
				return validateLeaveMessage(m, body);
			case ABORT:
				return validateAbortMessage(m, body);
			default:
				throw new FormatException();
		}
	}

	private BdfMessageContext validateInviteMessage(Message m, BdfList body)
			throws FormatException {
		checkSize(body, 7);
		String groupName = body.getString(1);
		checkLength(groupName, 1, MAX_GROUP_NAME_LENGTH);
		String creatorName = body.getString(2);
		checkLength(creatorName, 1, MAX_AUTHOR_NAME_LENGTH);
		byte[] creatorPublicKey = body.getRaw(3);
		checkLength(creatorPublicKey, 1, MAX_PUBLIC_KEY_LENGTH);
		byte[] salt = body.getRaw(4);
		checkLength(salt, GROUP_SALT_LENGTH);
		String message = body.getOptionalString(5);
		checkLength(message, 1, MAX_GROUP_INVITATION_MSG_LENGTH);
		byte[] signature = body.getRaw(6);
		checkLength(signature, 1, MAX_SIGNATURE_LENGTH);
		// Create the private group
		Author creator = authorFactory.createAuthor(creatorName,
				creatorPublicKey);
		PrivateGroup privateGroup = privateGroupFactory.createPrivateGroup(
				groupName, creator, salt);
		// Verify the signature
		BdfList signed = BdfList.of(
				m.getTimestamp(),
				m.getGroupId(),
				privateGroup.getId()
		);
		try {
			clientHelper.verifySignature(SIGNING_LABEL_INVITE, signature,
					creatorPublicKey, signed);
		} catch (GeneralSecurityException e) {
			throw new FormatException();
		}
		// Create the metadata
		BdfDictionary meta = messageEncoder.encodeMetadata(INVITE,
				privateGroup.getId(), m.getTimestamp(), false, false, false,
				false, false);
		return new BdfMessageContext(meta);
	}

	private BdfMessageContext validateJoinMessage(Message m, BdfList body)
			throws FormatException {
		checkSize(body, 3);
		byte[] privateGroupId = body.getRaw(1);
		checkLength(privateGroupId, UniqueId.LENGTH);
		byte[] previousMessageId = body.getOptionalRaw(2);
		checkLength(previousMessageId, UniqueId.LENGTH);
		BdfDictionary meta = messageEncoder.encodeMetadata(JOIN,
				new GroupId(privateGroupId), m.getTimestamp(), false, false,
				false, false, false);
		if (previousMessageId == null) {
			return new BdfMessageContext(meta);
		} else {
			MessageId dependency = new MessageId(previousMessageId);
			return new BdfMessageContext(meta,
					Collections.singletonList(dependency));
		}
	}

	private BdfMessageContext validateLeaveMessage(Message m, BdfList body)
			throws FormatException {
		checkSize(body, 3);
		byte[] privateGroupId = body.getRaw(1);
		checkLength(privateGroupId, UniqueId.LENGTH);
		byte[] previousMessageId = body.getOptionalRaw(2);
		checkLength(previousMessageId, UniqueId.LENGTH);
		BdfDictionary meta = messageEncoder.encodeMetadata(LEAVE,
				new GroupId(privateGroupId), m.getTimestamp(), false, false,
				false, false, false);
		if (previousMessageId == null) {
			return new BdfMessageContext(meta);
		} else {
			MessageId dependency = new MessageId(previousMessageId);
			return new BdfMessageContext(meta,
					Collections.singletonList(dependency));
		}
	}

	private BdfMessageContext validateAbortMessage(Message m, BdfList body)
			throws FormatException {
		checkSize(body, 2);
		byte[] privateGroupId = body.getRaw(1);
		checkLength(privateGroupId, UniqueId.LENGTH);
		BdfDictionary meta = messageEncoder.encodeMetadata(ABORT,
				new GroupId(privateGroupId), m.getTimestamp(), false, false,
				false, false, false);
		return new BdfMessageContext(meta);
	}
}
