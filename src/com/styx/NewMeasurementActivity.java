package com.styx;

import java.util.ArrayList;
import java.util.List;

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

public class NewMeasurementActivity extends Activity implements SensorEventListener {
	public static float mOrientationValues[] = new float[3];
//	public static Room room;

	private static final String TAG = "TAG";
	private SensorManager sensorManager;
	private TextView compassDirection;
	private MeasurementPreview measurementPreview;

//	private SensorManager mSensorManager;
//	private Sensor mAccelereometer;
//	private Sensor mMagnetic;

//	private static final int MAX_CORNERS = 50;
//	private List<Orientation> orientations = new ArrayList<Orientation>(MAX_CORNERS);

	// Orientation init values
	private float frontAngle = 0.0f;

	// private CameraPreview mPreview;

	private void launchMeasurementPreview() {
//		Intent i = new Intent(this, FloorPlanActivity.class);
//		i.putExtra("room", room);
//		startActivity(i);

		((StykxActivity)this.getParent()).getTabHost().setCurrentTab(1);
	}

//	private float[] toPoints(List<Corner> corners) {
//		// float points[] = new float[4*corners.size()];
//		List<Float> points = new ArrayList<Float>(4 * corners.size());
//		Corner prevCorner = corners.get(0);
//		int k = 0;
//		for (int i = 1; i < corners.size(); i++) {
//			Corner corner = corners.get(i);
//
//			points.add((float) prevCorner.x);
//			points.add((float) prevCorner.y);
//			points.add((float) corner.x);
//			points.add((float) corner.y);
//
//			prevCorner = corner;
//		}
//
//		points.add((float) prevCorner.x);
//		points.add((float) prevCorner.y);
//		points.add((float) corners.get(0).x);
//		points.add((float) corners.get(0).y);
//
//		Log.d("corners", "corners len: " + corners.size() + ", "
//				+ Util.toString(corners));
//		Log.d("points", "points len: " + points.size() + ", points: "
//				+ Util.toString(points));
//		float[] f = Util.toArray(points);
//		Log.d("points", "points len: " + f.length + ", points: " + Util.toString(f));
//
//		return f;
//	}



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		compassDirection = (TextView) findViewById(R.id.cross);
//		this.room = new Room();
//		compassDirection.setText("");

//		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//		mAccelereometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//		mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		// Create our Preview view and set it as the content of our activity.
		// mPreview = new CameraPreview(this);
		// setContentView(mPreview);
		setContentView(R.layout.newmap);

		final Button button = (Button) findViewById(R.id.markCorner);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Orientation orientation = new Orientation(mOrientationValues);
//				if (orientations.size() == 0) {
//					frontAngle = mOrientationValues[0];
//					orientation = new Orientation(0, Config.uprightAngle
//							+ mOrientationValues[1], mOrientationValues[2]);
//				} else
//					orientation = new Orientation(frontAngle
//							- mOrientationValues[0], Config.uprightAngle
//							+ mOrientationValues[1], mOrientationValues[2]);

//				Log.d("orientation", orientation.toString());
				measurementPreview = (MeasurementPreview) findViewById(R.id.measuremenPreview);
				measurementPreview.addOrientation(orientation);
//				orientations.add(orientation);

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

//		final Button finishMarking = (Button) findViewById(R.id.FinishMarking);
//		finishMarking.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
////				List<Corner> corners = toCorners(orientations);
//				launchMeasurementPreview();
//			}
//
////			private List<Corner> toCorners(List<Orientation> orientations) {
////				List<Corner> corners = new ArrayList<Corner>();
////
////				for (Orientation orientation : orientations) {
////					corners.add(toCorner(orientation));
////				}
////
////				// corners.add(toCorner(orientations.get(0)));
////
////				return corners;
////			}
////
////			private Corner toCorner(Orientation orientation) {
////				double y = Config.height * Math.tan(orientation.pitch);
////				double x = y * Math.tan(orientation.azimuth);
////				Corner corner = new Corner(x, y);
////				Log.d("toCorner", "orientation:" + orientation + " corner:"
////						+ corner);
////				return corner;
////			}
//		});

		// setContentView(R.layout.newmap);
		// CharSequence text = "this is a test";
		// ((TextView)findViewById(R.id.TextView01)).setText(text);
	}

//	public void onSensorChanged(int sensor, float[] values) {
//
//		synchronized (this) {
//			if (sensor == SensorManager.SENSOR_ORIENTATION) {
//				for (int i = 0; i < 3; i++) {
//					mOrientationValues[i] = values[i];
//				}
//
//				Log.d(TAG, "sensor: " + sensor + ", azimuth: " + values[0] + ", pitch: "
//						+ values[1] + ", roll: " + values[2]);
//
//				Orientation o = new Orientation(values);
//				Log.d(TAG, "orientation: " + sensor + ", azimuth: " + o.azimuth + ", pitch: "
//						+ o.pitch + ", roll: " + o.roll);
//
//				((CompassView) this.findViewById(R.id.compassView)).updateView(o);
//				((TextView) this.findViewById(R.id.txt_info)).setText(o.toString());
//
//				// ((TextView)findViewById(R.id.azimuthValue)).setText(mOrientationValues[0]+"");
//				// ((TextView)findViewById(R.id.pitchValue)).setText(mOrientationValues[1]+"");
//				// ((TextView)findViewById(R.id.rollValue)).setText(mOrientationValues[2]+"");
//
//				// Orientation o = new
//				// Orientation(mOrientationValues[0],mOrientationValues[1],mOrientationValues[2]);
//				// Log.d("orientation", o.toString());
//				// ((TextView)findViewById(R.id.TextView01)).setText(mOrientationValues[0]+"");
//			}
//		}
//	}


	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);

//		mSensorManager.registerListener(this, mAccelereometer, SensorManager.SENSOR_DELAY_UI);
//		mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onStop() {
		sensorManager.unregisterListener(this);
//		mSensorManager.unregisterListener(this);

		super.onStop();
	}

    protected void onPause() {
        super.onPause();
		sensorManager.unregisterListener(this);
//        mSensorManager.unregisterListener(this);
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	float[] geomagnetic,gravity;
	@Override
	public void onSensorChanged(SensorEvent event) {
//		if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
//			geomagnetic = event.values;
//		}
//		else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
//			gravity = event.values;
//		}
//		if(geomagnetic!=null && gravity !=null){
//			float r[]={0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f},i[]={0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f},values[]={0.0f,0.0f,0.0f};
//			SensorManager.getRotationMatrix(r, i, gravity, geomagnetic);
////			Log.d("sensor-cng", "r:"+toString(r)+", i"+toString(i)+", gravity"+toString(gravity)+", geomagnetic"+toString(geomagnetic)+", values"+toString(values));
//			SensorManager.getOrientation(r, values);
////			Log.d("sensor-cng", "values:"+toString(values));
//
//			if(values[0]!=0.0){
//				Orientation o = new Orientation(values);
//				((CompasView) this.findViewById(R.id.compasView)).updateView(o);
//			}
//		}

//		printDirection(event.values);
		Orientation o = new Orientation(event.values);
		((CompassView) this.findViewById(R.id.compassView)).updateView(o);
		((TextView) this.findViewById(R.id.compassDirection)).setText(getDirection(o));
		((TextView) this.findViewById(R.id.distance)).setText(Util.cmToFeetAndInches((int) Util.getDistance(o, Config.getHeight())));
		mOrientationValues = event.values;
//		Log.d("compass", o.toString());
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
