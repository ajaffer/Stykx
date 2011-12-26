package com.styx;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Orientation implements Serializable, Parcelable {

	private static final long serialVersionUID = 6582338804325735989L;
	//roll is not needed
	public float azimuth, pitch, roll;
	public float azimuthDegree, pitchDegree, rollDegree;

	public Orientation(float azimuth, float pitch, float roll) {
		this.azimuthDegree = Math.round(azimuth);
		this.pitchDegree = Math.round(pitch);
		this.rollDegree = Math.round(roll);
		this.azimuth = (float) Math.toRadians(azimuthDegree);
		this.pitch = (float) Math.toRadians(pitchDegree);
		this.roll = (float) Math.toRadians(rollDegree);
	}

	public Orientation(float[] values) {
		this(values[0], values[2], values[1]);
	}

	@Override
	public String toString() {
		return "azimuth:"+Math.toDegrees(azimuth)+", pitch:"+Math.toDegrees(pitch)+", roll:"+Math.toDegrees(roll);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeFloat(azimuthDegree);
		out.writeFloat(pitchDegree);
		out.writeFloat(rollDegree);
		out.writeFloat(azimuth);
		out.writeFloat(pitch);
		out.writeFloat(roll);
	}

	public static final Parcelable.Creator<Orientation> CREATOR = new Parcelable.Creator<Orientation>() {
		public Orientation createFromParcel(Parcel in) {
			return new Orientation(in);
		}

		public Orientation[] newArray(int size) {
			return new Orientation[size];
		}
	};

	private Orientation(Parcel in) {
		azimuthDegree = in.readFloat();
		pitchDegree = in.readFloat();
		rollDegree = in.readFloat();
		azimuth = in.readFloat();
		pitch = in.readFloat();
		roll = in.readFloat();
	}

	public void rescale(float factor) {
		this.azimuthDegree *= factor;
		this.pitchDegree *= factor;
		this.rollDegree *= factor;
		this.azimuth = (float) Math.toRadians(azimuthDegree);
		this.pitch = (float) Math.toRadians(pitchDegree);
		this.roll = (float) Math.toRadians(rollDegree);
	}



}
