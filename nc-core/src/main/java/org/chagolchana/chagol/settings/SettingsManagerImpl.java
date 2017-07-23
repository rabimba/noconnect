package org.chagolchana.chagol.settings;

import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.settings.Settings;
import org.chagolchana.chagol.api.settings.SettingsManager;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class SettingsManagerImpl implements SettingsManager {

	private final DatabaseComponent db;

	@Inject
	SettingsManagerImpl(DatabaseComponent db) {
		this.db = db;
	}

	@Override
	public Settings getSettings(String namespace) throws DbException {
		Settings s;
		Transaction txn = db.startTransaction(true);
		try {
			s = db.getSettings(txn, namespace);
			db.commitTransaction(txn);
		} finally {
			db.endTransaction(txn);
		}
		return s;
	}

	@Override
	public void mergeSettings(Settings s, String namespace) throws DbException {
		Transaction txn = db.startTransaction(false);
		try {
			db.mergeSettings(txn, s, namespace);
			db.commitTransaction(txn);
		} finally {
			db.endTransaction(txn);
		}
	}
}
