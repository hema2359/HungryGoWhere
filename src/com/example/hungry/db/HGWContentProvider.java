package com.example.hungry.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class HGWContentProvider extends ContentProvider implements HGWDBStructure {
	private DatabaseHelper mDbHelper;
	public static final String DATABASE_NAME = "Hungrygowhere.db";

	// related to db versioning
	private static final int DATABASE_VERSION = 1;
	public static final String DB_PREFS = "DB_PREFS", DB_VERSION = "DB_VERSION";

	private static final UriMatcher sUriMatcher;

	public static boolean IS_DEBUG = true;
	
	private class DatabaseHelper extends SQLiteOpenHelper { // IS_DEBUG
//		private static class DatabaseHelper extends SQLiteOpenHelper {
			public DatabaseHelper(Context context) {
				super(context, DATABASE_NAME, null, 1);
			}

			@Override
			public void onOpen(SQLiteDatabase db) {
				super.onOpen(db);
				// Enable foreign key constraints
				db.execSQL("PRAGMA foreign_keys=ON;");
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				if (IS_DEBUG) {
					db.execSQL(TABLE_RESTAURANTS_CREATE);
					
					Log.d("HUNGRY",DATABASE_NAME+ " Frame table created");
				}
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				if (IS_DEBUG) {
					db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
					
					onCreate(db);
				}
			}
		}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int match = sUriMatcher.match(uri);
		int count = db.delete(getTables(match), selection, selectionArgs);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int match = sUriMatcher.match(uri);

		long rowId = db.insert(getTables(match), null, values);
		if (rowId >= 0) {
			Uri insertUri = ContentUris.withAppendedId(uri, rowId);
			getContext().getContentResolver().notifyChange(insertUri, null);
			return insertUri;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		mDbHelper = new DatabaseHelper(getContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int match = sUriMatcher.match(uri);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = null;
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTables(match));
		cursor = qb.query(db, projection, selection, selectionArgs, null, null,
				sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int match = sUriMatcher.match(uri);

		long rowId = db.update(getTables(match), values, selection,
				selectionArgs);
		if (rowId >= 0) {
			Uri insertUri = ContentUris.withAppendedId(uri, rowId);
			getContext().getContentResolver().notifyChange(insertUri, null);
			return 1;
		}
		return 0;
	}
	@Override
	public int bulkInsert(Uri uri, ContentValues[] valuesArray) {
		int count = 0;
		boolean update_success;
		Cursor cursor = null;
		int match = sUriMatcher.match(uri);
		String table = getTables(match);
		String id_col_name = _ID;
		String select = null;
				
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		db.beginTransaction();
		try {
			for (ContentValues values : valuesArray) {
				select = String.format("%s = %d", id_col_name,
						values.getAsLong(id_col_name));
				cursor = db.query(table, new String[] { id_col_name }, select,
						null, null, null, null);
				if (cursor.getCount() == 0)
					update_success = (db.insert(table, null, values) >= 0);
				else
					update_success = (db.update(table, values, select, null) > 0);

				if (update_success)
					count++;
				cursor.close();
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			count = 0;
		} finally {
			db.endTransaction();
		}
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, ContentURIs.RESTAURANTS_PATH,
				ContentURIs.RESTAURANTS_CODE);
		
	}

	static private String getTables(int match) {
		switch (match) {
		case ContentURIs.RESTAURANTS_CODE:
			return TABLE_RESTAURANTS;
		
		default:
			throw new IllegalArgumentException("Unhandled match: " + match);
		}
	}

	public SQLiteDatabase getDBHandle() {
		return mDbHelper.getWritableDatabase();
	}
	
	public static void setupDatabase(Context c) {
		if (IS_DEBUG)
			return;

		SharedPreferences mPrefs;
		mPrefs = c.getSharedPreferences(DB_PREFS, Context.MODE_PRIVATE);

		// first check
		int DB_VERSION_NUMBER = mPrefs.getInt(DB_VERSION, -1);
		
		// second check
		String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
		File f = new File(databasePath);
		boolean isDBExists = f.exists();
		
		if (DB_VERSION_NUMBER < DATABASE_VERSION) {
			if (isDBExists) {
				f.delete();
			}
			
			// commit database version to shared prefs
			final SharedPreferences.Editor edit = mPrefs.edit();
			edit.clear();
			edit.putInt(DB_VERSION, DATABASE_VERSION);
			edit.commit();
			
		}
	}
	public static void copyDatabase(Context c) {
		String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
		File f = new File(databasePath);
		OutputStream myOutput = null;
		InputStream myInput = null;
		// Log.d("testing", " testing db path " + databasePath);
		// Log.d("testing", " testing db exist " + f.exists());

		if (f.exists()) {
			try {

				File directory = new File("/mnt/sdcard/HGW_DB_DEBUG");
				if (!directory.exists())
					directory.mkdir();

				myOutput = new FileOutputStream(directory.getAbsolutePath()
						+ "/" + DATABASE_NAME);
				myInput = new FileInputStream(databasePath);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = myInput.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}

				myOutput.flush();
			} catch (Exception e) {
			} finally {
				try {
					if (myOutput != null) {
						myOutput.close();
						myOutput = null;
					}
					if (myInput != null) {
						myInput.close();
						myInput = null;
					}
				} catch (Exception e) {
				}
			}
		}
	}
}
