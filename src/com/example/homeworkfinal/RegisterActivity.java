package com.example.homeworkfinal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	DatabaseHandler db = new DatabaseHandler(this);
	EditText name = null;
	EditText username = null;
	EditText password = null;
	EditText email = null;
	EditText facebook = null;
	RadioGroup rg = null;
	Spinner spinnerVarsta = null;
	Button registerButton = null;
	User user = new User();
	String spinnerAge = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		name = (EditText) findViewById(R.id.registerName);
		username = (EditText) findViewById(R.id.registerUsername);
		password = (EditText) findViewById(R.id.regPassword);
		email = (EditText) findViewById(R.id.registerEmail);
		facebook = (EditText) findViewById(R.id.registerFacebook);
		spinnerVarsta = (Spinner) findViewById(R.id.spinnerVarsta);
		registerButton = (Button) findViewById(R.id.RegisterBTNRegister);
		rg = (RadioGroup) findViewById(R.id.rgSex);

		spinnerVarsta.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				spinnerAge = parent.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (name.getText().toString().trim().length() >= 6) {
					user.setName(name.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(),
							"Name must be atleast 6 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				if (username.getText().toString().trim().length() >= 4) {
					user.setUsername(username.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(),
							"User must be atleast 4 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				if (password.getText().toString().trim().length() >= 6) {
					user.setPassword(password.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(),
							"Password must be atleast 6 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				if (email.getText().toString().contains("@")
						&& email.getText().toString().trim().length() >= 6) {
					user.setEmail(email.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(),
							"Email must be atleast 6 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				user.setFacebookProf(facebook.getText().toString());
				if (((RadioButton) findViewById(R.id.radioMale)).isChecked() == true) {
					user.setSex("M");
				} else {
					user.setSex("F");
				}
				user.setVarsta(Integer.parseInt(spinnerAge));

				User user1 = new User();
				user1 = db.checkUser(user.getUsername());
				if (user1 != null) {
					Toast.makeText(getApplicationContext(),
							"Your account already exist", Toast.LENGTH_LONG)
							.show();
				} else if (user.getName() != null && user.getUsername() != null
						&& user.getPassword() != null
						&& user.getEmail() != null) {

					db.addUser(user);
					Toast.makeText(getApplicationContext(),
							"Your account was created succesfully!",
							Toast.LENGTH_LONG).show();
					finish();

				}

			}
		});

	}
}
