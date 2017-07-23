package org.chagolchana.noconnect.api.sharing;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.SessionId;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class InvitationResponse extends InvitationMessage {

	private final GroupId shareableId;
	private final boolean accept;

	public InvitationResponse(MessageId id, GroupId groupId,
			long time, boolean local, boolean sent, boolean seen,
			boolean read, SessionId sessionId, GroupId shareableId,
			ContactId contactId, boolean accept) {
		super(id, groupId, time, local, sent, seen, read, sessionId, contactId);
		this.shareableId = shareableId;
		this.accept = accept;
	}

	public boolean wasAccepted() {
		return accept;
	}

	public GroupId getShareableId() {
		return shareableId;
	}

}
