package com.example.google;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
 
public class GPSTracker extends Service implements LocationListener {
	private final String TAG = "GPSTracker";
    private final Context mContext;
 
    // flag for GPS status
    boolean isGPSEnabled = false;
 
    // flag for network status
    boolean isNetworkEnabled = false;
 
    // flag for GPS status
    boolean canGetLocation = false;
 
    Location location = null; // location
    double latitude; // latitude
    double longitude; // longitude
 
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
 
    // Declaring a Location Manager
    protected LocationManager locationManager;
 
    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }
 
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
 
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return location;
    }
 
    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
 
    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
 
        // return latitude
        return latitude;
    }
 
    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
 
        // return longitude
        return longitude;
    }
    /**
     * Function to get LatLng
     * 
     * @return Latlng
     * */
    public LatLng getLatLng(){
    	
    	LatLng latlng = new LatLng(latitude, longitude);
		return latlng;
    	
    }
    /**
     * Function to check GPS/wifi enabled
     * 
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
 
    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     * */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    @Override
					public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
					public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
 
        // Showing Alert Message
        alertDialog.show();
    }
	/**
	 * 
	 * @param location
	 */
	private String updateWithNewLocation(Location location) {
		String where = "";
		if (location != null) {

			double lng = location.getLongitude();

			double lat = location.getLatitude();
	
			float speed = location.getSpeed();

			long time = location.getTime();
	
	
		}else{
			where = "No location found.";
		}
		return where;
		

		
	}
	public String getLatLngToChineseAddress() throws IOException {

		Geocoder gc = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);
		List<Address> lstAddress = gc.getFromLocation(latitude,
				longitude, 1);
		String returnAddress = lstAddress.get(0).getAddressLine(0);

		lstAddress.get(0).getCountryName(); // 台灣省
		lstAddress.get(0).getAdminArea(); // 台北市
		lstAddress.get(0).getLocality(); // 中正區
		lstAddress.get(0).getThoroughfare(); // 信陽街(包含路巷弄)
		lstAddress.get(0).getFeatureName(); // 會得到33(號)

		return returnAddress;

	}
    @Override
    public void onLocationChanged(Location location) {
		updateWithNewLocation(location);
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    	updateWithNewLocation(null);
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    	
    	switch (status) {
	    case LocationProvider.OUT_OF_SERVICE:
	        Log.v(TAG, "Status Changed: Out of Service");
	        Toast.makeText(mContext, "Status Changed: Out of Service",
	                Toast.LENGTH_SHORT).show();
	        break;
	    case LocationProvider.TEMPORARILY_UNAVAILABLE:
	        Log.v(TAG, "Status Changed: Temporarily Unavailable");
	        Toast.makeText(mContext, "Status Changed: Temporarily Unavailable",
	                Toast.LENGTH_SHORT).show();
	        break;
	    case LocationProvider.AVAILABLE:
	        Log.v(TAG, "Status Changed: Available");
	        Toast.makeText(mContext, "Status Changed: Available",
	                Toast.LENGTH_SHORT).show();
	        break;
	    }
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}