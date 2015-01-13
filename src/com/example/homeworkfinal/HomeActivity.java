package com.example.homeworkfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends Activity {
	private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
	private EditText location, country, temperature, humidity, pressure;
	private HandleJSON obj;
	private Button btnProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		location = (EditText) findViewById(R.id.etLocation);
		country = (EditText) findViewById(R.id.etCountry);
		temperature = (EditText) findViewById(R.id.etTemperature);
		humidity = (EditText) findViewById(R.id.etHumidity);
		pressure = (EditText) findViewById(R.id.etPressure);

		btnProfile = (Button) findViewById(R.id.btnProfile);

		btnProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, Profile.class));
			}
		});
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items
	// // to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	public void open(View view) {
		String url = location.getText().toString();
		String finalUrl = url1 + url + "&units=metric";
		country.setText(finalUrl);
		obj = new HandleJSON(finalUrl);
		obj.fetchJSON();

		while (obj.parsingComplete)
			;
		country.setText(obj.getCountry());
		temperature.setText(obj.getTemperature());
		humidity.setText(obj.getHumidity());
		pressure.setText(obj.getPressure());

	}
}
