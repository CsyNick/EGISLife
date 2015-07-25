package com.example.object;


public class Point{
	public String PointName;
	public double lon = 0.0;
	public double lat = 0.0;
	
	public Point(double lon, double lat){
		this.lon = lon;
		this.lat = lat;
	}
	
	public int getLongitudeE6(){
		return (int)(lon * 1E6);
	}
	
	public int getLatitudeE6(){
		return (int)(lat * 1E6);
	}

	public double getLongitude(int precision){
		double factor = (int)Math.pow(10, precision);
		return ((int)(lon * factor))/factor;
	}
	
	public double getLatitude(int precision){
		double factor = (int)Math.pow(10, precision);
		return ((int)(lat * factor))/factor;
	}
}