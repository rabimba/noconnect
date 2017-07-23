package org.chagolchana.noconnect.android.controller.handler;

import android.support.annotation.UiThread;

import org.chagolchana.noconnect.android.DestroyableContext;

public abstract class UiResultHandler<R> implements ResultHandler<R> {

	private final DestroyableContext listener;

	protected UiResultHandler(DestroyableContext listener) {
		this.listener = listener;
	}

	@Override
	public void onResult(final R result) {
		listener.runOnUiThreadUnlessDestroyed(new Runnable() {
			@Override
			public void run() {
				onResultUi(result);
			}
		});
	}

	@UiThread
	public abstract void onResultUi(R result);
}
