package org.chagolchana.noconnect.android.sharing;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.contactselection.ContactSelectorController;
import org.chagolchana.noconnect.android.contactselection.SelectableContactItem;
import org.chagolchana.noconnect.android.controller.handler.ExceptionHandler;

import java.util.Collection;

public interface ShareForumController
		extends ContactSelectorController<SelectableContactItem> {

	void share(GroupId g, Collection<ContactId> contacts, String msg,
			ExceptionHandler<DbException> handler);

}
