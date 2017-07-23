package org.chagolchana.noconnect.android.privategroup.conversation;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;
import org.chagolchana.noconnect.android.threaded.ThreadListController;
import org.chagolchana.noconnect.api.privategroup.GroupMessageHeader;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;
import org.chagolchana.noconnect.api.privategroup.Visibility;

public interface GroupController
		extends
		ThreadListController<PrivateGroup, GroupMessageItem, GroupMessageHeader> {

	void loadLocalAuthor(
			ResultExceptionHandler<LocalAuthor, DbException> handler);

	void isDissolved(
			ResultExceptionHandler<Boolean, DbException> handler);

	interface GroupListener extends ThreadListListener<GroupMessageHeader> {
		@UiThread
		void onContactRelationshipRevealed(AuthorId memberId,
				ContactId contactId, Visibility v);

		@UiThread
		void onGroupDissolved();
	}

}
