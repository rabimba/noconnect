package org.chagolchana.noconnect.android.privategroup.memberlist;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.plugin.ConnectionRegistry;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.controller.DbControllerImpl;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;
import org.chagolchana.noconnect.api.privategroup.GroupMember;
import org.chagolchana.noconnect.api.privategroup.PrivateGroupManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.inject.Inject;

import static java.util.logging.Level.WARNING;

class GroupMemberListControllerImpl extends DbControllerImpl
		implements GroupMemberListController {

	private static final Logger LOG =
			Logger.getLogger(GroupMemberListControllerImpl.class.getName());

	private final ConnectionRegistry connectionRegistry;
	private final PrivateGroupManager privateGroupManager;

	@Inject
	GroupMemberListControllerImpl(@DatabaseExecutor Executor dbExecutor,
			LifecycleManager lifecycleManager,
			ConnectionRegistry connectionRegistry,
			PrivateGroupManager privateGroupManager) {
		super(dbExecutor, lifecycleManager);
		this.connectionRegistry = connectionRegistry;
		this.privateGroupManager = privateGroupManager;
	}

	@Override
	public void loadMembers(final GroupId groupId, final
			ResultExceptionHandler<Collection<MemberListItem>, DbException> handler) {
		runOnDbThread(new Runnable() {
			@Override
			public void run() {
				try {
					Collection<MemberListItem> items = new ArrayList<>();
					Collection<GroupMember> members =
							privateGroupManager.getMembers(groupId);
					for (GroupMember m : members) {
						ContactId c = m.getContactId();
						boolean online = false;
						if (c != null)
							online = connectionRegistry.isConnected(c);
						items.add(new MemberListItem(m, online));
					}
					handler.onResult(items);
				} catch (DbException e) {
					if (LOG.isLoggable(WARNING))
						LOG.log(WARNING, e.toString(), e);
					handler.onException(e);
				}
			}
		});
	}

}
