package org.chagolchana.noconnect.api.blog;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.SessionId;
import org.chagolchana.noconnect.api.sharing.InvitationResponse;

@NotNullByDefault
public class BlogInvitationResponse extends InvitationResponse {

	public BlogInvitationResponse(MessageId id, GroupId groupId, long time,
			boolean local, boolean sent, boolean seen, boolean read,
			SessionId sessionId, GroupId blogId, ContactId contactId,
			boolean accept) {
		super(id, groupId, time, local, sent, seen, read, sessionId, blogId,
				contactId, accept);
	}

}
