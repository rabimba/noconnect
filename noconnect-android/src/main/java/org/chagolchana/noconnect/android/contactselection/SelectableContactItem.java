package org.chagolchana.noconnect.android.contactselection;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.contact.ContactItem;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
public class SelectableContactItem extends ContactItem {

	private boolean selected, disabled;

	public SelectableContactItem(Contact contact, boolean selected,
			boolean disabled) {
		super(contact);
		this.selected = selected;
		this.disabled = disabled;
	}

	boolean isSelected() {
		return selected;
	}

	void toggleSelected() {
		selected = !selected;
	}

	public boolean isDisabled() {
		return disabled;
	}

}
