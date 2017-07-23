package org.chagolchana.noconnect.android.privategroup.invitation;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.sharing.InvitationController;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationItem;

@NotNullByDefault
interface GroupInvitationController
		extends InvitationController<GroupInvitationItem> {
}
