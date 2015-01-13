package com.example.homeworkfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 5;
	private static final String DATABASE_NAME = "movieManager";

	// USERS TABLE
	private static final String TABLE_USERS = "users";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_FACEBOOK = "facebook_profile";
	private static final String KEY_SEX = "sex";
	private static final String KEY_VARSTA = "varsta";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS
				+ "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_NAME
				+ " TEXT NOT NULL, " + KEY_USERNAME + " TEXT NOT NULL, "
				+ KEY_PASSWORD + " TEXT NOT NULL," + KEY_EMAIL
				+ " TEXT NOT NULL, " + KEY_FACEBOOK + " TEXT, " + KEY_SEX
				+ " TEXT, " + KEY_VARSTA + " INTEGER " + ")";
		db.execSQL(CREATE_USERS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(db);
	}

	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_USERNAME, user.getUsername());
		values.put(KEY_PASSWORD, user.getPassword());
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_FACEBOOK, user.getFacebookProf());
		values.put(KEY_SEX, user.getSex());
		values.put(KEY_VARSTA, user.getVarsta());

		db.insert(TABLE_USERS, null, values);
		db.close();

	}

	public User getUser(String username, String password) {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * from users where username='" + username
				+ "' and password='" + password + "'";
		Cursor cursor = db.rawQuery(query, null);
		User user = new User();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			user.setId(Integer.parseInt(cursor.getString(0)));
			user.setName(cursor.getString(1));
			user.setUsername(cursor.getString(2));
			user.setPassword(cursor.getString(3));
			user.setEmail(cursor.getString(4));
			user.setFacebookProf(cursor.getString(5));
			user.setSex(cursor.getString(6));
			user.setVarsta(Integer.parseInt(cursor.getString(7)));
			cursor.close();
		} else {
			user = null;
			cursor.close();
		}

		return user;
	}

	public User checkUser(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * from users where username='" + username + "'";
		Cursor cursor = db.rawQuery(query, null);
		User user = new User();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			user.setId(Integer.parseInt(cursor.getString(0)));
			user.setName(cursor.getString(1));
			user.setUsername(cursor.getString(2));
			user.setPassword(cursor.getString(3));
			user.setEmail(cursor.getString(4));
			user.setFacebookProf(cursor.getString(5));
			user.setSex(cursor.getString(6));
			user.setVarsta(Integer.parseInt(cursor.getString(7)));
			cursor.close();
		} else {
			user = null;
			cursor.close();
		}

		return user;
	}

	public boolean updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_FACEBOOK, user.getFacebookProf());
		values.put(KEY_PASSWORD, user.getPassword());

		int i = db.update(TABLE_USERS, values, KEY_USERNAME + "=?",
				new String[] { user.getUsername() });
		return i > 0;
	}
}
