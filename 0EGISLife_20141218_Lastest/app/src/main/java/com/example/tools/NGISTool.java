package com.example.tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.egislife.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.object.Landmark;


public class NGISTool {

	private GoogleMap googleMap;
	private Context context;
	
	private HashMap<String, String> selectedTypeHashMap = null;
	public HashMap<String, Drawable> drawableHashMap = new HashMap<String, Drawable>();
	private HashMap<String, String> typeHashMap = new HashMap<String, String>();
	private List<Landmark> LandmarkList = null;
	
	private double lon = 0, lat = 0;
	private double fitFactor = 1.1;
	private final double DegToRad = 3.1415926/180.0;
	
	public int minLatitudeE6 = (int)(+81 * 1E6);
    public int maxLatitudeE6 = (int)(-81 * 1E6);
    
    public int minLongitudeE6  = (int)(+181 * 1E6);
    public int maxLongitudeE6  = (int)(-181 * 1E6);
	
	public NGISTool(){
		init(context);
	}
	
	public NGISTool(GoogleMap googleMap, Context context){
		this.googleMap = googleMap;
		this.context = context;
		init(context);
	}
	
	public void init(Context context)
	{
		createDrawables(context);
	}
	
	public boolean isSameFilter(String filter){
		if(selectedTypeHashMap == null) return false;
		String[] strArr = filter.split(",");
		String value = null;
		for(int i=0; i< strArr.length; i++){
			value = selectedTypeHashMap.get(strArr[0]);
			if(value == null) return false;
		}
		return true;
	}
	
	public void removeLandmarksLayer(){
		
	}
	
	//PlaceMarkA 基本資料服務
		public List<Landmark> PlaceMarkA(double TWD97_X, double TWD97_Y,
				int range, String filter) {
			removeLandmarksLayer();
	        //-----------------------------------------------------
			List<Landmark> theLandmarkList = null;
	        double[] latlon = NGISTool.TWD97TM2toWGS84(TWD97_X, TWD97_Y);
	        //todo
	        //計算新查詢點(latlon[1], latlon[1])與前一查詢點間之距離
	        //如果(latlon[1], latlon[1])點與(lon.lat)點之距離相差小於某一範圍時
	        //則採用已存在的LandmarkList(亦即不再到NGIS中查詢資料)
	        // Approximate Equirectangular -- works if (lat1,lon1) ~ (lat2,lon2) 
	        double R = 6371; // km
	        double x = (lon - latlon[1])*(DegToRad) * Math.cos((latlon[0] + lat)*(DegToRad) / 2); 
	        double y = (lat - latlon[0])*(DegToRad); 
	        double d = Math.sqrt(x * x + y * y) * R; //km
	        
	        Log.i("jpyuMsg:distanceBetweenTwoPoints=", ""+d);
	        //Log.i("jpyuMsg: isSameFilter(filter)=", ""+isSameFilter(filter));
	        if(LandmarkList != null && (d < 0.5 /*km*/)){
	        	//無須重新載入NGIS search results
	            Log.i("jpyuMsg: ", "無須重新載入NGIS search results");
	            theLandmarkList = parsePlaceMarkList(LandmarkList, filter);
	        	return theLandmarkList; //50 meter
	        }

	        Log.i("jpyuMsg: ", "重新載入NGIS search results");
			//更新LandmarkList所對應之(lon, lat)
	        lat = latlon[0];
	        lon = latlon[1];
	        //-----------------------------------------------------
			StringBuilder urlStringB = new StringBuilder();
			urlStringB.append("http://egis.moea.gov.tw/innoserve/webservice/XMLFunc_basic.aspx?cmd=basic");
			urlStringB.append("&x=");
			urlStringB.append(Double.toString(TWD97_X));
			urlStringB.append("&y=");
			urlStringB.append(Double.toString(TWD97_Y));
			urlStringB.append("&coor=84");
			urlStringB.append("&buffer=");
			urlStringB.append(Integer.toString(range));
			
			Element root = getRootOfGisXml(urlStringB.toString());
			theLandmarkList = parsePlaceMarkA(root, filter);

			return theLandmarkList;
		}
		
