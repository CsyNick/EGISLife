package com.chsy.kml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Tour {
	Context context;
	public static final String SD_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public static final String FILE_PATH = "/GoogleEarthDB/01/";
	public static final String INPUT_FILENAME = "doc.kml";
	public ArrayList<LatLng> points_with_bearing;

	Tour() {
	}

	public Tour(Context _context) {
		this.context = _context;
	}

	String generateKml(String tourFilename) {

		ArrayList<LatLng> points = null;// BZ.getPointsFromBezierCurve();
		generateKml(points);
		return "";

	}

	public String generateKml(ArrayList<LatLng> points) {
		String filename = Tour.SD_PATH + Tour.FILE_PATH + Tour.INPUT_FILENAME;
		String s = "";
		s += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		s += "\r\n";
		s += "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">";
		s += "\r\n";
		s += "<Document>";
		s += "\r\n";
		s += "<name>A tour and some features</name>";
		s += "<open>1</open>";

		s += RoutePathKml(points);
		s += TourKml_Lookat(points);
		s += PlaceMarkKML(points);

		s += "</Document>";
		s += "\r\n";
		s += "</kml>";

		writeKmlFileToSdcard(filename, s);

		return filename;
	}

	public String createKml(ArrayList<LatLng> points) {
		String filename = Tour.SD_PATH + Tour.FILE_PATH + Tour.INPUT_FILENAME;
		String s = "";
		s += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		s += "\r\n";
		s += "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">";
		s += "\r\n";
		s += "<Document>";
		s += "\r\n";
		s += "<name>A tour and some features</name>";
		s += "<open>1</open>";

		s += RoutePathKml(points);
		s += TourKml_Lookat(points);
		s += PlaceMarkKML(points);

		s += "</Document>";
		s += "\r\n";
		s += "</kml>";

		writeKmlFileToSdcard(filename, s);

		return filename;
	}
	private String PlaceMarkKML(ArrayList<LatLng> points) {
		String s = "";
		s += "<Folder>";
		s += "<name>Points and polygons</name>";

		for (LatLng latLng : points) {

			s += "<Placemark>";
			s += "<name>MarkName</name>";

			s += "<Point>";
			s += "<coordinates>";
			s += latLng.longitude + "," + latLng.latitude;
			s += "</coordinates>";
			s += "</Point>";
			s += "</Placemark>";
		}

		s += "</Folder>";
		return s;
	}

	private String TourKml_Camera(ArrayList<LatLng> points) {
		String s = "";
		s += "<gx:Tour>";
		s += "<name>Play me!</name>";
		s += "<gx:Playlist>";

		s += "<!-- bounce is the default flyToMode -->";
		for (int i = 0; i < points.size(); i += 2) {
			s += "<gx:FlyTo>";
			s += "<gx:duration>0.5</gx:duration>";
			s += "<Camera>";
			s += "<longitude>" + points.get(i).longitude + "</longitude>";
			s += "<latitude>" + points.get(i).latitude + "</latitude>";
			s += "<altitude>600</altitude>";
			s += "<heading>-6.333</heading>";
			s += "<tilt>33.5</tilt>";
			s += "</Camera>";
			s += "</gx:FlyTo>";
		}
		s += "</gx:Playlist>";
		s += "</gx:Tour>";

		return s;
	}

	private String TourKml_Lookat(ArrayList<LatLng> points) {

		String s = "";
		s += "<gx:Tour>";
		s += "<name>Play me!</name>";
		s += "<gx:Playlist>";

		s += "<!-- bounce is the default flyToMode -->";
		for (int i = 0; i < points.size(); i ++) {
			if (i < points.size() - 1) {
				Log.i("Bearing", bearing(points.get(i), points.get(i + 1)) + "");
			}

			s += "<gx:FlyTo>";
			s += "<gx:duration>5.0</gx:duration>";
			s += "<gx:flyToMode>smooth</gx:flyToMode>";

			s += "<LookAt>";
			s += "<longitude>" + points.get(i).longitude + "</longitude>";
			s += "<latitude>" + points.get(i).latitude + "</latitude>";
			s += "<altitude>0</altitude>";
			if (i < points.size() - 1) {
				s += "<heading>" + bearing(points.get(i), points.get(i + 1))
						+ "</heading>";
			}
			s += "<tilt>60.5</tilt>";
			s += "<range>200</range>";
			s += "<altitudeMode>relativeToGround</altitudeMode>";
			s += "</LookAt>";
			s += "</gx:FlyTo>";
		}

		s += "</gx:Playlist>";
		s += "</gx:Tour>";

		return s;
	}

	private String RoutePathKml(ArrayList<LatLng> points) {
		String s = "";
		s += "<Placemark>";
		s += "\r\n";
		s += "<name>Outward flight</name>";
		s += "<Style id=\"street\">";
		s += "\r\n";
		s += "<LineStyle>";
		s += "\r\n";
		s += "<color>ffFFFF00</color>";
		s += "\r\n";
		s += " <gx:physicalWidth>12</gx:physicalWidth>";
		s += "\r\n";
		s += " </LineStyle>";
		s += "\r\n";
		s += " </Style>";
		s += "\r\n";
		s += "<LineString>";
		s += "\r\n";
		s += "<styleUrl>#street</styleUrl>";
		s += "\r\n";
		s += "<altitudeMode>clampToGround</altitudeMode>";
		s += "\r\n";
		s += "<coordinates>";
		s += "\r\n";
		for (LatLng latlng : points) {
			s += latlng.longitude + "," + latlng.latitude;
			s += "\r\n";
		}
		s += "</coordinates>";

		s += "</LineString>";
		s += "\r\n";
		s += "</Placemark>";
		s += "\r\n";
		return s;
	}

	private boolean writeKmlFileToSdcard(String filenameWithPath, String str) {

		int ipos = filenameWithPath.lastIndexOf("/");
		String dirPath = filenameWithPath.substring(0, ipos);
		// write on SD card file data in the text box
		try {
			// create a File object for the parent directory
			File newDir = new File(dirPath);
			// have the object build the directory structure, if needed.
			newDir.mkdirs();

			File myFile = new File(filenameWithPath);
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(str);
			myOutWriter.close();
			fOut.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private double bearing(double lat1, double lon1, double lat2, double lon2) {
		double longitude1 = lon1;
		double longitude2 = lon2;
		double latitude1 = Math.toRadians(lat1);
		double latitude2 = Math.toRadians(lat2);
		double longDiff = Math.toRadians(longitude2 - longitude1);
		double y = Math.sin(longDiff) * Math.cos(latitude2);
		double x = Math.cos(latitude1) * Math.sin(latitude2)
				- Math.sin(latitude1) * Math.cos(latitude2)
				* Math.cos(longDiff);

		return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
	}

	private double bearing(LatLng point1, LatLng point2) {

		double longitude1 = point1.longitude;
		double longitude2 = point2.longitude;
		double latitude1 = Math.toRadians(point1.latitude);
		double latitude2 = Math.toRadians(point2.latitude);
		double longDiff = Math.toRadians(longitude2 - longitude1);
		double y = Math.sin(longDiff) * Math.cos(latitude2);
		double x = Math.cos(latitude1) * Math.sin(latitude2)
				- Math.sin(latitude1) * Math.cos(latitude2)
				* Math.cos(longDiff);

		return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
	}

	public void openKmlToGE(String filename) {
		File file = new File(filename);
		Uri uri = Uri.fromFile(file);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
	}

	public void openKmlToGEbySDCard(String path) {
		File file = new File(path);
		Uri uri = Uri.fromFile(file);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		context.startActivity(it);
	}
//	public void openKmlToGE(InputStream kmlFile) {
//		// TODO Auto-generated method stub
//		
//		File file = new File(kmlFile);
//		Uri uri = Uri.fromFile(file);
//		Intent it = new Intent(Intent.ACTION_VIEW, uri);
//		context.startActivity(it);
//		
//	}
	
}
