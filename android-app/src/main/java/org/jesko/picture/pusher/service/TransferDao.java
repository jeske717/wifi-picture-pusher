package org.jesko.picture.pusher.service;

import java.sql.SQLException;

import org.jesko.picture.pusher.data.GenericDatabaseHelper;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

@ContextSingleton
public class TransferDao extends BaseDaoImpl<Transfer, Integer> {

	@Inject
	public TransferDao(Context context) throws SQLException {
		super(new AndroidConnectionSource(new GenericDatabaseHelper(context, Transfer.class)), Transfer.class);
	}
	
	public TransferDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Transfer.class);
	}
	
}
