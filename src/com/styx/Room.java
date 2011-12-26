package com.styx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.styx.lib.Config;
import com.styx.lib.Util;

public class Room implements Serializable, Parcelable {
	private static final long serialVersionUID = -6787794622520280846L;
	// public int markedCorners = 0;
	private int maxRadius;
	private int cx, cy;
	private float zoomHeight;
	private Corner farthest;

	private static final int MAX_ORIENTATIONS = 100;
	private Orientation orientations[] = new Orientation[MAX_ORIENTATIONS];
	private float[] points;
	private List<Corner> corners;
	float minx, maxx, miny, maxy;
	Corner left, right, top, bottom;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelableArray(orientations, flags);
		out.writeFloatArray(points);
		out.writeInt(maxRadius);
		out.writeInt(cx);
		out.writeInt(cy);
	}

	public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {
		public Room createFromParcel(Parcel in) {
			return new Room(in);
		}

		public Room[] newArray(int size) {
			return new Room[size];
		}
	};

	private Room(Parcel in) {
		orientations = (Orientation[]) in.readParcelableArray(null);
		points = in.createFloatArray();
		maxRadius = in.readInt();
		cx = in.readInt();
		cy = in.readInt();
	}

	// public Room(float[] points) {
	// this.points = points;
	// }

	public Room(int cx, int cy, int maxRadius, Orientation orientations[]) {
		this.maxRadius = maxRadius;
		this.cx = cx;
		this.cy = cy;
		this.orientations = orientations;
		this.setZoom(Config.getHeight());
		this.points = getPoints(this.orientations, cx, cy, maxRadius);
	}

	// public void addOrientation(Orientation o) {
	// this.orientations[markedCorners++] = o;
	// }

	public void draw(Canvas canvas, int cx, int cy, int maxRadius) {
		this.cx = cx;
		this.cy = cy;
		this.maxRadius = maxRadius;

		this.points = getPoints(this.orientations, cx, cy, maxRadius);

		draw(canvas);
	}

	public void draw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		canvas.drawLines(this.points, p);
		drawLabels(canvas);
	}

	public void drawLabels(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.WHITE);

		int i = 0;
		while (i < this.points.length) {
			float x1 = this.points[i];
			float y1 = this.points[i + 1];
			float x2 = this.points[i + 2];
			float y2 = this.points[i + 3];

			int distance = (int) Math.sqrt(Math.pow((x1 - x2), 2)
					+ Math.pow((y1 - y2), 2));
			distance = (int) Util.descale(distance, this.maxRadius);
			canvas.drawText("" + Util.cmToFeetAndInches(distance),
					(x1 + (x2 - x1) / 2), (y1 + (y2 - y1) / 2), p);
			i += 4;
		}
	}

	public void drawConvexHull(Canvas canvas) {
		calcConvexHull();
		Paint p = new Paint();
		p.setColor(Color.BLUE);

		canvas.drawLines(getConvexHullPoints(), p);
	}

	private float[] getPoints(Orientation orientations[], int cx, int cy,
			int maxRadius) {
		// return rescale(toPoints(toCorners(orientations, cx, cy)), cx, cy,
		// maxRadius);
		// return rescale(toPoints(toCorners(orientations, cx, cy)), cx, cy,
		// maxRadius);
		// float points[] = toPoints(toCorners(orientations, cx, cy,
		// maxRadius));
		corners = toCorners(orientations, cx, cy, maxRadius);
		//recenter(corners);
		float points[] = toPoints(corners);
//		float points[] = recenter(toPoints(corners), cx, cy);

		// calcConvexHull(points);

		return points;
	}

	private float[] getConvexHullPoints() {
		return new float[] { minx, maxy, maxx, maxy, maxx, maxy, maxx, miny,
				maxx, miny, minx, miny, minx, miny, minx, maxy };
	}

	public void recenter(List<Corner> corners){
		calcConvexHull();
		float azimuth, pitch;

		azimuth = (float) Math.atan((cx - (minx + (maxx - minx) / 2)) / zoomHeight);
		pitch = (float) Math.atan((cy - (miny + (maxy - miny) / 2)) / zoomHeight);
		translate(azimuth, pitch, corners);
	}


//	public float[] recenter(float[] points, int cx, int cy) {
//		calcConvexHull();
//		points = traslate(points, cx - (minx + (maxx - minx) / 2), cy
//				- (miny + (maxy - miny) / 2));
//		return points;
//	}

	public void translate(float azimuth, float pitch, List<Corner> corners) {
		for(Corner corner : corners) {
			corner.o.azimuth += azimuth;
			corner.o.pitch += pitch;
			corner.calculateCartesianCoordinates();
		}
	}

	public float[] translateCenterToOrigin(float[] points, int cx, int cy) {
		calcConvexHull();
		points = traslate(points, cx - (cx + (maxx - minx) / 2), cy
				- (cy + (maxy - miny) / 2));
		return points;
	}

