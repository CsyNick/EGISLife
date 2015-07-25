package com.database.sqldb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.egislife.R;
import com.google.android.gms.maps.GoogleMap;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class GUIGenerator {

	public HashMap<String, Drawable> drawableHashMap = new HashMap<String, Drawable>();
	public Drawable[] allLandmarkDrawable = null;
	public String[] allLandmarkTitle = null; // 所有可能的Landmark類型

	// existingLandmarkList_WebServiceB:某次request中存在的所有Landmarks
	static List<TaipeiLandmark> WebServiceB_existingLandmarkList = null;
	// existingLandmarkTypeHashMap:某次request中存在的所有Landmarks的類型
	private HashMap<String, String> existingLandmarkTypeHashMap = new HashMap<String, String>();

	// selectedLandmarkTypeHashMap:要加入Map中之Landmark類型
	HashMap<String, String> selectedLandmarkTypeHashMap = null;
	private HashMap<String, String> filterHashMap = new HashMap<String, String>();

	Context context = null;
	GoogleMap gmap;

	public GUIGenerator(GoogleMap mapView, Context context) {
		this.gmap = mapView;
		this.context = context;
		createDrawables(context);
	}
    public GUIGenerator(Context context) {
		// TODO Auto-generated constructor stub
    	createDrawables(context);
	}
	public HashMap<String,Drawable> getdrawableHashMap(){
    	return drawableHashMap;
    }
	public boolean isSameFilter(String filter) {
		if (selectedLandmarkTypeHashMap == null)
			return false;
		String[] strArr = filter.split(",");
		String value = null;
		for (int i = 0; i < strArr.length; i++) {
			value = selectedLandmarkTypeHashMap.get(strArr[0]);
			if (value == null)
				return false;
		}
		return true;
	}

	public HashMap<String, String> getExistingLandmarkTypes() {
		return existingLandmarkTypeHashMap;
	}

	public HashMap<String, Drawable> createDrawables(Context context) {
		if (context != null) {
			drawableHashMap.clear();
			Drawable drawable;

			drawable = context.getResources().getDrawable(R.drawable.shopping);
			drawableHashMap.put("休閒購物", drawable);

			drawable = context.getResources().getDrawable(R.drawable.food);
			drawableHashMap.put("美食佳餚", drawable);

			drawable = context.getResources().getDrawable(
					R.drawable.icon_goverment);
			drawableHashMap.put("政府機關", drawable);

			drawable = context.getResources().getDrawable(
					R.drawable.icon_museum);
			drawableHashMap.put("藝術文化", drawable);

			drawable = context.getResources().getDrawable(
					R.drawable.icon_school);
			drawableHashMap.put("文教機構", drawable);

			drawable = context.getResources().getDrawable(
					R.drawable.transportation);
			drawableHashMap.put("交通建設", drawable);

			drawable = context.getResources().getDrawable(
					R.drawable.publiccareer);
			drawableHashMap.put("公用事業", drawable);

			drawable = context.getResources().getDrawable(
					R.drawable.gcidservice);
			drawableHashMap.put("工商服務", drawable);

			drawable = context.getResources().getDrawable(R.drawable.hosptial);
			drawableHashMap.put("醫療保健", drawable);

			drawable = context.getResources().getDrawable(R.drawable.icon19);
			drawableHashMap.put("other", drawable);
			// =======================================

			allLandmarkTitle = context.getResources().getStringArray(
					R.array.lifeScore);

			allLandmarkDrawable = new Drawable[allLandmarkTitle.length];
			for (int i = 0; i < allLandmarkTitle.length; i++) {
				drawable = drawableHashMap.get(allLandmarkTitle[i]);
				if (drawable != null) {
					allLandmarkDrawable[i] = drawable;
				} else {
					allLandmarkDrawable[i] = drawableHashMap.get("other");
				}
			}
		}

		return drawableHashMap;
	}
//	public List<TaipeiLandmark> parseWebServiceB(List<TaipeiLandmark> markList, String filter)
//	{
//	
//		
//		existingLandmarkTypeHashMap.clear();	
//		WebServiceB_existingLandmarkList = null;
//		List<TaipeiLandmark> theSelectedLandmarkList = null;
//		//===============================================================
//		if (markList.size() > 0) {
//			WebServiceB_existingLandmarkList=new ArrayList<TaipeiLandmark>();
//			for (int i = 0; i < nodes.getLength(); i++) {
//				Element dataElement = (Element) nodes.item(i);
//				// -------------------------------------------------------------------------
//				String type =dataElement.getAttribute("TYPE").trim();
//				existingLandmarkTypeHashMap.put(type, type);
//				
//				String name =dataElement.getAttribute("NAME");
//				String x =dataElement.getAttribute("X");
//				String y=dataElement.getAttribute("Y");
//				//Log.i("travel_path_value_Msg: ", type+":"+name+x+y);
//				// ----------------------------------------------------------------------------
//				Landmark landmark = new Landmark();
//
//				landmark.Name=name;
//				landmark.Type=type;
//				landmark.lon=Double.parseDouble(x);
//				landmark.lat=Double.parseDouble(y);
//				Drawable drawable = this.drawableHashMap.get(landmark.Type);
//				if(drawable == null){
//					landmark.drawable = this.drawableHashMap.get("other");
//				}else{
//					landmark.drawable = drawable;
//				}
//				//Log.i("travel_path_value_Msg2: lon=", landmark.lon+", lat="+landmark.lat);
//				WebServiceB_existingLandmarkList.add(landmark);
//			}
//			Log.i("jpyuMsg: parseWebServiceB() --LandmarkList_WebServiceB=", ""+ WebServiceB_existingLandmarkList.size());
//			theSelectedLandmarkList = parsePlaceMarkList(WebServiceB_existingLandmarkList, filter);
//		}
//		//===============================================================

		//Log.i("jpyuMsg: parseWebServiceB() --theLandmarkList=", ""+ theLandmarkList.size());
//	   return theSelectedLandmarkList;
//	}

	public List<TaipeiLandmark> parsePlaceMarkList(List<TaipeiLandmark> allLandmarkList, String filter_selectedTypes)
	{
		Log.i("jpyuMsg: parsePlaceMarkList() --LandmarkList=", ""+ allLandmarkList.size());
		Log.i("jpyuMsg: filter =", filter_selectedTypes);
		List<TaipeiLandmark> theSelectedLandmarkList = null;
		//===================================
		if(filter_selectedTypes == null || filter_selectedTypes.length()== 0){
			//theSelectedLandmarkList = allLandmarkList;
			//selectedLandmarkTypeHashMap = existingLandmarkTypeHashMap;
			theSelectedLandmarkList = null;
			selectedLandmarkTypeHashMap = null;
		}else{
			String[] selectedTypeArr = filter_selectedTypes.split(",");
			selectedLandmarkTypeHashMap = new HashMap<String, String>();
			for(String str: selectedTypeArr){
				str = str.trim();
				selectedLandmarkTypeHashMap.put(str, str);  //(key, value)
			}
			//===================================
			theSelectedLandmarkList = new ArrayList<TaipeiLandmark>();
			for(TaipeiLandmark landmark: allLandmarkList){
				if(selectedLandmarkTypeHashMap != null){
					String jpyuStr = selectedLandmarkTypeHashMap.get(landmark.getClass_main());
					//如果在selectedTypeHashMap找不到，則表示此element不是被選取來顯示的Type
					if(jpyuStr == null) continue;
					theSelectedLandmarkList.add(landmark);
				}
			}
		}
	    return theSelectedLandmarkList;
	}
}