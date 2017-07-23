package org.chagolchana.noconnect.android.privategroup.list;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.DestroyableContext;
import org.chagolchana.noconnect.android.controller.DbController;
import org.chagolchana.noconnect.android.controller.handler.ExceptionHandler;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;
import org.chagolchana.noconnect.api.privategroup.GroupMessageHeader;

import java.util.Collection;

@NotNullByDefault
interface GroupListController extends DbController {

	/**
	 * The listener must be set right after the controller was injected
	 */
	void setGroupListListener(GroupListListener listener);

	@UiThread
	void onStart();

	@UiThread
	void onStop();

	void loadGroups(
			ResultExceptionHandler<Collection<GroupItem>, DbException> result);

	void removeGroup(GroupId g, ExceptionHandler<DbException> result);

	void loadAvailableGroups(
			ResultExceptionHandler<Integer, DbException> result);

	interface GroupListListener extends DestroyableContext {

		@UiThread
		void onGroupMessageAdded(GroupMessageHeader header);

		@UiThread
		void onGroupInvitationReceived();

		@UiThread
		void onGroupAdded(GroupId groupId);

		@UiThread
		void onGroupRemoved(GroupId groupId);

		@UiThread
		void onGroupDissolved(GroupId groupId);

	}

}
