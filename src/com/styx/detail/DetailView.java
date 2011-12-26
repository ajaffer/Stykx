package com.styx.detail;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.styx.measurement.Room;

public class DetailView extends View {

	private List<Room> rooms;
	private float cx, cy;
	private float boundingBoxWidth, boundingBoxHeight;
	private float dropAreaCx, dropAreaCy, dropAreaWidth, dropAreaHeight;

	public DetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		rooms = new ArrayList<Room>();
	}

	public void addRoom(Room room) {
		rooms.add(room);
	}

	public List<Room> getRooms() { return rooms; }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for(Room room : rooms){
			int cx = canvas.getWidth()/2;
			int cy = canvas.getHeight()/4;

			int scaleFactor = canvas.getHeight()/4;

			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			canvas.drawCircle(cx, cy, canvas.getHeight()/4, paint);

			room.draw(canvas, cx, cy, scaleFactor);
		}
	}

	private void drawDropAreaBoundingBox(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		canvas.drawCircle(dropAreaCx, dropAreaCy, dropAreaHeight, p);

		p.setColor(Color.RED);
		float[] pts = new float[]{
				dropAreaCx-dropAreaWidth/2, dropAreaCy-dropAreaHeight/2, dropAreaCx+dropAreaWidth/2, dropAreaCy-dropAreaHeight/2,
				dropAreaCx+dropAreaWidth/2, dropAreaCy-dropAreaHeight/2, dropAreaCx+dropAreaWidth/2, dropAreaCy+dropAreaHeight/2,
				dropAreaCx+dropAreaWidth/2, dropAreaCy+dropAreaHeight/2, dropAreaCx-dropAreaWidth/2, dropAreaCy+dropAreaHeight/2,
				dropAreaCx-dropAreaWidth/2, dropAreaCy+dropAreaHeight/2, dropAreaCx-dropAreaWidth/2, dropAreaCy-dropAreaHeight/2
				};

		canvas.drawLines(pts, p);

	}

	private void setupBounds(Canvas canvas) {
		cx = canvas.getWidth()/2;
		cy = canvas.getHeight()/2;
		boundingBoxWidth = (int) (canvas.getWidth() * 0.70);
		boundingBoxHeight = (int) (canvas.getHeight() * 0.70);
		dropAreaCx = (int) (canvas.getWidth() * 0.84);
		dropAreaCy = (int) (canvas.getHeight() * 0.20);
		dropAreaWidth = (int) (canvas.getWidth() * 0.15);
		dropAreaHeight = (int) (canvas.getWidth() * 0.15);
	}

	private void drawBoundingBox(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		float[] pts = new float[]{cx-boundingBoxWidth, cy-boundingBoxHeight, cx+boundingBoxWidth, cy-boundingBoxHeight,
				cx+boundingBoxWidth, cy-boundingBoxHeight, cx+boundingBoxWidth, cy+boundingBoxHeight,
				cx+boundingBoxWidth, cy+boundingBoxHeight, cx-boundingBoxWidth, cy+boundingBoxHeight,
				cx-boundingBoxWidth, cy+boundingBoxHeight, cx-boundingBoxWidth, cy-boundingBoxHeight};

		canvas.drawLines(pts, p);
	}

	public void eraseAllRooms() {
		rooms.clear();
	}

}
