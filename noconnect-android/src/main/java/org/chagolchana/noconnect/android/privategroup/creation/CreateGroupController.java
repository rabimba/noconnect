package org.chagolchana.noconnect.android.privategroup.creation;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.contactselection.ContactSelectorController;
import org.chagolchana.noconnect.android.contactselection.SelectableContactItem;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;

import java.util.Collection;

@NotNullByDefault
public interface CreateGroupController
		extends ContactSelectorController<SelectableContactItem> {

	void createGroup(String name,
			ResultExceptionHandler<GroupId, DbException> result);

	void sendInvitation(GroupId g, Collection<ContactId> contacts,
			String message, ResultExceptionHandler<Void, DbException> result);

}
