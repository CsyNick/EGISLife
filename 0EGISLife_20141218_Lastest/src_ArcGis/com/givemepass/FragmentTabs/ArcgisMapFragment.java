package com.givemepass.FragmentTabs;

import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.example.egislife.R;

import csy.arcgisTools.ArcGISTools;
import csy.arcgisTools.NGisMapTools;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArcgisMapFragment extends Fragment {
	public static MapView mMapView;
	public static TextView layerContent;
	ArcGISTools arcgisTools = null;
	public static NGisMapTools myNGisMapTools = null;
	double locy;
	double locx;
	final static double SEARCH_RADIUS = 8;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		return inflater.inflate(R.layout.arcgis_map, container, false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		findView();
		init();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	private void findView() {
		layerContent = (TextView) getActivity().findViewById(R.id.txt_layerContent);
		mMapView = (MapView) getActivity().findViewById(R.id.map);
	 mMapView.setEsriLogoVisible(true);
	}

	public void init() {// 定位
		myNGisMapTools = new NGisMapTools(mMapView);
		myNGisMapTools.showNGisLayer("電子地圖");
		fixlocation();

	}

	public void fixlocation() {
		mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onStatusChanged(Object source, STATUS status) {
				if (source == mMapView && status == STATUS.INITIALIZED) {
					LocationDisplayManager ls = mMapView
							.getLocationDisplayManager();
					ls.setAutoPanMode(LocationDisplayManager.AutoPanMode.COMPASS);
					ls.setLocationListener(new LocationListener() {

						boolean locationChanged = false;

						// Zooms to the current location when first GPS fix
						// arrives.
						@Override
						public void onLocationChanged(Location loc) {
							if (!locationChanged) {
								locationChanged = true;
								locy = loc.getLatitude();
								locx = loc.getLongitude();

								Log.i(locy + "," + locx, locy + "," + locx);
								Point wgspoint = new Point(locx, locy);
								Point mapPoint = (Point) GeometryEngine
										.project(wgspoint,
												SpatialReference.create(4326),
												mMapView.getSpatialReference());

								Unit mapUnit = mMapView.getSpatialReference()
										.getUnit();
								double zoomWidth = Unit.convertUnits(
										SEARCH_RADIUS,
										Unit.create(LinearUnit.Code.KILOMETER),
										mapUnit);
								Envelope zoomExtent = new Envelope(mapPoint,
										zoomWidth, zoomWidth);
								mMapView.setExtent(zoomExtent);

							}

						}

						@Override
						public void onProviderDisabled(String arg0) {

						}

						@Override
						public void onProviderEnabled(String arg0) {
						}

						@Override
						public void onStatusChanged(String arg0, int arg1,
								Bundle arg2) {

						}
					});
					ls.start();

				}

			}
		});
	}
}
