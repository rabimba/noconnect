package org.chagolchana.noconnect.android.sharing;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.NoSuchContactException;
import org.chagolchana.chagol.api.db.NoSuchGroupException;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.android.contactselection.ContactSelectorControllerImpl;
import org.chagolchana.noconnect.android.controller.handler.ExceptionHandler;
import org.chagolchana.noconnect.api.forum.ForumSharingManager;
import org.chagolchana.noconnect.api.messaging.ConversationManager;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static java.util.logging.Level.WARNING;
import static org.chagolchana.chagol.util.StringUtils.isNullOrEmpty;

@Immutable
@NotNullByDefault
class ShareForumControllerImpl extends ContactSelectorControllerImpl
		implements ShareForumController {

	private final static Logger LOG =
			Logger.getLogger(ShareForumControllerImpl.class.getName());

	private final ConversationManager conversationManager;
	private final ForumSharingManager forumSharingManager;
	private final Clock clock;

	@Inject
	ShareForumControllerImpl(@DatabaseExecutor Executor dbExecutor,
			LifecycleManager lifecycleManager, ContactManager contactManager,
			ConversationManager conversationManager,
			ForumSharingManager forumSharingManager, Clock clock) {
		super(dbExecutor, lifecycleManager, contactManager);
		this.conversationManager = conversationManager;
		this.forumSharingManager = forumSharingManager;
		this.clock = clock;
	}

	@Override
	protected boolean isDisabled(GroupId g, Contact c) throws DbException {
		return !forumSharingManager.canBeShared(g, c);
	}

	@Override
	public void share(final GroupId g, final Collection<ContactId> contacts,
			final String message,
			final ExceptionHandler<DbException> handler) {
		runOnDbThread(new Runnable() {
			@Override
			public void run() {
				try {
					String msg = isNullOrEmpty(message) ? null : message;
					for (ContactId c : contacts) {
						try {
							long time = Math.max(clock.currentTimeMillis(),
									conversationManager.getGroupCount(c)
											.getLatestMsgTime() + 1);
							forumSharingManager.sendInvitation(g, c, msg, time);
						} catch (NoSuchContactException | NoSuchGroupException e) {
							if (LOG.isLoggable(WARNING))
								LOG.log(WARNING, e.toString(), e);
						}
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
