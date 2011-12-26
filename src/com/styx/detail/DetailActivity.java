package com.styx.detail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.styx.R;
import com.styx.measurement.Orientation;
import com.styx.measurement.Preview;
import com.styx.measurement.Room;

public class DetailActivity extends Activity {
	String FILENAME = "details";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void onResume() {
		super.onResume();
		final TextView mapDetailStatus = (TextView) findViewById(R.id.detailsMessage);
//		mapDetailStatus.setText("");
		// Bundle extras = getIntent().getExtras();
		// Room room = extras.getParcelable("room");

		// Log.d("floor-plan", "room received:" + room.toString());


		setContentView(R.layout.details);
//		Preview measurementPreview = (Preview) findViewById(R.id.measurementPreview);
		final DetailView detatilView = ((DetailView) findViewById(R.id.floorPlanView));;
//		if(measurementPreview!=null){
		Preview measurementPreview = (Preview) findViewById(R.id.measuremenPreview);
			Room room = measurementPreview.getRoom();
			detatilView.addRoom(room);
//		}else{
//			Log.e("detail", "could not find prev room");
//		}

		Button button = (Button) findViewById(R.id.saveToFile);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveRooms();
//				mapDetailStatus.setText("Successfully saved to file.");
			}
		});

		button = (Button) findViewById(R.id.loadFromFile);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				detatilView.eraseAllRooms();
				List<Room> rooms = loadPreviousRooms();

				for (Room r : rooms)
					detatilView.addRoom(r);

				Log.d("floor-plan", "loaded " + rooms.size() + " rooms from file.");
				detatilView.invalidate();
			}
		});
	}

	private void saveRooms() {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			out = new ObjectOutputStream(fos);
			out.writeObject(((DetailView) findViewById(R.id.floorPlanView))
					.getRooms());
		} catch (FileNotFoundException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		} catch (StreamCorruptedException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		}
		try {
			fos.close();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		}
	}

	private List<Room> loadPreviousRooms() {
		List<Room> rooms = new ArrayList<Room>();
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = openFileInput(FILENAME);
			in = new ObjectInputStream(fis);
			rooms = (List<Room>) in.readObject();
		} catch (FileNotFoundException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		} catch (StreamCorruptedException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		} catch (ClassNotFoundException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		}
		try {
			if (fis != null)
				fis.close();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("floorPlan", stacktrace);
		}

		return rooms;
	}
}