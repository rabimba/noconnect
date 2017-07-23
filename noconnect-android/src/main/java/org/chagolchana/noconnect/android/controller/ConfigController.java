package org.chagolchana.noconnect.android.controller;

import android.content.Context;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;

@NotNullByDefault
public interface ConfigController {

	@Nullable
	String getEncryptedDatabaseKey();

	void storeEncryptedDatabaseKey(String hex);

	void deleteAccount(Context ctx);

	boolean accountExists();
}
