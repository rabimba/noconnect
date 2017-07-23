package org.chagolchana.noconnect.android.controller;

import org.chagolchana.noconnect.android.controller.handler.ResultHandler;

public interface BriarController extends ActivityLifecycleController {

	void startAndBindService();

	boolean hasEncryptionKey();

	void signOut(ResultHandler<Void> eventHandler);
}
