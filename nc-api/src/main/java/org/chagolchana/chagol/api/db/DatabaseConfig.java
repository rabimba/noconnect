package org.chagolchana.chagol.api.db;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.File;

import javax.annotation.Nullable;

@NotNullByDefault
public interface DatabaseConfig {

	boolean databaseExists();

	File getDatabaseDirectory();

	void setEncryptionKey(SecretKey key);

	@Nullable
	SecretKey getEncryptionKey();

	void setLocalAuthorName(String nickname);

	@Nullable
	String getLocalAuthorName();

	long getMaxSize();
}
