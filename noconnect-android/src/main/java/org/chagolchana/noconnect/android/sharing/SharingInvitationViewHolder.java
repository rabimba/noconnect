package org.chagolchana.noconnect.android.sharing;

import android.view.View;

import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.util.StringUtils;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.api.sharing.SharingInvitationItem;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nullable;

class SharingInvitationViewHolder
		extends InvitationViewHolder<SharingInvitationItem> {

	SharingInvitationViewHolder(View v) {
		super(v);
	}

	@Override
	public void onBind(@Nullable final SharingInvitationItem item,
			final InvitationAdapter.InvitationClickListener<SharingInvitationItem> listener) {
		super.onBind(item, listener);
		if (item == null) return;

		Collection<String> names = new ArrayList<>();
		for (Contact c : item.getNewSharers())
			names.add(c.getAuthor().getName());
		sharedBy.setText(
				sharedBy.getContext().getString(R.string.shared_by_format,
						StringUtils.join(names, ", ")));
	}

}
