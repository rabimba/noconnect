package org.chagolchana.noconnect.android.contact;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.util.StringUtils;
import org.chagolchana.noconnect.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@UiThread
@NotNullByDefault
class ConversationNoticeOutViewHolder extends ConversationOutItemViewHolder {

	private final TextView msgText;

	ConversationNoticeOutViewHolder(View v) {
		super(v);
		msgText = (TextView) v.findViewById(R.id.msgText);
	}

	@Override
	void bind(ConversationItem conversationItem) {
		super.bind(conversationItem);

		ConversationNoticeOutItem item =
				(ConversationNoticeOutItem) conversationItem;

		String message = item.getMsgText();
		if (StringUtils.isNullOrEmpty(message)) {
			msgText.setVisibility(GONE);
			layout.setBackgroundResource(R.drawable.notice_out);
		} else {
			msgText.setVisibility(VISIBLE);
			msgText.setText(StringUtils.trim(message));
			layout.setBackgroundResource(R.drawable.notice_out_bottom);
		}
	}

	@Override
	protected boolean hasDarkBackground() {
		return false;
	}

}
