package org.chagolchana.noconnect.android.privategroup.invitation;

import android.view.View;

import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.sharing.InvitationAdapter.InvitationClickListener;
import org.chagolchana.noconnect.android.sharing.InvitationViewHolder;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationItem;

import javax.annotation.Nullable;

class GroupInvitationViewHolder
		extends InvitationViewHolder<GroupInvitationItem> {

	GroupInvitationViewHolder(View v) {
		super(v);
	}

	@Override
	public void onBind(@Nullable final GroupInvitationItem item,
			final InvitationClickListener<GroupInvitationItem> listener) {
		super.onBind(item, listener);
		if (item == null) return;

		sharedBy.setText(
				sharedBy.getContext().getString(R.string.groups_created_by,
						item.getCreator().getAuthor().getName()));
	}

}