package com.styx.lib;

import java.util.List;

import android.util.Log;

import com.styx.Corner;
import com.styx.Orientation;

public class Util {
	public static float[] toArray(List<Float> points) {
		float[] p = new float[points.size()];

		for (int i = 0; i < points.size(); i++) {
			p[i] = points.get(i);
		}

		return p;
	}

	public static String toString(List<?> items) {
		StringBuilder str = new StringBuilder();
		for (Object item : items) {
			str.append(item.toString());
		}

		return str.toString();
	}

	public static String toString(float[] points) {
		StringBuilder str = new StringBuilder();
		for (float point : points) {
			str.append(point);
			str.append(", ");
		}

		return str.toString();
	}

	public static double[] x(List<Corner> corners) {
		double x_values[] = new double[corners.size()];
		int i = 0;
		for (Corner corner : corners) {
			x_values[i++] = corner.x;
		}
		return x_values;
	}

	public static float getHorizon() { return (float) (Math.abs(Config.getHeight() * Math.tan(Math.toRadians(Config.getUprightAngle())))); }

	public static float descale(float distance, int maxRadius) {
		float horizon = getHorizon();
		return distance * (horizon / maxRadius);
	}

	public static Corner toCorner(int cx, int cy, int maxRadius, Orientation o, float zoomHeight) {
		float distance = getDistance(o, zoomHeight);
		distance = scale(maxRadius, distance, getHorizon());

		float y = (float) (cy + distance * Math.sin(o.azimuth-Math.PI/2));
		float x = (float) (cx + distance * Math.cos(o.azimuth-Math.PI/2));

		return new Corner(x, y, o);
	}


	public static float getDistance(Orientation o, float zoomHeight) {
		return (float) (zoomHeight * Math.tan(o.pitch));
	}

	public static float scale(int maxRadius, float distance, float horizon) {
		if (Math.abs(distance) > horizon) {
			Log.e("compass", distance + " was cropped");
			distance = horizon;
		}

		return distance * (maxRadius / horizon);
	}

	public static String cmToFeetAndInches(int distanceInCM) {
		int inches = (int) (distanceInCM / 2.5);
		return inches/12+"\'"+inches%12+"\"";
	}

	public static int getScaleHorizon(float zoomHeight) {
		int scaleHorizon = (int) (Math.tan(Math.toRadians(Config.getUprightAngle())) * zoomHeight);
//		Log.d("Util", "scaleHorizon: " + scaleHorizon);
		return scaleHorizon;
	}


}
