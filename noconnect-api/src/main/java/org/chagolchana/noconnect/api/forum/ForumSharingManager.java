package org.chagolchana.noconnect.api.forum;

import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.noconnect.api.sharing.SharingManager;

public interface ForumSharingManager extends SharingManager<Forum> {

	ClientId CLIENT_ID = new ClientId("org.briarproject.briar.forum.sharing");

}
