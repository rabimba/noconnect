package org.chagolchana.noconnect.android.contactselection;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.DestroyableContext;

import java.util.Collection;

@NotNullByDefault
public interface ContactSelectorListener extends DestroyableContext {

	@UiThread
	void contactsSelected(Collection<ContactId> contacts);

}