//	public float[] rescale() {
////		points = translateCenterToOrigin(points, cx, cy);
//
//		float xScale = (cx + maxRadius) / maxx;
//		float yScale = (cy + maxRadius) / maxy;
//		for (int i = 0; i < points.length; i++) {
//			if (i % 2 == 0) {
//				points[i] *= xScale;
//			} else {
//				points[i] *= yScale;
//			}
//		}
//
//		points = recenter(points, cx, cy);
//		return points;
//	}

	private float[] traslate(float[] points, float transX, float transY) {
		// Log.d("translate", "transX:"+transX+",transY:"+transY);
		// Log.d("Room-pre-translation", Util.toString(points));
		for (int i = 0; i < points.length; i++) {
			if (i % 2 == 0)
				points[i] += transX;
			else if (i % 2 != 0)
				points[i] += transY;
		}
		// Log.d("Room-post-translation", Util.toString(points));
		return points;
	}

	private void calcConvexHull() {
		if (corners.size() < 2) {
			Log.i("room", "corners less than 2:" + corners.size());
			return;
		}
		left = corners.get(0);
		right = corners.get(0);
		top = corners.get(0);
		bottom = corners.get(0);

		for(Corner corner : corners) {
			if(left.x < corner.x)
				left = corner;
			if(right.x > corner.x)
				right = corner;
			if(top.y < corner.y)
				top = corner;
			if(bottom.y > corner.y)
				bottom = corner;
		}

		if(Math.abs(right.x-cx) < Math.abs(left.x-cx))
			farthest = left;
		else
			farthest = right;
		if(Math.abs(farthest.y-cy) < Math.abs(top.y-cy))
			farthest = top;
		else if(Math.abs(farthest.y-cy) < Math.abs(bottom.y-cy))
			farthest = bottom;


		minx = left.x;
		maxx = right.x;
		miny = top.y;
		maxy = bottom.y;
	}

//	private void calcConvexHull(float[] points) {
//		if (points == null) {
//			return;
//		}
//		if (points.length < 2) {
//			Log.i("room", "points less than 2:" + points.length);
//			return;
//		}
//		minx = points[0];
//		maxx = points[0];
//		miny = points[1];
//		maxy = points[1];
//
//		for (int i = 0; i < points.length; i++) {
//			if (i % 2 == 0) {
//				if (points[i] < minx)
//					minx = points[i];
//				if (points[i] > maxx)
//					maxx = points[i];
//			} else {
//				if (points[i] < miny)
//					miny = points[i];
//				if (points[i] > maxy)
//					maxy = points[i];
//			}
//		}
//	}

	public String toString() {
		if (this.points == null) {
			Log.i("room-tostring", "points are null");
			return "";
		}
		StringBuilder str = new StringBuilder();

		str.append("Points: ");
		for (float point : this.points) {
			str.append(point);
			str.append(", ");
		}

		return str.toString();
	}

	private float[] toPoints(List<Corner> corners) {
		if (corners.size() == 0)
			return new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
		// float points[] = new float[4*corners.size()];
		List<Float> points = new ArrayList<Float>(4 * corners.size());
		Corner prevCorner = corners.get(0);
		for (int i = 1; i < corners.size(); i++) {
			Corner corner = corners.get(i);

			points.add((float) prevCorner.x);
			points.add((float) prevCorner.y);
			points.add((float) corner.x);
			points.add((float) corner.y);

			prevCorner = corner;
		}

		points.add((float) prevCorner.x);
		points.add((float) prevCorner.y);
		points.add((float) corners.get(0).x);
		points.add((float) corners.get(0).y);

		// Log.d("corners", "corners len: " + corners.size() + ", "
		// + Util.toString(corners));
		// Log.d("points", "points len: " + points.size() + ", points: "
		// + Util.toString(points));
		float[] f = Util.toArray(points);
		// Log.d("points", "points len: " + f.length + ", points: " +
		// Util.toString(f));

		return f;
	}

	private List<Corner> toCorners(Orientation orientations[], int cx, int cy,
			int maxRadius) {
		List<Corner> corners = new ArrayList<Corner>();

		for (Orientation orientation : orientations) {
			corners.add(new Corner(cx, cy, maxRadius, orientation, zoomHeight));
		}

		return corners;
	}

	// public int getMaxRadius() {
	// calcConvexHull(this.points);
	// return (int) Math.max((maxx - cx),
	// Math.max(cx - minx, Math.max(maxy - cy, cy - miny)));
	// }

	public void drawScales(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		int divisions = 2;
		int unit = maxRadius * 2 / divisions;
		int originX = cx - maxRadius;
		int originY = cy + maxRadius;

//		Log.i("Room", "getScaleHorizon: " +  Util.getScaleHorizon(zoomHeight) / divisions);
//		Log.i("Room", "descale: " +  Util.descale(Util.getScaleHorizon(zoomHeight) / divisions, maxRadius));
//		Log.i("Room", "cmToFeetAndInches" +  Util.cmToFeetAndInches((int) Util.descale(Util.getScaleHorizon(zoomHeight) / divisions, maxRadius)));

		for (int i = 0; i <= divisions; i++) {
			String distance = Util.cmToFeetAndInches((int) (i * Util.getScaleHorizon(zoomHeight) / divisions));
			canvas.drawText(distance, originX + unit * i, originY, paint);
			canvas.drawText(distance, originX, originY - unit * i, paint);
		}
	}

	public float getZoom() {
		return zoomHeight;
	}

	public void setZoom(float zoom) {
		this.zoomHeight = zoom;
	}
}
