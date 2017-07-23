package org.chagolchana.noconnect.android.sharing;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.noconnect.android.controller.handler.ExceptionHandler;
import org.chagolchana.noconnect.api.forum.Forum;
import org.chagolchana.noconnect.api.forum.ForumSharingManager;
import org.chagolchana.noconnect.api.forum.event.ForumInvitationRequestReceivedEvent;
import org.chagolchana.noconnect.api.sharing.SharingInvitationItem;

import java.util.Collection;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import static java.util.logging.Level.WARNING;
import static org.chagolchana.noconnect.api.forum.ForumManager.CLIENT_ID;

@NotNullByDefault
class ForumInvitationControllerImpl
		extends InvitationControllerImpl<SharingInvitationItem>
		implements ForumInvitationController {

	private final ForumSharingManager forumSharingManager;

	@Inject
	ForumInvitationControllerImpl(@DatabaseExecutor Executor dbExecutor,
			LifecycleManager lifecycleManager, EventBus eventBus,
			ForumSharingManager forumSharingManager) {
		super(dbExecutor, lifecycleManager, eventBus);
		this.forumSharingManager = forumSharingManager;
	}

	@Override
	public void eventOccurred(Event e) {
		super.eventOccurred(e);

		if (e instanceof ForumInvitationRequestReceivedEvent) {
			LOG.info("Forum invitation received, reloading");
			listener.loadInvitations(false);
		}
	}

	@Override
	protected ClientId getShareableClientId() {
		return CLIENT_ID;
	}

	@Override
	protected Collection<SharingInvitationItem> getInvitations()
			throws DbException {
		return forumSharingManager.getInvitations();
	}

	@Override
	public void respondToInvitation(final SharingInvitationItem item,
			final boolean accept,
			final ExceptionHandler<DbException> handler) {
		runOnDbThread(new Runnable() {
			@Override
			public void run() {
				try {
					Forum f = (Forum) item.getShareable();
					for (Contact c : item.getNewSharers()) {
						// TODO: What happens if a contact has been removed?
						forumSharingManager.respondToInvitation(f, c, accept);
					}
				} catch (DbException e) {
					if (LOG.isLoggable(WARNING))
						LOG.log(WARNING, e.toString(), e);
					handler.onException(e);
				}
			}
		});
	}

}
