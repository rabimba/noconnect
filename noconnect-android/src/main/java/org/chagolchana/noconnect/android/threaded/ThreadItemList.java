package org.chagolchana.noconnect.android.threaded;

import org.chagolchana.chagol.api.sync.MessageId;

import java.util.List;

import javax.annotation.Nullable;

public interface ThreadItemList<I extends ThreadItem> extends List<I> {

	@Nullable
	MessageId getFirstVisibleItemId();

	void setFirstVisibleId(@Nullable MessageId bottomVisibleItemId);
}
