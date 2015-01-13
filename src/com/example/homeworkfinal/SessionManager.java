package com.example.homeworkfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	SharedPreferences preference;
	Editor editor;
	Context _context;

	int PRIVATE_MODE = 0;
	private static final String IS_LOGIN = "IsLoggedIn";
	private static final String PREF_NAME = "LoginInfos";
	private static final String KEY_NAME = "name";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_FACEBOOK = "facebook_profile";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_SEX = "sex";

	public SessionManager(Context context) {
		this._context = context;
		preference = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = preference.edit();
	}

	public void createLoginSession(String name, String user, String email,
			String facebook, String password, String sex) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		// Storing name in pref
		editor.putString(KEY_NAME, name);

		editor.putString(KEY_USERNAME, user);
		// Storing email in pref
		editor.putString(KEY_EMAIL, email);

		editor.putString(KEY_FACEBOOK, facebook);

		editor.putString(KEY_PASSWORD, password);

		editor.putString(KEY_SEX, sex);

		// commit changes
		editor.commit();
	}

	public User getUserDetails() {
		User user = new User();
		user.setName(preference.getString(KEY_NAME, null));
		user.setUsername(preference.getString(KEY_USERNAME, null));
		user.setEmail(preference.getString(KEY_EMAIL, null));
		user.setFacebookProf(preference.getString(KEY_FACEBOOK, null));
		user.setPassword(preference.getString(KEY_PASSWORD, null));
		user.setSex(preference.getString(KEY_SEX, null));
		return user;
	}

	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
	}

	public void checkLogin() {
		// Check login status
		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}

	}

	public boolean isLoggedIn() {
		return preference.getBoolean(IS_LOGIN, false);
	}
}
