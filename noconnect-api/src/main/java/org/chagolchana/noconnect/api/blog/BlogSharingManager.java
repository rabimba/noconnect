package org.chagolchana.noconnect.api.blog;

import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.noconnect.api.sharing.SharingManager;

public interface BlogSharingManager extends SharingManager<Blog> {

	ClientId CLIENT_ID = new ClientId("org.briarproject.briar.blog.sharing");

}
