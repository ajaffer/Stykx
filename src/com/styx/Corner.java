package com.styx;

import java.io.Serializable;

import com.styx.lib.Util;


public class Corner implements Serializable{

	private static final long serialVersionUID = -4710121729172703595L;
	public float x, y;
	public Orientation o;
	private float zoomHeight;
	private int maxRadius;
	private int cx;
	private int cy;

	public Corner(float x, float y, Orientation o){
		this.x = x;
		this.y = y;
		this.o = o;
	}

	public Corner(int cx, int cy, int maxRadius, Orientation o, float zoomHeight) {
		this.zoomHeight = zoomHeight;
		this.maxRadius = maxRadius;
		this.cx = cx;
		this.cy = cy;
		this.o = o;

		calculateCartesianCoordinates();
	}

	public void calculateCartesianCoordinates() {
		float distance = Util.getDistance(o, zoomHeight);
		distance = Util.scale(maxRadius, distance, Util.getHorizon());

		this.y = (float) (cy + distance * Math.sin(o.azimuth-Math.PI/2));
		this.x = (float) (cx + distance * Math.cos(o.azimuth-Math.PI/2));
	}


	public String toString(){
		return "x:"+x+",y:"+y+",Orientation:"+o;
	}
}
