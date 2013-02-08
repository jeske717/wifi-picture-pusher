package org.jesko.picture.pusher.data;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

@Singleton
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String DATABASE_NAME = "picturePusherDatabase";
	private static final int DATABASE_VERSION = 1;
	private final List<Class<?>> dataClasses;
	
	@Inject
	public DatabaseHelper(Provider<Context> contextProvider) {
		super(contextProvider.get(), DATABASE_NAME, null, DATABASE_VERSION);
		this.dataClasses = Entities.ENTITY_CLASSES;
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		for (Class<?> dataClass : dataClasses) {
			try {
				TableUtils.createTable(connectionSource, dataClass);
			} catch (SQLException e) {
				Log.e(getClass().getName(), "Couldn't create the "+ dataClass.getName() + " table", e);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		for(Class<?> dataClass : dataClasses) {
			try {
				TableUtils.dropTable(connectionSource, dataClass, true);
			} catch (SQLException e) {
				Log.e(getClass().getName(), "Couldn't upgrade the " + dataClass.getName() + " table", e);
			}
		}
			onCreate(database, connectionSource);
	}
}
