package org.chagolchana.noconnect.android;

public interface DestroyableContext {

	void runOnUiThreadUnlessDestroyed(Runnable runnable);
}