		public List<Landmark> parsePlaceMarkList(List<Landmark> LandmarkList, String filter)
		{
			Log.i("jpyuMsg: parsePlaceMarkList() --LandmarkList=", ""+ LandmarkList.size());
			Log.i("jpyuMsg: filter =", filter);
			List<Landmark> theLandmarkList = null;
			//===================================
			if(filter == null || filter.length()== 0){
				theLandmarkList = LandmarkList;
				selectedTypeHashMap = typeHashMap;
				Log.i("jpyuMsg: xtheLandmarkList=", ""+theLandmarkList.size());
			}else{
				String[] selectedTypeArr = filter.split(",");
				selectedTypeHashMap = new HashMap<String, String>();
				for(String str: selectedTypeArr){
					str = str.trim();
					selectedTypeHashMap.put(str, str);  //(key, value)
				}
				//===================================
				theLandmarkList = new ArrayList<Landmark>();
				for(Landmark landmark: LandmarkList){
					if(selectedTypeHashMap != null){
						String jpyuStr = selectedTypeHashMap.get(landmark.Type);
						//如果在selectedTypeHashMap找不到，則表示此element不是被選取來顯示的Type
						if(jpyuStr == null) continue;
						theLandmarkList.add(landmark);
					}
				}
				Log.i("jpyuMsg: ytheLandmarkList=", ""+theLandmarkList.size());
			}
		    return theLandmarkList;
		}
		
		public List<Landmark> parsePlaceMarkA(Element root, String filter)
		{
			
			typeHashMap.clear();	
			NodeList nodes = root.getElementsByTagName("DATA");//
			LandmarkList = null;
			List<Landmark> theLandmarkList = null;
			//===============================================================
			if (nodes.getLength() > 0) {
				LandmarkList=new ArrayList<Landmark>();
				for (int i = 0; i < nodes.getLength(); i++) {
					Element dataElement = (Element) nodes.item(i);
					// -------------------------------------------------------------------------
					String type =dataElement.getAttribute("TYPE").trim();
					typeHashMap.put(type, type);
					
					String name =dataElement.getAttribute("NAME");
					String x =dataElement.getAttribute("X");
					String y=dataElement.getAttribute("Y");
					//Log.i("travel_path_value_Msg: ", type+":"+name+x+y);
					// ----------------------------------------------------------------------------
					Landmark landmark = new Landmark();

					landmark.Name=name;
					landmark.Type=type;
					landmark.lon=Double.parseDouble(x);
					landmark.lat=Double.parseDouble(y);
					Drawable drawable = this.drawableHashMap.get(landmark.Type);
					if(drawable == null){
						landmark.drawable = this.drawableHashMap.get("other");
					}else{
						landmark.drawable = drawable;
					}
					//Log.i("travel_path_value_Msg2: lon=", landmark.lon+", lat="+landmark.lat);
					LandmarkList.add(landmark);
				}
				Log.i("jpyuMsg: parsePlaceMarkA() --LandmarkList=", ""+ LandmarkList.size());
				theLandmarkList = parsePlaceMarkList(LandmarkList, filter);
			}
			//===============================================================

			Log.i("jpyuMsg: parsePlaceMarkA() --theLandmarkList=", ""+ theLandmarkList.size());
		   return theLandmarkList;
		}
		
		public HashMap<String, String> getTypeList(){
			return typeHashMap;
		}

		public HashMap<String, String> copy_typeHashMap(){
			HashMap<String, String> typeHashMap_tmp = new HashMap<String, String>();
			for (HashMap.Entry<String, String> entry : typeHashMap.entrySet()) 
			{    
				typeHashMap_tmp.put(entry.getKey(), entry.getValue());
			}
			return typeHashMap_tmp;
		}
		
