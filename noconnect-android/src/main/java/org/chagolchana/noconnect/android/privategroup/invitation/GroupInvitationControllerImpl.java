package org.chagolchana.noconnect.android.privategroup.invitation;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.noconnect.android.controller.handler.ExceptionHandler;
import org.chagolchana.noconnect.android.sharing.InvitationControllerImpl;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;
import org.chagolchana.noconnect.api.privategroup.event.GroupInvitationRequestReceivedEvent;
import org.chagolchana.noconnect.api.privategroup.event.GroupInvitationResponseReceivedEvent;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationItem;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationManager;

import java.util.Collection;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import static java.util.logging.Level.WARNING;
import static org.chagolchana.noconnect.api.privategroup.PrivateGroupManager.CLIENT_ID;

@NotNullByDefault
class GroupInvitationControllerImpl
		extends InvitationControllerImpl<GroupInvitationItem>
		implements GroupInvitationController {

	private final GroupInvitationManager groupInvitationManager;

	@Inject
	GroupInvitationControllerImpl(@DatabaseExecutor Executor dbExecutor,
			LifecycleManager lifecycleManager, EventBus eventBus,
			GroupInvitationManager groupInvitationManager) {
		super(dbExecutor, lifecycleManager, eventBus);
		this.groupInvitationManager = groupInvitationManager;
	}

	@Override
	public void eventOccurred(Event e) {
		super.eventOccurred(e);

		if (e instanceof GroupInvitationRequestReceivedEvent) {
			LOG.info("Group invitation request received, reloading");
			listener.loadInvitations(false);
		} else if (e instanceof GroupInvitationResponseReceivedEvent) {
			LOG.info("Group invitation response received, reloading");
			listener.loadInvitations(false);
		}
	}

	@Override
	protected ClientId getShareableClientId() {
		return CLIENT_ID;
	}

	@Override
	protected Collection<GroupInvitationItem> getInvitations()
			throws DbException {
		return groupInvitationManager.getInvitations();
	}

	@Override
	public void respondToInvitation(final GroupInvitationItem item,
			final boolean accept,
			final ExceptionHandler<DbException> handler) {
		runOnDbThread(new Runnable() {
			@Override
			public void run() {
				try {
					PrivateGroup g = item.getShareable();
					ContactId c = item.getCreator().getId();
					groupInvitationManager.respondToInvitation(c, g, accept);
				} catch (DbException e) {
					if (LOG.isLoggable(WARNING))
						LOG.log(WARNING, e.toString(), e);
					handler.onException(e);
				}
			}
		});
	}

}
