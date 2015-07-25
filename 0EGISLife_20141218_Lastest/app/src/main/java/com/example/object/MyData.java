package com.example.object;

import com.google.android.gms.maps.model.LatLng;

import android.graphics.drawable.Drawable;

public class MyData {
	private String text;
	private String id;
	private String address;
    private Drawable drawable;
    private double[] lnglat;
	public MyData(String text, String id,String address,Drawable drawable) {
		this.text = text;
		this.id = id;
		this.drawable = drawable;
		this.setAddress(address);
	}

	public MyData(String markName, String id, String address,
			Drawable drawable, double[] lnglat) {
		this.text = markName;
		this.id = id;
		this.drawable = drawable;
		this.setAddress(address);
		this.setLnglat(lnglat);

	}

	public String getText() {
		return text;
	}

	public String getId() {
		return id;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double[] getLnglat() {
		return lnglat;
	}

	public void setLnglat(double[] lnglat) {
		this.lnglat = lnglat;
	}
	
	public LatLng getLatLng(){
		LatLng latLng =new LatLng(lnglat[0], lnglat[1]);
		return latLng;
		
	}
}