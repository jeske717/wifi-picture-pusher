package org.jesko.picture.pusher.host;

import java.sql.SQLException;

import org.jesko.picture.pusher.data.GenericDatabaseHelper;

import roboguice.inject.ContextSingleton;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

@ContextSingleton
public class HostDao extends BaseDaoImpl<Host, Long> {

	@Inject
	public HostDao(Context context) throws SQLException {
		super(new AndroidConnectionSource(new GenericDatabaseHelper(context, Host.class)), Host.class);
	}
	
	public HostDao(ConnectionSource connectionSource)
			throws SQLException {
		super(connectionSource, Host.class);
	}

}
