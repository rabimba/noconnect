package org.chagolchana.noconnect.api.privategroup.invitation;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.privategroup.PrivateGroup;
import org.chagolchana.noconnect.api.sharing.InvitationItem;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class GroupInvitationItem extends InvitationItem<PrivateGroup> {

	private final Contact creator;

	public GroupInvitationItem(PrivateGroup privateGroup, Contact creator) {
		super(privateGroup, false);
		this.creator = creator;
	}

	public Contact getCreator() {
		return creator;
	}

}
