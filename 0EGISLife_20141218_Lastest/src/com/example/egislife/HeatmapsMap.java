package com.example.egislife;

/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.database.sqldb.TaipeiLandmark;
import com.example.fragment.Map_Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A demo of the Heatmaps library. Demonstrates how the HeatmapTileProvider can
 * be used to create a colored map overlay that visualises many points of
 * weighted importance/intensity, with different colors representing areas of
 * high and low concentration/combined intensity of points.
 */
public class HeatmapsMap {

	/**
	 * Alternative radius for convolution
	 */
	private static final int ALT_HEATMAP_RADIUS = 10;

	/**
	 * Alternative opacity of heatmap overlay
	 */
	private static final double ALT_HEATMAP_OPACITY = 0.4;

	/**
	 * Alternative heatmap gradient (blue -> red) Copied from Javascript version
	 */
	private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
			Color.argb(0, 0, 255, 255),// transparent
			Color.argb(255 / 3 * 2, 0, 255, 255), Color.rgb(0, 191, 255),
			Color.rgb(0, 0, 127), Color.rgb(255, 0, 0) };

	public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = { 0.0f,
			0.10f, 0.20f, 0.60f, 1.0f };

	public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(
			ALT_HEATMAP_GRADIENT_COLORS, ALT_HEATMAP_GRADIENT_START_POINTS);

	private HeatmapTileProvider mProvider;
	private TileOverlay mOverlay;

	private boolean mDefaultGradient = true;
	private boolean mDefaultRadius = true;
	private boolean mDefaultOpacity = true;

	/**
	 * Maps name of data set to data (list of LatLngs) Also maps to the URL of
	 * the data set for attribution
	 */
	private HashMap<String, DataSet> mLists = new HashMap<String, DataSet>();

	private List<TaipeiLandmark> listTaipeiData;
	public Context context;

	// 121.563881,25.037302
	public HeatmapsMap(Context _context) {
		// TODO Auto-generated constructor stub
		this.context = _context;
	}

	protected void startDemo() {
		Map_Fragment.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(25.037302, 121.563881), 14));

		try {

			mLists.put("MRTLocation", new DataSet(readItems(listTaipeiData)));
		} catch (JSONException e) {
			Toast.makeText(context, "Problem reading list of markers.",
					Toast.LENGTH_LONG).show();
		}

	}
	public void changeRadius(int heatmap_radius) {
	
         mProvider.setRadius(heatmap_radius);
		mOverlay.clearTileCache();

	}
	public void changeRadius(View view) {
		if (mDefaultRadius) {
			mProvider.setRadius(ALT_HEATMAP_RADIUS);
		} else {
			mProvider.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);
		}
		mOverlay.clearTileCache();
		mDefaultRadius = !mDefaultRadius;
	}

	public void changeGradient(View view) {
		if (mDefaultGradient) {
			mProvider.setGradient(ALT_HEATMAP_GRADIENT);
		} else {
			mProvider.setGradient(HeatmapTileProvider.DEFAULT_GRADIENT);
		}
		mOverlay.clearTileCache();
		mDefaultGradient = !mDefaultGradient;
	}

	public void changeOpacity(View view) {
		if (mDefaultOpacity) {
			mProvider.setOpacity(ALT_HEATMAP_OPACITY);
		} else {
			mProvider.setOpacity(HeatmapTileProvider.DEFAULT_OPACITY);
		}
		mOverlay.clearTileCache();
		mDefaultOpacity = !mDefaultOpacity;
	}



	// Dealing with spinner choices
	public void changeHeatMapContent(List<TaipeiLandmark> listTaipeiData) {
		try {

			mLists.put("MRTLocation", new DataSet(readItems(listTaipeiData)));
		} catch (JSONException e) {
			Toast.makeText(context, "Problem reading list of markers.",
					Toast.LENGTH_LONG).show();
		}
		// Check if need to instantiate (avoid setData etc twice)
	
			mProvider = new HeatmapTileProvider.Builder().data(
					mLists.get("MRTLocation").getData()).build();
			mOverlay = Map_Fragment.googleMap
					.addTileOverlay(new TileOverlayOptions()
							.tileProvider(mProvider));
			// Render links

		

	}

	// DataSets from MRTLocation
	private ArrayList<LatLng> readItems(List<TaipeiLandmark> TaipeiLandmarkList)
			throws JSONException {
		ArrayList<LatLng> list = new ArrayList<LatLng>();

		for (TaipeiLandmark taipei : TaipeiLandmarkList) {
			double[] lnglat = taipei.getlnglat();
			list.add(new LatLng(lnglat[0], lnglat[1]));
			// Log.i("LatLon", mrt.getLatitude()+""+mrt.getLongitude());
		}

		return list;
	}

	/**
	 * Helper class - stores data sets and sources.
	 */
	private class DataSet {
		private ArrayList<LatLng> mDataset;

		public DataSet(ArrayList<LatLng> dataSet) {
			this.mDataset = dataSet;

		}

		public ArrayList<LatLng> getData() {
			return mDataset;
		}

	}
	public TileOverlay getOverlay(){
		
		return mOverlay;
	}
}
