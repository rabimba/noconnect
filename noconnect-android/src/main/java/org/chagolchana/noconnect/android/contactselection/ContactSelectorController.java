package org.chagolchana.noconnect.android.contactselection;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.controller.DbController;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;

import java.util.Collection;

@NotNullByDefault
public interface ContactSelectorController<I extends SelectableContactItem>
		extends DbController {

	void loadContacts(GroupId g, Collection<ContactId> selection,
			ResultExceptionHandler<Collection<I>, DbException> handler);

}
