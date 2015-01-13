package com.example.homeworkfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText username = null;
	EditText password = null;
	Button btnLogin = null;
	Button btnRegister = null;
	User user = new User();
	DatabaseHandler db = new DatabaseHandler(this);
	SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		session = new SessionManager(getApplicationContext());

		btnRegister = (Button) findViewById(R.id.buttonRegister);
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent registerInt = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(registerInt);
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				username = (EditText) findViewById(R.id.editTextUsername);
				password = (EditText) findViewById(R.id.editTextPassword);
				user = db.getUser(username.getText().toString(), password
						.getText().toString());
				if (user != null) {
					session.createLoginSession(user.getName(),
							user.getUsername(), user.getEmail(),
							user.getFacebookProf(), user.getPassword(),
							user.getSex());
					Intent dashBoard = new Intent(LoginActivity.this,
							HomeActivity.class);
					startActivity(dashBoard);
					Toast.makeText(getApplicationContext(),
							"Logged in succesfully!", Toast.LENGTH_LONG).show();
					finish();
				}

				else {
					Toast.makeText(getApplicationContext(), "Login Failed",
							Toast.LENGTH_LONG).show();
				}
			}
		});

	}

}
