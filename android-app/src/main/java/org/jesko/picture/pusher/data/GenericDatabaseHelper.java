package org.jesko.picture.pusher.data;

import java.sql.SQLException;

import org.jesko.picture.pusher.service.Transfer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class GenericDatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String DATABASE_NAME = "picturePusherDatabase";
	private static final int DATABASE_VERSION = 1;
	private final Class<?> dataClass;
	
	public GenericDatabaseHelper(Context context, Class<?> dataClass) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.dataClass = dataClass;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, dataClass);
		} catch (SQLException e) {
			Log.e(getClass().getName(), "Couldn't create the "+ dataClass.getName() + " table", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Transfer.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			Log.e(getClass().getName(), "Couldn't upgrade the " + dataClass.getName() + " table", e);
		}
	}
}
