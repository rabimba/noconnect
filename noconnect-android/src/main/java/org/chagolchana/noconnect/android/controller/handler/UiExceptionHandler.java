package org.chagolchana.noconnect.android.controller.handler;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.DestroyableContext;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public abstract class UiExceptionHandler<E extends Exception>
		implements ExceptionHandler<E> {

	protected final DestroyableContext listener;

	protected UiExceptionHandler(DestroyableContext listener) {
		this.listener = listener;
	}

	@Override
	public void onException(final E exception) {
		listener.runOnUiThreadUnlessDestroyed(new Runnable() {
			@Override
			public void run() {
				onExceptionUi(exception);
			}
		});
	}

	@UiThread
	public abstract void onExceptionUi(E exception);

}