		public static Element getRootOfGisXml(String urlString) {
			Element root = null;
			// connect to map web service 
			//http://ngis.moea.gov.tw/ngisfxdata/webservice/XMLFunc_basic.aspx?cmd=basic&x=304324&y=2770863&coor=84&buffer=5000
			
			Log.d("connect to map web service:", "URL=" + urlString);

			// get the kml (XML) doc. And parse it to get the coordinates(direction
			// route).
			Document doc = null;
			HttpURLConnection urlConnection = null;
			URL url = null;
			try {
				url = new URL(urlString);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				urlConnection.connect();

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				doc = db.parse(urlConnection.getInputStream());
				root = doc.getDocumentElement();

			} catch (MalformedURLException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (ParserConfigurationException e) {

				e.printStackTrace();

			} catch (SAXException e) {

				e.printStackTrace();
			}
			return root;

		}
	
	public HashMap<String, Drawable> createDrawables(Context context){
		if(context != null){
			drawableHashMap.clear();
			Drawable drawable;
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon_school);
			drawableHashMap.put("學校", drawable);
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon_park);
			drawableHashMap.put("公園", drawable);
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon_goverment);
			drawableHashMap.put("政府機構", drawable);
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon_museum);
			drawableHashMap.put("博物館", drawable);
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon_landmark);
			drawableHashMap.put("古蹟", drawable);
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon_gym);
			drawableHashMap.put("運動場", drawable);
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon_star);
			drawableHashMap.put("地標", drawable);
	 		
			drawable = context.getResources().getDrawable(
	   				R.drawable.icon19);
			drawableHashMap.put("other", drawable);
		}
		
 		return drawableHashMap;
	}
	
	public void addPlacemarks(List<Landmark> theLandmarkList){
		for(int i=0; i<theLandmarkList.size(); i++){
			googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(theLandmarkList.get(i).lat,theLandmarkList.get(i).lon))
				.title(theLandmarkList.get(i).Name)
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		}
		
	}
	
	
	public static double[] TWD97TM2toWGS84(double x, double y) {
	    // Ref1 : http://www.uwgb.edu/dutchs/UsefulData/UTMFormulas.htm
	    // Ref2 : http://blog.ez2learn.com/2009/08/15/lat-lon-to-twd97/
	    // 修正 Ref2 中lng回傳值 toDegree 設錯地方的Bug
	   
	    double dx = 250000;
	    double dy = 0;
	    double lon0 = 121;
	    double k0 = 0.9999;
	    double a =  6378137.0;
	    double b = 6356752.314245;
	    double e = Math.sqrt((1-(b*b)/(a*a)));
	   
	    x -= dx;
	    y -= dy;
	 
	    // Calculate the Meridional Arc
	    double M = y/k0;
	 
	    // Calculate Footprint Latitude
	    double mu = M/(a*(1.0 - Math.pow(e, 2)/4.0 - 3*Math.pow(e, 4)/64.0 - 5*Math.pow(e, 6)/256.0));
	    double e1 = (1.0 - Math.pow((1.0 - Math.pow(e, 2)), 0.5)) / (1.0 + Math.pow((1.0 - Math.pow(e, 2)), 0.5));
	 
	    double J1 = (3*e1/2 - 27*Math.pow(e1, 3)/32.0);
	    double J2 = (21*Math.pow(e1, 2)/16 - 55*Math.pow(e1, 4)/32.0);
	    double J3 = (151*Math.pow(e1, 3)/96.0);
	    double J4 = (1097*Math.pow(e1, 4)/512.0);
	 
	    double fp = mu + J1*Math.sin(2*mu) + J2*Math.sin(4*mu) + J3*Math.sin(6*mu) + J4*Math.sin(8*mu);
	 
	    // Calculate Latitude and Longitude
	 
	    double e2 = Math.pow((e*a/b), 2);
	    double C1 = Math.pow(e2*Math.cos(fp), 2);
	    double T1 = Math.pow(Math.tan(fp), 2);
	    double R1 = a*(1-Math.pow(e, 2))/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), (3.0/2.0));
	    double N1 = a/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), 0.5);
	 
	    double D = x/(N1*k0);
	    //double drad = Math.PI/180.0;
	   
	    // lat
	    double Q1 = N1*Math.tan(fp)/R1;
	    double Q2 = (Math.pow(D, 2)/2.0);
	    double Q3 = (5 + 3*T1 + 10*C1 - 4*Math.pow(C1, 2) - 9*e2)*Math.pow(D, 4)/24.0;
	    double Q4 = (61 + 90*T1 + 298*C1 + 45*Math.pow(T1, 2) - 3*Math.pow(C1, 2) - 252*e2)*Math.pow(D, 6)/720.0;
	    double lat = Math.toDegrees(fp - Q1*(Q2 - Q3 + Q4));
	 
	    // long
	    double Q5 = D;
	    double Q6 = (1 + 2*T1 + C1)*Math.pow(D, 3)/6.0;
	    double Q7 = (5 - 2*C1 + 28*T1 - 3*Math.pow(C1, 2) + 8*Math.pow(e2,2) + 24*Math.pow(T1, 2))*Math.pow(D, 5)/120.0;
	   
	    double lon = lon0 + Math.toDegrees((Q5 - Q6 + Q7)/Math.cos(fp)); 
	   
	    return new double[] {lat-0.00185, lon+0.0082};
	   } 

	  public static double[] WGS84toTWD97TM2(double lat, double lon){
		    // Ref1 : http://www.uwgb.edu/dutchs/UsefulData/UTMFormulas.htm
		    // Ref2 : http://blog.ez2learn.com/2009/08/15/lat-lon-to-twd97/    
		   
		    // convert from degrees to radians
		    lat = Math.toRadians(lat);
		    lon = Math.toRadians(lon);
		   
		    double a =  6378137.0;
		    double b = 6356752.314245;
		    double long0 = Math.toRadians(121);
		    double k0 = 0.9999;
		    double dx = 250000;
		   
		    double e = Math.sqrt(1- Math.pow(b, 2)/Math.pow(a,2));
		   
		    double e2 = Math.pow(e,2)/(1- Math.pow(e,2));
		   
		    double n = (a-b) / (a+b);

		    double n2 = n*n;
		    double n3 = n*n*n;
		    double n4 = n*n*n*n;
		    double n5 = n*n*n*n*n;
		   
		    double nu = a / Math.sqrt(1- Math.pow(e,2) * Math.pow(Math.sin(lat),2));
		   
		    double p = lon - long0;

		    double A = a * (1 - n + (5/4.0)*(n2-n3) + (81/64.0)*(n4 - n5));
		    double B = (3*a*n/2.0)*(1 - n + (7/8.0)*(n2 -n3) + (55/64.0)*(n4 - n5));
		    double C = (15*a*n2/16.0)*(1 - n + (3/4.0)*(n2 - n3));
		    double D = (35*a*n3/48.0)*(1 - n + (11/16.0)*(n2 - n3));
		    double E = (315*a*n4/51.0)*(1-n);
		       
		    double S = A*lat - B*Math.sin(2*lat) + C*Math.sin(4*lat) - D*Math.sin(6*lat) + E*Math.sin(8*lat);
		   
		    double K1 = S*k0;
		    double K2 = k0*nu*Math.sin(2*lat)/4.0;
		    double K3 = (k0*nu*Math.sin(lat)*(Math.pow(Math.cos(lat),3)/24.0)) * (5 - Math.pow(Math.tan(lat), 2) + 9*e2*Math.pow(Math.cos(lat),2)) + 4*Math.pow(e2,2)*Math.pow(Math.cos(lat), 4);
		    double y = K1+K2*Math.pow(p,2) + K3*Math.pow(p, 4);
		  
		    double K4 = k0*nu*Math.cos(lat);
		    double K5 = (k0*nu*Math.pow(Math.cos(lat), 3)/6.0) * (1-Math.pow(Math.tan(lat),2) + e2*Math.pow(Math.cos(lat), 2));
		   
		    double x = K4*p + K5*Math.pow(p, 3) + dx;

		    return new double[] {x,y};
	  }
	  /*
	  public void resetSpan(){
		    // The latitude is clamped between -80 degrees and +80 degrees inclusive
		    // thus we ensure that we go beyond that number
			minLatitudeE6 = (int)(+81 * 1E6);
			maxLatitudeE6 = (int)(-81 * 1E6);
		    
		    // Minimum & maximum longitude so we can span it
		    // The longitude is clamped between -180 degrees and +180 degrees inclusive
		    // thus we ensure that we go beyond that number
			minLongitudeE6  = (int)(+181 * 1E6);;
			maxLongitudeE6  = (int)(-181 * 1E6);;
		}
		
		public void findSpan(LatLng p){ 

		     // Minimum & maximum latitude so we can span it
		 

		            int latitude = (int)(p.latitude * 1E6);
		            int longitude = (int)(p.longitude * 1E6);
		            
		            // Sometimes the longitude or latitude gathering
		            // did not work so skipping the point
		            // doubt anybody would be at 0 0
		            if (latitude != 0 && longitude !=0)  {
		                    
		                    // Sets the minimum and maximum latitude so we can span and zoom
		                    minLatitudeE6 = (minLatitudeE6 > latitude) ? latitude : minLatitudeE6;
		                    maxLatitudeE6 = (maxLatitudeE6 < latitude) ? latitude : maxLatitudeE6;                
		                    
		                    // Sets the minimum and maximum latitude so we can span and zoom
		                    minLongitudeE6 = (minLongitudeE6 > longitude) ? longitude : minLongitudeE6;
		                    maxLongitudeE6 = (maxLongitudeE6 < longitude) ? longitude : maxLongitudeE6;
		                    
		            }
		}
		
		public void setZoomToSpan(GoogleMap mapView, double fitFactor){

	        MapController mMapController = mapView.getController();

	        int lon_span = maxLongitudeE6 - minLongitudeE6;
	        int lat_span = maxLatitudeE6 - minLatitudeE6;
	        if(lon_span <= 0) lon_span =800;
	        if(lat_span <= 0) lat_span =800;
	        mMapController.zoomToSpan(
	                        (int)(lat_span*fitFactor), 
	                        (int)(lon_span*fitFactor));
	        
		}
		

		public void animateToSpanCenter(GoogleMap mapView) {
	        MapController mMapController = mapView.getController();
	        GeoPoint geoPoint = new GeoPoint((maxLatitudeE6 + minLatitudeE6)/2, (maxLongitudeE6 + minLongitudeE6)/2);
	        mMapController.animateTo(geoPoint);
		}
		*/
}
