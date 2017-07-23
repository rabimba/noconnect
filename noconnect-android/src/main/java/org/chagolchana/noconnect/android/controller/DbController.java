package org.chagolchana.noconnect.android.controller;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface DbController {

	void runOnDbThread(Runnable task);
}
