package org.chagolchana.noconnect.android.contact;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.contact.BaseContactListAdapter.OnContactClickListener;
import org.chagolchana.noconnect.android.util.UiUtils;

import javax.annotation.Nullable;

import static android.support.v4.view.ViewCompat.setTransitionName;
import static org.chagolchana.noconnect.android.util.UiUtils.formatDate;

@UiThread
@NotNullByDefault
class ContactListItemViewHolder extends ContactItemViewHolder<ContactListItem> {

	private final TextView unread;
	private final TextView date;

	ContactListItemViewHolder(View v) {
		super(v);
		unread = (TextView) v.findViewById(R.id.unreadCountView);
		date = (TextView) v.findViewById(R.id.dateView);
	}

	@Override
	protected void bind(ContactListItem item, @Nullable
			OnContactClickListener<ContactListItem> listener) {
		super.bind(item, listener);

		// unread count
		int unreadCount = item.getUnreadCount();
		if (unreadCount > 0) {
			unread.setText(String.valueOf(unreadCount));
			unread.setVisibility(View.VISIBLE);
		} else {
			unread.setVisibility(View.INVISIBLE);
		}

		// date of last message
		if (item.isEmpty()) {
			date.setText(R.string.date_no_private_messages);
		} else {
			long timestamp = item.getTimestamp();
			date.setText(formatDate(date.getContext(), timestamp));
		}

		ContactId c = item.getContact().getId();
		setTransitionName(avatar, UiUtils.getAvatarTransitionName(c));
		setTransitionName(bulb, UiUtils.getBulbTransitionName(c));
	}

}
