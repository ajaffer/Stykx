package com.styx.measurement;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.styx.R;
import com.styx.lib.Config;
import com.styx.lib.Util;
import com.styx.widgets.CompassView;

public class MeasurementActivity extends Activity implements SensorEventListener {
	public static float mOrientationValues[] = new float[3];
	private static final String TAG = "TAG";
	private SensorManager sensorManager;
	private TextView compassDirection;
	private Preview measurementPreview;

	private float frontAngle = 0.0f;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		compassDirection = (TextView) findViewById(R.id.cross);
		setContentView(R.layout.newmap);

		final Button button = (Button) findViewById(R.id.markCorner);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Orientation orientation = new Orientation(mOrientationValues);
				measurementPreview = (Preview) findViewById(R.id.measuremenPreview);
				measurementPreview.addOrientation(orientation);

				((TextView) findViewById(R.id.cornersMarkedCount))
						.setText(measurementPreview.getOrientationSize() + "");
			}
		});

		final Button resetCorners = (Button) findViewById(R.id.resetCorners);
		resetCorners.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				measurementPreview.clearOrientations();
				((TextView) findViewById(R.id.cornersMarkedCount))
				.setText("");
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
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	float[] geomagnetic,gravity;
	@Override
	public void onSensorChanged(SensorEvent event) {
		Orientation o = new Orientation(event.values);
		((CompassView) this.findViewById(R.id.compassView)).updateView(o);
		((TextView) this.findViewById(R.id.compassDirection)).setText(getDirection(o));
		((TextView) this.findViewById(R.id.distance)).setText(Util.cmToFeetAndInches((int) Util.getDistance(o, Config.getHeight())));
		mOrientationValues = event.values;
	}

	private String getDirection(Orientation o) {
		String direction = "?";
		if (o.azimuthDegree < 22)
			direction =  degreeSymbol(o.azimuthDegree) + "N";
		else if (o.azimuthDegree >= 22 && o.azimuthDegree < 67)
			direction = degreeSymbol(o.azimuthDegree) + "NE";
		else if (o.azimuthDegree >= 67 && o.azimuthDegree < 112)
			direction = degreeSymbol(o.azimuthDegree) + "E";
		else if (o.azimuthDegree >= 112 && o.azimuthDegree < 157)
			direction = degreeSymbol(o.azimuthDegree) + "SE";
		else if (o.azimuthDegree >= 157 && o.azimuthDegree < 202)
			direction = degreeSymbol(o.azimuthDegree) + "S";
		else if (o.azimuthDegree >= 202 && o.azimuthDegree < 247)
			direction = degreeSymbol(o.azimuthDegree) + "SW";
		else if (o.azimuthDegree >= 247 && o.azimuthDegree < 292)
			direction = degreeSymbol(o.azimuthDegree) + "W";
		else if (o.azimuthDegree >= 292 && o.azimuthDegree < 337)
			direction = degreeSymbol(o.azimuthDegree) + "NW";
		else if (o.azimuthDegree >= 337)
			direction = degreeSymbol(o.azimuthDegree) + "N";
		else
			direction = "?";

		return direction;
	}

	private String degreeSymbol(float azimuth){
		return ((" " + Math.round(azimuth)) + (char)176)+ " ";
	}

}
