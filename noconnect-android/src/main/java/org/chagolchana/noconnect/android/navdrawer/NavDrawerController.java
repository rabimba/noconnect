package org.chagolchana.noconnect.android.navdrawer;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.TransportId;
import org.chagolchana.noconnect.android.controller.ActivityLifecycleController;
import org.chagolchana.noconnect.android.controller.handler.ResultHandler;

@NotNullByDefault
public interface NavDrawerController extends ActivityLifecycleController {

	boolean isTransportRunning(TransportId transportId);

	void showExpiryWarning(final ResultHandler<Boolean> handler);

	void expiryWarningDismissed();

}
