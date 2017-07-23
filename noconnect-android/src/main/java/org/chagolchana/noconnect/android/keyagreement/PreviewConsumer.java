package org.chagolchana.noconnect.android.keyagreement;

import android.hardware.Camera;
import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@SuppressWarnings("deprecation")
@NotNullByDefault
interface PreviewConsumer {

	@UiThread
	void start(Camera camera);

	@UiThread
	void stop();
}
