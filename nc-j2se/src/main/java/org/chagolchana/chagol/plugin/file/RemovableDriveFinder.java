package org.chagolchana.chagol.plugin.file;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@NotNullByDefault
interface RemovableDriveFinder {

	Collection<File> findRemovableDrives() throws IOException;
}
