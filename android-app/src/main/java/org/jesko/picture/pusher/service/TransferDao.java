package org.jesko.picture.pusher.service;

import java.sql.SQLException;

import org.jesko.picture.pusher.data.DatabaseHelper;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

@ContextSingleton
public class TransferDao extends BaseDaoImpl<Transfer, Long> {

	@Inject
	public TransferDao(Context context, DatabaseHelper helper) throws SQLException {
		super(new AndroidConnectionSource(helper), Transfer.class);
	}
	
	public TransferDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Transfer.class);
	}
	
}
