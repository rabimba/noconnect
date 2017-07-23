package org.chagolchana.noconnect.android.privategroup.memberlist;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.controller.DbController;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;

import java.util.Collection;

public interface GroupMemberListController extends DbController {

	void loadMembers(GroupId groupId,
			ResultExceptionHandler<Collection<MemberListItem>, DbException> handler);

}
