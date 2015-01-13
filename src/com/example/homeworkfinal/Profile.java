package com.example.homeworkfinal;

import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
	SessionManager session;
	DatabaseHandler db;
	User user = new User();
	EditText newEmail;
	EditText newFacebook;
	EditText newPassword;
	int k = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		TextView tvName, tvUser, tvEmail, tvFacebook;
		Button btnEditEmail, btnEditFacebook, btnEditPassword, btnSave, btnCancelChanges;
		try {
			tvName = (TextView) findViewById(R.id.textViewName);
			tvUser = (TextView) findViewById(R.id.textViewUser);
			tvEmail = (TextView) findViewById(R.id.textViewEmail);
			tvFacebook = (TextView) findViewById(R.id.textViewFacebook);

			btnEditEmail = (Button) findViewById(R.id.btnEditEmail);
			btnEditFacebook = (Button) findViewById(R.id.btnEditFacebook);
			btnEditPassword = (Button) findViewById(R.id.btnEditPassword);
			btnSave = (Button) findViewById(R.id.btnSaveChanges);
			btnCancelChanges = (Button) findViewById(R.id.btnCancelChange);
			tvName.setText(user.getName().toString());
			tvUser.setText(user.getUsername().toString());
			tvEmail.setText(user.getEmail().toString());
			if (user.getFacebookProf() != null) {
				tvFacebook.setText(user.getFacebookProf().toString());
			} else {
				tvFacebook.setText("");
			}
			if (haveNetworkConnection() == true
					&& user.getFacebookProf() != null) {
				new DownloadImageTask((ImageView) findViewById(R.id.avatar))
						.execute("https://graph.facebook.com/"
								+ user.getFacebookProf()
								+ "/picture?type=large");
			} else {
				if (user.getSex().equals("M")) {
					ImageView iv = (ImageView) findViewById(R.id.avatar);
					iv.setImageResource(R.drawable.ic_nointernetboy);
				} else {
					ImageView iv = (ImageView) findViewById(R.id.avatar);
					iv.setImageResource(R.drawable.ic_nointernetgirl);
				}
			}

			btnSave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					db = new DatabaseHandler(getApplicationContext());
					if (db.updateUser(user) && k == 1) {
						session.createLoginSession(user.getName(),
								user.getUsername(), user.getEmail(),
								user.getFacebookProf(), user.getPassword(),
								user.getSex());
						/*
						 * Fragment fragment = new HomeFragment();
						 * FragmentManager fragmentManager =
						 * getFragmentManager();
						 * fragmentManager.beginTransaction()
						 * .replace(R.id.frame_container, fragment).commit();
						 * setTitle("Home");
						 */
						Intent dashBoard = new Intent(Profile.this,
								HomeActivity.class);
						startActivity(dashBoard);
						Toast.makeText(getApplicationContext(), "SUCCESS!",
								Toast.LENGTH_LONG).show();

					} else {
						Intent dashBoard = new Intent(Profile.this,
								HomeActivity.class);
						startActivity(dashBoard);
						Toast.makeText(getApplicationContext(),
								"NO CHANGES WERE MADE", Toast.LENGTH_LONG)
								.show();

					}
				}
			});
			btnCancelChanges.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					k = 0;
					Toast.makeText(getApplicationContext(),
							"NO CHANGES WERE MADE", Toast.LENGTH_LONG).show();

				}
			});
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), "Please login!",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(Profile.this, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			startActivity(i);
		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {

				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if (result != null)
				bmImage.setImageBitmap(result);
			else {
				if (user.getSex().equals("M"))
					result = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_nointernetboy);
				else
					result = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_nointernetgirl);
				bmImage.setImageBitmap(result);
			}
		}
	}

	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
}
