package com.styx.calibrate;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.styx.R;
import com.styx.lib.Config;
import com.styx.measurement.Orientation;

public class CalibrateActivity extends Activity implements SensorEventListener {
	private float[] geomagnetic, gravity;
	private SensorManager sensorManager;
	private float values[] = { 0.0f, 0.0f, 0.0f };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calibrate);

		((TextView) findViewById(R.id.currentAngle))
				.setText("" + Config.getUprightAngle());

		// Get instance of Vibrator from current Context
		final Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


		final Button button = (Button) findViewById(R.id.configButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((TextView) findViewById(R.id.calibrationErrorMessage))
						.setText("");

				Orientation o = new Orientation(values);
				if (not_vertical(o.pitchDegree)) {
					((TextView) findViewById(R.id.calibrationErrorMessage))
							.setText("Phone is not vertical, try again!");

					vib.vibrate(300);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						Log.i("config", "thread interrupted: " + e.getMessage());
					}
					vib.vibrate(300);
				} else {
					Config.setUprightAngle(o.pitchDegree);
					((TextView) findViewById(R.id.currentAngle)).setText(""
							+ o.pitchDegree);
					((TextView) findViewById(R.id.calibrationErrorMessage))
							.setText("Successfully configured!");

					vib.vibrate(300);
				}
			}

			private boolean not_vertical(float pitchDegree) {
				return pitchDegree < 80;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		sensorManager.unregisterListener(this);

		super.onStop();
	}

	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		values = event.values;
	}

}