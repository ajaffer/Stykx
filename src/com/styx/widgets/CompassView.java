package com.styx.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.styx.Corner;
import com.styx.Orientation;
import com.styx.lib.Config;
import com.styx.lib.Util;

public class CompassView extends View {
	private static Canvas mCanvas;
	private int cx, cy, r1, r2, r3;
	private float[] pts = { 0.0f, 0.0f, 0.0f, 0.0f };

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mCanvas = canvas;

		cx = canvas.getWidth() / 11;
		cy = canvas.getHeight() / 8;
		r1 = canvas.getWidth() / 36;
		r2 = canvas.getWidth() / 18;
		r3 = canvas.getWidth() / 12;

		Paint outline = new Paint();
		outline.setColor(Color.BLACK);
		outline.setStyle(Paint.Style.STROKE);

		canvas.drawCircle(cx, cy, r1, outline);
		canvas.drawCircle(cx, cy, r2, outline);
		canvas.drawCircle(cx, cy, r3, outline);

		Paint direction = new Paint();
		direction.setColor(Color.RED);
		direction.setStyle(Paint.Style.FILL_AND_STROKE);
		mCanvas.drawLines(pts, direction);
	}

	public void updateView(Orientation o) {
		if (mCanvas != null) {
			Corner corner = Util.toCorner(cx, cy, r3, o, Config.getHeight());
			pts = new float[] { cx, cy, corner.x, corner.y };
			invalidate();
		}
	}


}
