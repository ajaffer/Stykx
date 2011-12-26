package com.styx.measurement;

import java.util.ArrayList;
import java.util.List;

import com.styx.lib.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Preview extends View {

	private List<Orientation> orientations = new ArrayList<Orientation>();
	private static Room room;

	public Preview(Context context, float[] points) {
		super(context);
	}

	public Preview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public static Room getRoom() {
		return room;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int maxRadius = (int) (canvas.getHeight() * .12);
		int cx = (int) (canvas.getWidth() / 11 * 9.5);
		int cy = (int) (canvas.getHeight() / 5);

		room = new Room(cx, cy, maxRadius,
				orientations.toArray(new Orientation[0]));

		drawBoundingBox(canvas, maxRadius, cx, cy);
		room.draw(canvas);
//		room.drawConvexHull(canvas);
		room.drawScales(canvas);
	}

	private void drawBoundingBox(Canvas canvas, int maxRadius, int cx, int cy) {
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		canvas.drawLine(cx - maxRadius, cy + maxRadius, cx - maxRadius, cy
				- maxRadius, p);
		canvas.drawLine(cx - maxRadius, cy - maxRadius, cx + maxRadius, cy
				- maxRadius, p);
		canvas.drawLine(cx + maxRadius, cy - maxRadius, cx + maxRadius, cy
				+ maxRadius, p);
		canvas.drawLine(cx + maxRadius, cy + maxRadius, cx - maxRadius, cy
				+ maxRadius, p);
	}

	public void addOrientation(Orientation orientation) {
		orientations.add(orientation);

	}

	public int getOrientationSize() {
		return orientations.size();
	}

	public void clearOrientations() {
		orientations.clear();
	}

	public Orientation[] getOrientationsArray() {
		return orientations.toArray(new Orientation[0]);
	}

}
