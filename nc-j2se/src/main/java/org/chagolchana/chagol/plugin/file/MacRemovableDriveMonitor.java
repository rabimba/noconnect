package org.chagolchana.chagol.plugin.file;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
class MacRemovableDriveMonitor extends UnixRemovableDriveMonitor {

	@Override
	protected String[] getPathsToWatch() {
		return new String[] {"/Volumes"};
	}
}
