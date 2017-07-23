package org.chagolchana.noconnect.android.privategroup.conversation;

import android.content.Context;
import android.support.annotation.UiThread;
import android.view.View;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.threaded.BaseThreadItemViewHolder;
import org.chagolchana.noconnect.android.threaded.ThreadItemAdapter.ThreadItemListener;

import static org.chagolchana.chagol.api.identity.Author.Status.OURSELVES;

@UiThread
@NotNullByDefault
class JoinMessageItemViewHolder
		extends BaseThreadItemViewHolder<GroupMessageItem> {

	private final boolean isCreator;

	JoinMessageItemViewHolder(View v, boolean isCreator) {
		super(v);
		this.isCreator = isCreator;
	}

	@Override
	public void bind(GroupMessageItem item,
			ThreadItemListener<GroupMessageItem> listener) {
		super.bind(item, listener);

		if (isCreator) bindForCreator((JoinMessageItem) item);
		else bind((JoinMessageItem) item);
	}

	private void bindForCreator(final JoinMessageItem item) {
		if (item.isInitial()) {
			textView.setText(R.string.groups_member_created_you);
		} else {
			textView.setText(
					getContext().getString(R.string.groups_member_joined,
							item.getAuthor().getName()));
		}
	}

	private void bind(final JoinMessageItem item) {
		final Context ctx = getContext();

		if (item.isInitial()) {
			textView.setText(ctx.getString(R.string.groups_member_created,
					item.getAuthor().getName()));
		} else {
			if (item.getStatus() == OURSELVES) {
				textView.setText(R.string.groups_member_joined_you);
			} else {
				textView.setText(ctx.getString(R.string.groups_member_joined,
						item.getAuthor().getName()));
			}
		}
	}

}
