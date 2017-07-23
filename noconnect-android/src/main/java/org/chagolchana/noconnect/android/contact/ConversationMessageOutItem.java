package org.chagolchana.noconnect.android.contact;

import android.support.annotation.LayoutRes;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.api.messaging.PrivateMessageHeader;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
class ConversationMessageOutItem extends ConversationOutItem {

	ConversationMessageOutItem(PrivateMessageHeader h) {
		super(h.getId(), h.getGroupId(), null, h.getTimestamp(), h.isSent(),
				h.isSeen());
	}

	@LayoutRes
	@Override
	public int getLayout() {
		return R.layout.list_item_conversation_msg_out;
	}

}
