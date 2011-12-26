package com.styx.configuration;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.styx.R;
import com.styx.lib.Config;

public class ConfigurationActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configure);

		((EditText) findViewById(R.id.heightFeet)).setText(Config
				.getHeightFeet());
		((EditText) findViewById(R.id.heightInches)).setText(Config
				.getHeightInches());
		((TextView) findViewById(R.id.configureStatus)).setText("");
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		saveToFile();
		super.onStop();
	}

	protected void onPause() {
		saveToFile();
		super.onPause();
	}

	private void saveToFile() {
		Config.setHeight(calcHeight());
		((TextView) findViewById(R.id.configureStatus))
				.setText("Configuration Saved.");
	}

	private float calcHeight() {
		float heightFeet = Float
				.parseFloat(((EditText) findViewById(R.id.heightFeet))
						.getText().toString());

		float heightInches = Float
				.parseFloat(((EditText) findViewById(R.id.heightInches))
						.getText().toString());

		float cms = (float) (heightFeet * 30 + heightInches * 2.5);

		return cms;
	}
}