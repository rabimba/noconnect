package org.chagolchana.noconnect.android.navdrawer;

import org.chagolchana.chagol.api.plugin.TransportId;
import org.chagolchana.noconnect.android.DestroyableContext;

interface TransportStateListener extends DestroyableContext {

	void stateUpdate(TransportId id, boolean enabled);
}
