package org.chagolchana.noconnect.privategroup.invitation;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfEntry;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.SessionId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_INVITE_TIMESTAMP;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_LAST_LOCAL_MESSAGE_ID;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_LAST_REMOTE_MESSAGE_ID;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_LOCAL_TIMESTAMP;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_PRIVATE_GROUP_ID;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_ROLE;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_SESSION_ID;
import static org.chagolchana.noconnect.privategroup.invitation.GroupInvitationConstants.SESSION_KEY_STATE;
import static org.chagolchana.noconnect.privategroup.invitation.Role.CREATOR;
import static org.chagolchana.noconnect.privategroup.invitation.Role.INVITEE;
import static org.chagolchana.noconnect.privategroup.invitation.Role.PEER;

@Immutable
@NotNullByDefault
class SessionParserImpl implements SessionParser {

	@Inject
	SessionParserImpl() {
	}

	@Override
	public BdfDictionary getSessionQuery(SessionId s) {
		return BdfDictionary.of(new BdfEntry(SESSION_KEY_SESSION_ID, s));
	}

	@Override
	public Role getRole(BdfDictionary d) throws FormatException {
		return Role.fromValue(d.getLong(SESSION_KEY_ROLE).intValue());
	}

	@Override
	public CreatorSession parseCreatorSession(GroupId contactGroupId,
			BdfDictionary d) throws FormatException {
		if (getRole(d) != CREATOR) throw new IllegalArgumentException();
		return new CreatorSession(contactGroupId, getPrivateGroupId(d),
				getLastLocalMessageId(d), getLastRemoteMessageId(d),
				getLocalTimestamp(d), getInviteTimestamp(d),
				CreatorState.fromValue(getState(d)));
	}

	@Override
	public InviteeSession parseInviteeSession(GroupId contactGroupId,
			BdfDictionary d) throws FormatException {
		if (getRole(d) != INVITEE) throw new IllegalArgumentException();
		return new InviteeSession(contactGroupId, getPrivateGroupId(d),
				getLastLocalMessageId(d), getLastRemoteMessageId(d),
				getLocalTimestamp(d), getInviteTimestamp(d),
				InviteeState.fromValue(getState(d)));
	}

	@Override
	public PeerSession parsePeerSession(GroupId contactGroupId,
			BdfDictionary d) throws FormatException {
		if (getRole(d) != PEER) throw new IllegalArgumentException();
		return new PeerSession(contactGroupId, getPrivateGroupId(d),
				getLastLocalMessageId(d), getLastRemoteMessageId(d),
				getLocalTimestamp(d), PeerState.fromValue(getState(d)));
	}

	private int getState(BdfDictionary d) throws FormatException {
		return d.getLong(SESSION_KEY_STATE).intValue();
	}

	private GroupId getPrivateGroupId(BdfDictionary d) throws FormatException {
		return new GroupId(d.getRaw(SESSION_KEY_PRIVATE_GROUP_ID));
	}

	@Nullable
	private MessageId getLastLocalMessageId(BdfDictionary d)
			throws FormatException {
		byte[] b = d.getOptionalRaw(SESSION_KEY_LAST_LOCAL_MESSAGE_ID);
		return b == null ? null : new MessageId(b);
	}

	@Nullable
	private MessageId getLastRemoteMessageId(BdfDictionary d)
			throws FormatException {
		byte[] b = d.getOptionalRaw(SESSION_KEY_LAST_REMOTE_MESSAGE_ID);
		return b == null ? null : new MessageId(b);
	}

	private long getLocalTimestamp(BdfDictionary d) throws FormatException {
		return d.getLong(SESSION_KEY_LOCAL_TIMESTAMP);
	}

	private long getInviteTimestamp(BdfDictionary d) throws FormatException {
		return d.getLong(SESSION_KEY_INVITE_TIMESTAMP);
	}
}
