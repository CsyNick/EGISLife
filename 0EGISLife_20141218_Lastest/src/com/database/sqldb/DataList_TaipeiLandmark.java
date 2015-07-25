package com.database.sqldb;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;

public class DataList_TaipeiLandmark extends DBHelper_TaipeiLandmark {
	List<TaipeiLandmark> landmarks = null;
	protected Cursor cursor = null;
	TaipeiLandmark taipeiLandmark = null;
	GUIGenerator guiGenerator;
	public DataList_TaipeiLandmark(Context context) {
		super(context);
		guiGenerator = new GUIGenerator(context);
		// TODO Auto-generated constructor stub
	}

	public List<TaipeiLandmark> getTaipeimarkList(int selection) {
		cursor = getClassMainData(selection);
		landmarks = new ArrayList<TaipeiLandmark>();
		if (cursor != null) {
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				taipeiLandmark = new TaipeiLandmark();
				taipeiLandmark.setId(cursor.getString(0));
				taipeiLandmark.setMarkName(cursor.getString(1));
				taipeiLandmark.setDistrict(cursor.getString(2));
				taipeiLandmark.setAddress(cursor.getString(3));
				taipeiLandmark.setX(cursor.getString(4));
				taipeiLandmark.setY(cursor.getString(5));
				taipeiLandmark.setUrl_webpage(cursor.getString(6));
				taipeiLandmark.setVillage(cursor.getString(7));
				taipeiLandmark.setClass_main(cursor.getString(8));
				taipeiLandmark.setClass_middle(cursor.getString(9));
				taipeiLandmark.setClass_small(cursor.getString(10));
				taipeiLandmark.setDrawable(guiGenerator.getdrawableHashMap().get(cursor.getString(8)));
				// LocationTool.GetDistance(lat1, lng1, lat2, lng2)

				landmarks.add(taipeiLandmark);
			}

			cursor.close();
		}
		return landmarks;
	}

	public List<TaipeiLandmark> getTaipeimarkListByDistrict(String district) {
		cursor = getAllLandmarker(district);
		landmarks = new ArrayList<TaipeiLandmark>();
		if (cursor != null) {
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				taipeiLandmark = new TaipeiLandmark();
				taipeiLandmark.setId(cursor.getString(0));
				taipeiLandmark.setMarkName(cursor.getString(1));
				taipeiLandmark.setDistrict(cursor.getString(2));
				taipeiLandmark.setAddress(cursor.getString(3));
				taipeiLandmark.setX(cursor.getString(4));
				taipeiLandmark.setY(cursor.getString(5));
				taipeiLandmark.setUrl_webpage(cursor.getString(6));
				taipeiLandmark.setVillage(cursor.getString(7));
				taipeiLandmark.setClass_main(cursor.getString(8));
				taipeiLandmark.setClass_middle(cursor.getString(9));
				taipeiLandmark.setClass_small(cursor.getString(10));
				taipeiLandmark.setDrawable(guiGenerator.getdrawableHashMap().get(cursor.getString(8)));
				// LocationTool.GetDistance(lat1, lng1, lat2, lng2)

				landmarks.add(taipeiLandmark);
			}

			cursor.close();
		}
		return landmarks;
	}

	/**
	 * 
	 * @param selectoin
	 *            0.休閒購物 1.美食佳餚 2.政府機關 3.藝術文化 4.文教機構 5.交通建設 6.公用事業 7.工商服務 8.醫療保健
	 * 
	 * @return List<TaipeiLandmark>
	 */
	public List<TaipeiLandmark> getTaipeimarkListByDistrictAndClassMain(
			String district, int selection) {
		cursor = getClassMainDatabyDistrict(district, selection);
		landmarks = new ArrayList<TaipeiLandmark>();
		if (cursor != null) {
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				taipeiLandmark = new TaipeiLandmark();
				taipeiLandmark.setId(cursor.getString(0));
				taipeiLandmark.setMarkName(cursor.getString(1));
				taipeiLandmark.setDistrict(cursor.getString(2));
				taipeiLandmark.setAddress(cursor.getString(3));
				taipeiLandmark.setX(cursor.getString(4));
				taipeiLandmark.setY(cursor.getString(5));
				taipeiLandmark.setUrl_webpage(cursor.getString(6));
				taipeiLandmark.setVillage(cursor.getString(7));
				taipeiLandmark.setClass_main(cursor.getString(8));
				taipeiLandmark.setClass_middle(cursor.getString(9));
				taipeiLandmark.setClass_small(cursor.getString(10));
				taipeiLandmark.setDrawable(guiGenerator.getdrawableHashMap().get(cursor.getString(8)));
				// LocationTool.GetDistance(lat1, lng1, lat2, lng2)

				landmarks.add(taipeiLandmark);
			}

			cursor.close();
		}
		return landmarks;
	}

	/**
	 * 
	 * @param selectoin
	 *            0.休閒購物 1.美食佳餚 2.政府機關 3.藝術文化 4.文教機構 5.交通建設 6.公用事業 7.工商服務 8.醫療保健
	 * 
	 * @return List<TaipeiLandmark>
	 */
	public List<TaipeiLandmark> getTaipeimarkListByDistrictAndClassMainWithRange(String district,int selection,double centerLng,double centerLat,double range) {
		cursor = getClassMainDatabyDistrict(district, selection);
		landmarks = new ArrayList<TaipeiLandmark>();
		if (cursor != null) {
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				taipeiLandmark = new TaipeiLandmark();
				taipeiLandmark.setId(cursor.getString(0));
				taipeiLandmark.setMarkName(cursor.getString(1));
				taipeiLandmark.setDistrict(cursor.getString(2));
				taipeiLandmark.setAddress(cursor.getString(3));
				taipeiLandmark.setX(cursor.getString(4));
				taipeiLandmark.setY(cursor.getString(5));
				taipeiLandmark.setUrl_webpage(cursor.getString(6));
				taipeiLandmark.setVillage(cursor.getString(7));
				taipeiLandmark.setClass_main(cursor.getString(8));
				taipeiLandmark.setClass_middle(cursor.getString(9));
				taipeiLandmark.setClass_small(cursor.getString(10));
				taipeiLandmark.setDrawable(guiGenerator.getdrawableHashMap().get(cursor.getString(8)));
				double[] latlng = taipeiLandmark.getlnglat();
				double distance=GetDistance(centerLat,centerLng,latlng[0],latlng[1]);
				if(distance<range)
				landmarks.add(taipeiLandmark);
			}
			
			cursor.close();
		}
		return landmarks;
	}

	public int getSize() {
		return landmarks.size();

	}

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		float[] results = new float[1];
		Location.distanceBetween(lat1, lng1, lat2, lng2, results);
		return results[0] / 1000;
	}
}
