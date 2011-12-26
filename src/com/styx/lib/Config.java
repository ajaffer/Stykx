package com.styx.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.StringWriter;

import android.content.Context;
import android.util.Log;

public class Config implements Serializable {
	private static String FILENAME = "configuration";
	private static final long serialVersionUID = 4895755897781993885L;
	private float height;
	private float uprightAngle;
	private static Config _instance;
	static private Context context;

	public Config(Context context) {
		Config.context = context;
	}

	public static void setupInstance(Context context) {
		_instance = loadFromFile(context);

		if(_instance!=null){
			Config.context = context;
		}
		else{
			_instance = new Config(context);
			_instance.saveToFile();
		}
	}

	public static float getHeight() {
		return _instance.height;
	}

	public static void setHeight(float height) {
		_instance.height = height;
		_instance.saveToFile();
	}

	public static float getUprightAngle() {
		return _instance.uprightAngle;
	}

	public static void setUprightAngle(float uprightAngle) {
		if(uprightAngle == 90){
			Log.i("Confg", "upright angle was cropped to 89");
			uprightAngle = 89;
		}
		_instance.uprightAngle = uprightAngle;
		_instance.saveToFile();
	}

	private void saveToFile() {
		Log.d("config", "saved: " + this.toString());

		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = Config.context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
		} catch (FileNotFoundException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		} catch (StreamCorruptedException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		}
		try {
			fos.close();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		}
	}
	private static Config loadFromFile(Context context) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		Config config = null;
		try {
			fis = context.openFileInput(FILENAME);
			in = new ObjectInputStream(fis);
			config = (Config) in.readObject();
		} catch (FileNotFoundException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		} catch (StreamCorruptedException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		} catch (ClassNotFoundException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		}
		try {
			if (fis != null)
				fis.close();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();

			Log.e("config", stacktrace);
		}

		return config;
	}

	public static String getHeightFeet() {
		return (int)(getHeight()/30)+"";
	}

	public static String getHeightInches() {
		return (getHeight()%30)+"";

	}

	@Override
	public String toString() {
		return "height:"+height+",uprightAngle:"+uprightAngle;
	}

}