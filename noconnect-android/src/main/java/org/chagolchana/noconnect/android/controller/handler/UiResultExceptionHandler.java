package org.chagolchana.noconnect.android.controller.handler;

import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.DestroyableContext;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public abstract class UiResultExceptionHandler<R, E extends Exception>
		extends UiExceptionHandler<E> implements ResultExceptionHandler<R, E> {

	protected UiResultExceptionHandler(DestroyableContext listener) {
		super(listener);
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
