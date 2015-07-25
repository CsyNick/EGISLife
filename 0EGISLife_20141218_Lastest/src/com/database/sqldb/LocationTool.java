package com.database.sqldb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

public class LocationTool {

	private static String TAG = "LocationTool";
	private static Activity activity;
	private static LocationManager locationManager;
	
	public LocationTool(Activity activity){
		LocationTool.activity = activity;
	}
	
	public Location getMyLocation() {
		locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		Location location = null;
		
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			Log.i(TAG, "NETWORK_PROVIDER");

		} else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			Log.i(TAG, "GPS_PROVIDER");

		} else {
			location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			Log.i(TAG, "NETWORK_PROVIDER");
		}
		
		return location;
	}
	
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2){
		float[] results = new float[1];  
	    Location.distanceBetween(lat1, lng1, lat2, lng2, results); 
	    return results[0]/1000;
	}
}
