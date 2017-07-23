package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.client.ContactGroupFactory;
import org.chagolchana.chagol.api.data.MetadataParser;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.forum.Forum;
import org.chagolchana.noconnect.api.forum.ForumInvitationResponse;
import org.chagolchana.noconnect.api.forum.ForumManager.RemoveForumHook;
import org.chagolchana.noconnect.api.forum.ForumSharingManager;

import javax.inject.Inject;

@NotNullByDefault
class ForumSharingManagerImpl extends SharingManagerImpl<Forum>
		implements ForumSharingManager, RemoveForumHook {

	@Inject
	ForumSharingManagerImpl(DatabaseComponent db, ClientHelper clientHelper,
			MetadataParser metadataParser, MessageParser<Forum> messageParser,
			SessionEncoder sessionEncoder, SessionParser sessionParser,
			MessageTracker messageTracker,
			ContactGroupFactory contactGroupFactory,
			ProtocolEngine<Forum> engine,
			InvitationFactory<Forum, ForumInvitationResponse> invitationFactory) {
		super(db, clientHelper, metadataParser, messageParser, sessionEncoder,
				sessionParser, messageTracker, contactGroupFactory, engine,
				invitationFactory);
	}

	@Override
	protected ClientId getClientId() {
		return CLIENT_ID;
	}

	@Override
	public void removingForum(Transaction txn, Forum f) throws DbException {
		removingShareable(txn, f);
	}

}
