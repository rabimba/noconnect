package org.chagolchana.noconnect.android.privategroup.reveal;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.contactselection.SelectableContactItem;
import org.chagolchana.noconnect.api.privategroup.Visibility;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
class RevealableContactItem extends SelectableContactItem {

	private final Visibility visibility;

	RevealableContactItem(Contact contact, boolean selected,
			boolean disabled, Visibility visibility) {
		super(contact, selected, disabled);
		this.visibility = visibility;
	}

	Visibility getVisibility() {
		return visibility;
	}

}
