package org.chagolchana.chagol.plugin.file;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.File;
import java.io.IOException;

@NotNullByDefault
interface RemovableDriveMonitor {

	void start(Callback c) throws IOException;

	void stop() throws IOException;

	interface Callback {

		void driveInserted(File root);

		void exceptionThrown(IOException e);
	}
}
