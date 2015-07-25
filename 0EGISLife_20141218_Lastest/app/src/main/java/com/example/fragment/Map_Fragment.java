package com.example.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import me.wtao.widget.SlidingDrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.database.sqldb.DataList_TaipeiLandmark;
import com.database.sqldb.TaipeiLandmark;
import com.example.activity.Activity_Main;
import com.example.adapter.Adapter_Pager;
import com.example.adapter.GridAdapter;
import com.example.adapter.MyListAdapter;
import com.example.egislife.HeatmapsMap;
import com.example.egislife.R;
import com.example.foundi.TransactionBuilding;
import com.example.foundi.XMLtoSQLiteHandler;
import com.example.fragment.TouchableWrapper.UpdateMapAfterUserInterection;
import com.example.object.MyData;
import com.example.tools.NineXNineGridTool;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

public class Map_Fragment extends Fragment implements OnMarkerClickListener,
		UpdateMapAfterUserInterection, OnItemClickListener {
	public static GridAdapter ga;
	public SupportMapFragment fragment;
	private List<TaipeiLandmark> listTaipeiData = null;
	private static TaipeiLandmark taipeiLandmark;
	public static GoogleMap googleMap;
	public static TextView textView;
	public TextView txt_address;
	static Marker marker;
	public static List<Marker> markerList = null;
	public final static int dataLimit = 500;
	public HeatmapsMap heatmap;
	public static Gallery ViewTypeGallery;
	String[] class_mainArr = { "休閒購物", "美食佳餚", "政府機關", "藝術文化", "文教機構", "交通建設",
			"公用事業", "工商服務", "醫療保健" };
	ArrayList<MyData> myDataList = null;
	ListView mListView;
	public static SlidingDrawer t2b;
	public NineXNineGridTool nineGridTool = null;
	public static Activity activity;
	public boolean isBegin = true;
	ImageView imageView;

	// Foundi data
	List<TransactionBuilding> transObjectList = null;
	XmlPullParserFactory pullParserFactory;
	XMLtoSQLiteHandler db = null;
	View FoundiMarkerView;
	TextView numTxt;
	ImageView imgView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Map_Fragment", "onCreate");

		activity = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("Map_Fragment", "onCreateView");
		View rootView = inflater.inflate(R.layout.map_v2, container, false);

		TouchableWrapper mTouchView = new TouchableWrapper(getActivity());
		mTouchView.addView(rootView);
		mTouchView.setUpdateMapAfterUserInterection(this);
		// return rootView;
		return mTouchView;
	}

	public void showHeatMap() {
		googleMap.clear();

		// ViewTypeGallery.setVisibility(View.INVISIBLE);
		heatmap.changeHeatMapContent(listTaipeiData);
		heatmap.changeRadius(20);
		textView.setText(listTaipeiData.get(0).getClass_main());
	}

	public void showMarker() {
		t2b.setVisibility(View.VISIBLE);
		ViewTypeGallery.setVisibility(View.VISIBLE);

		changeDataMark_Mode(listTaipeiData);
	}

	public void changeDataMark_Mode(List<TaipeiLandmark> listTaipeiData) {
		double[] lnglat;
		MyData myData;
		googleMap.clear();
		markerList.clear();
		myDataList = new ArrayList<MyData>();
		textView.setText(listTaipeiData.get(0).getClass_main());
		Log.i("Map_Fragment", listTaipeiData.size() + "");
		if (listTaipeiData != null && listTaipeiData.size() < dataLimit) {

			for (int i = 0; i < listTaipeiData.size(); i++) {
				taipeiLandmark = listTaipeiData.get(i);
				myData = new MyData(taipeiLandmark.getMarkName(),
						taipeiLandmark.getId(), taipeiLandmark.getAddress(),
						taipeiLandmark.getDrawable(),
						taipeiLandmark.getlnglat());
				lnglat = taipeiLandmark.getlnglat();
				marker = googleMap.addMarker(new MarkerOptions()
						.position(new LatLng(lnglat[0], lnglat[1]))
						.title(taipeiLandmark.getMarkName())
						.icon(BitmapDescriptorFactory
								.fromBitmap(((BitmapDrawable) taipeiLandmark
										.getDrawable()).getBitmap()))
						.snippet(taipeiLandmark.getClass_main()));
				myDataList.add(myData);
				markerList.add(marker);

			}
		}
		mListView.setAdapter(new MyListAdapter(getActivity(), R.layout.row,
				myDataList));
		mListView.setOnItemClickListener(this);
	}

	public void loadingTaipeiData() {

		listTaipeiData = Activity_Main.listTaipeiData;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("Map_Fragment", "onDestroy");
		Map_Fragment.googleMap = null;
		super.onDestroy();
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d("Map_Fragment", "onActivityCreated");
		txt_address = (TextView) getActivity().findViewById(R.id.txt_address);
		imageView = (ImageView) activity.findViewById(R.id.imgV_focus);
		textView = (TextView) getActivity().findViewById(R.id.txt_classmain);
		initIconGenerator();
		ViewTypeGallery = (Gallery) getActivity().findViewById(
				R.id.travelTypeGallery);
		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map, fragment).commit();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("Map_Fragment", "onResume");
		if (googleMap == null) {
			ga = Activity_Main.ga;
			googleMap = fragment.getMap();
			googleMap.setMyLocationEnabled(true);
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.setBuildingsEnabled(true);
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			googleMap.setOnMarkerClickListener(this);

			nineGridTool = new NineXNineGridTool(googleMap, getActivity());
			nineGridTool.showNineXNineGridLayer(Activity_Main.taipei.lat,
					Activity_Main.taipei.lng);

			t2b = (SlidingDrawer) getActivity().findViewById(
					R.id.drawer_left_to_right);
			mListView = (ListView) getActivity()
					.findViewById(R.id.listview_map);
			loadingTaipeiData();
			heatmap = new HeatmapsMap(getActivity());

			markerList = new ArrayList<Marker>();
			myDataList = new ArrayList<MyData>();

			textView.setText("生活指數圖");

			setFragmentControlGallery();
			try {
				txt_address.setText(getLatLngToChineseAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param selectoin
	 *            0.休閒購物 1.美食佳餚 2.政府機關 3.藝術文化 4.文教機構 5.交通建設 6.公用事業 7.工商服務 8.醫療保健
	 * 
	 * 
	 */
	public void setTaipeiDistrictAndClassMainByRange(String district,
			int selection, double centerLng, double centerLat, double km) {

		DataList_TaipeiLandmark dataList_TaipeiLandmark = new DataList_TaipeiLandmark(
				getActivity());

		listTaipeiData = dataList_TaipeiLandmark
				.getTaipeimarkListByDistrictAndClassMainWithRange(district,
						selection, centerLng, centerLat, km);
		Log.i("Size", listTaipeiData.size() + "");

	}

	public static void setFragmentControl() {

		HashMap<String, Object> myHasMap;
		String lifeScoreString[] = activity.getResources().getStringArray(
				R.array.lifeScore);
		Object[] TypeImage = { R.drawable.shopping, R.drawable.food,
				R.drawable.icon_goverment, R.drawable.icon_museum,
				R.drawable.icon_school, R.drawable.transportation,
				R.drawable.publiccareer, R.drawable.gcidservice,
				R.drawable.hosptial, };

		ArrayList<HashMap<String, Object>> TypeImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < lifeScoreString.length; i++) {

			if (ga.isChecked(i)) {

				myHasMap = new HashMap<String, Object>();
				myHasMap.put("ItemImage", TypeImage[i]);
				myHasMap.put("ItemText", lifeScoreString[i]);
				TypeImageItem.add(myHasMap);
			}

		}

		SimpleAdapter ViewTypeAdapter = new SimpleAdapter(activity,
				TypeImageItem, R.layout.item_view_type, new String[] {
						"ItemImage", "ItemText" }, new int[] {
						R.id.ViewItemImage, R.id.ViewItemText });
		ViewTypeGallery.setAdapter(ViewTypeAdapter);
		// ViewTypeGallery.setGravity(Gravity.CENTER_HORIZONTAL); // 设置水平居中显示
		ViewTypeGallery.setUnselectedAlpha(0.5f); // 设置未选中图片的透明度
		ViewTypeGallery.setSpacing(15); // 设置图片之间的间距
	}

	public void setFragmentControlGallery() {

		HashMap<String, Object> myHasMap;
		String lifeScoreString[] = activity.getResources().getStringArray(
				R.array.lifeScore);
		Object[] TypeImage = { R.drawable.shopping, R.drawable.food,
				R.drawable.icon_goverment, R.drawable.icon_museum,
				R.drawable.icon_school, R.drawable.transportation,
				R.drawable.publiccareer, R.drawable.gcidservice,
				R.drawable.hosptial, };

		ArrayList<HashMap<String, Object>> TypeImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < lifeScoreString.length; i++) {

			if (ga.isChecked(i)) {

				myHasMap = new HashMap<String, Object>();
				myHasMap.put("ItemImage", TypeImage[i]);
				myHasMap.put("ItemText", lifeScoreString[i]);
				TypeImageItem.add(myHasMap);
			}

		}

		SimpleAdapter ViewTypeAdapter = new SimpleAdapter(getActivity(),
				TypeImageItem, R.layout.item_view_type, new String[] {
						"ItemImage", "ItemText" }, new int[] {
						R.id.ViewItemImage, R.id.ViewItemText });
		ViewTypeGallery.setAdapter(ViewTypeAdapter);
		// ViewTypeGallery.setGravity(Gravity.CENTER_HORIZONTAL); // 设置水平居中显示
		ViewTypeGallery.setUnselectedAlpha(0.5f); // 设置未选中图片的透明度
		ViewTypeGallery.setSpacing(15); // 设置图片之间的间距

		// ======================
		ViewTypeGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CameraPosition cameraPosition = googleMap.getCameraPosition();
				double lat = cameraPosition.target.latitude;
				double lon = cameraPosition.target.longitude;
				setTaipeiDistrictAndClassMainByRange(Activity_Main.district,
						position, lon, lat, Activity_Main.range);
				String addr;
				try {
					addr = getLatLngToChineseAddress();
					txt_address.setText(addr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (listTaipeiData.size() == 0)
					return;
				if (Activity_Main.isHeatMap) {
					changeDataMark_Mode(listTaipeiData);
					t2b.setVisibility(View.VISIBLE);
				} else {
					showHeatMap();
				}
			}
		});

	}

	@Override
	public void onUpdateMapAfterUserInterection() {
		// TODO Auto-generated method stub

		if (Activity_Main.isLifeScoreLayer == true) {
			CameraPosition cameraPosition = googleMap.getCameraPosition();
			double lat = cameraPosition.target.latitude;
			double lon = cameraPosition.target.longitude;
			Adapter_Pager.control_fragment.changeByControl_ALL(
					Fragment_Control.sfilter, lon, lat);
		} else if (Activity_Main.isFoundiAnalysis == true) {
			Adapter_Pager.map_Fragment.setFoundiAnalysis();

		}

		try {
			String addr = getLatLngToChineseAddress();
			txt_address.setText(addr);
			Adapter_Pager.control_fragment.txtAddress.setText("所在地:" + addr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d("Map_Fragment", position + "click");
		MyData data = myDataList.get(position);
		Marker marker = markerList.get(position);
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(data.getLatLng()).zoom(19).tilt(45).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
		marker.showInfoWindow();

	}

	public String getLatLngToChineseAddress() throws IOException {
		String addr = null;
		CameraPosition cameraPosition = googleMap.getCameraPosition();
		double lat = cameraPosition.target.latitude;
		double lon = cameraPosition.target.longitude;
		Geocoder gc = new Geocoder(getActivity(), Locale.TRADITIONAL_CHINESE);
		List<Address> lstAddress = gc.getFromLocation(lat, lon, 1);
		// String returnAddress = lstAddress.get(0).getAddressLine(0);

		// lstAddress.get(0).getCountryName(); // 台灣省
		// lstAddress.get(0).getAdminArea(); // 台北市
		String district = lstAddress.get(0).getLocality(); // 中正區
		String street = lstAddress.get(0).getThoroughfare(); // 信陽街(包含路巷弄)
		String number = lstAddress.get(0).getFeatureName(); // 會得到33(號)
		if (district != null) {
			addr = district + street + number + "號";
		} else {
			addr = street + number + "號";
		}

		return addr;

	}

	public void setFoundiAnalysis() {
		Map_Fragment.googleMap.clear();
		int price = 0;
		db = new XMLtoSQLiteHandler(getActivity());
		if (db.getAllContacts().size() <= 0) {
			InsertData();
		} else {
			CameraPosition cameraPosition = Map_Fragment.googleMap
					.getCameraPosition();
			double centerLat = cameraPosition.target.latitude;
			double centerLng = cameraPosition.target.longitude;
			transObjectList = db.getTransactionBuildingbyRange(
					Activity_Main.district, centerLng, centerLat,
					Activity_Main.range);

			for (TransactionBuilding transObject : transObjectList) {
				Log.i("Foundi", transObject._district + "," + transObject._lat
						+ "," + transObject._lon + ","
						+ transObject._mainMaterial + ","
						+ transObject._XMLBuildingType
						+ transObject._priceOfOneArea + " nonono");

				if (!transObject._priceOfOneArea.contentEquals("")) {
					if (transObject._XMLBuildingType.contentEquals("買賣")) {
					
						price = (int) (Integer
								.parseInt(transObject._priceOfOneArea) / 0.3025 * 0.0001);
					
							imgView.setImageResource(R.drawable.custom_marker_buysell);	
						
					} else if (transObject._XMLBuildingType
							.contentEquals("預售屋"))

					{
						imgView.setImageResource(R.drawable.custom_marker_predictsell);
						price = (int) (Integer
								.parseInt(transObject._priceOfOneArea) / 0.3025 * 0.0001);
					} else {
						imgView.setImageResource(R.drawable.custom_marker_lease);
						price = (int) (Math.round(Double
								.parseDouble(transObject._totalPrice) * 0.0001));
					}
					if (String.valueOf(price).length() == 1) {
						RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
					    llp.setMargins(23, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
					    numTxt.setLayoutParams(llp);
						numTxt.setTextSize(22);
						numTxt.setText(" " + String.valueOf(price));
					} else if(String.valueOf(price).length() == 2) {
						RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
					    llp.setMargins(20, 18, 0, 0); // llp.setMargins(left, top, right, bottom);
					    numTxt.setLayoutParams(llp);
						numTxt.setTextSize(22);
						numTxt.setText(String.valueOf(price));
					}else{
						RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
						    llp.setMargins(20, 25, 0, 0); // llp.setMargins(left, top, right, bottom);
						    numTxt.setLayoutParams(llp);
						numTxt.setTextSize(15);
						numTxt.setText(String.valueOf(price));
					}

					LatLng latlng = new LatLng(
							Double.parseDouble(transObject._lat),
							Double.parseDouble(transObject._lon));
					Marker marker = Map_Fragment.googleMap
							.addMarker(new MarkerOptions()
									.position(latlng)
									.title("[" + transObject._XMLBuildingType
											+ "]"
											+ transObject._transactionObject)
									.icon(BitmapDescriptorFactory
											.fromBitmap(createDrawableFromView(
													getActivity(),
													FoundiMarkerView)))
									.snippet(transObject._address));

				}
			}
		}

	}

	public void InsertData() {
		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts
		Log.d("Insert: ", "Inserting ..");

		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			InputStream in_a = getActivity().getAssets().open(
					"a_lvr_land_a.xml");

			InputStream in_b = getActivity().getAssets().open(
					"a_lvr_land_b.xml");
			InputStream in_c = getActivity().getAssets().open(
					"a_lvr_land_c.xml");
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in_a, null);
			parseXML(parser, 1);
			Log.d("Insert", transObjectList.size() + "筆");
			for (TransactionBuilding transObj : transObjectList) {
				String[] latlon = addressToLatLon(transObj._address);

				transObj.setLAT(latlon[0]);
				transObj.setLON(latlon[1]);
				Log.d("Insert", transObj._address + ":" + transObj._lat + ","
						+ transObj._lon);
				db.addContact(transObj);
			}
			parser.setInput(in_b, null);
			parseXML(parser, 2);
			Log.d("Insert", transObjectList.size() + "筆");
			for (TransactionBuilding transObj : transObjectList) {
				String[] latlon = addressToLatLon(transObj._address);
				transObj.setLAT(latlon[0]);
				transObj.setLON(latlon[1]);
				Log.d("Insert", transObj._address + ":" + transObj._lat + ","
						+ transObj._lon);
				db.addContact(transObj);
			}
			parser.setInput(in_c, null);
			parseXML(parser, 3);
			Log.d("Insert", transObjectList.size() + "筆");
			for (TransactionBuilding transObj : transObjectList) {
				String[] latlon = addressToLatLon(transObj._address);
				transObj.setLAT(latlon[0]);
				transObj.setLON(latlon[1]);
				Log.d("Insert", transObj._address + ":" + transObj._lat + ","
						+ transObj._lon);
				db.addContact(transObj);
			}
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseXML(XmlPullParser parser, int type)
			throws XmlPullParserException, IOException {

		String XMLBuildingType;
		int eventType = parser.getEventType();
		TransactionBuilding currentTransObject = null;
		switch (type) {
		case 1:
			XMLBuildingType = "買賣";

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					transObjectList = new ArrayList<TransactionBuilding>();
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();

					if (name.contentEquals(XMLBuildingType)) {
						currentTransObject = new TransactionBuilding();
						currentTransObject.setXMLBuildingType(XMLBuildingType);

					} else if (currentTransObject != null) {
						if (name.contentEquals("鄉鎮市區")) {
							// Log.d("Insert", parser.nextText());
							currentTransObject._district = parser.nextText();
						} else if (name.contentEquals("交易標的")) {
							currentTransObject._transactionObject = parser
									.nextText();
						} else if (name.contentEquals("土地區段位置或建物區門牌")) {
							currentTransObject._address = parser.nextText();
						} else if (name.contentEquals("土地移轉總面積平方公尺")) {
							currentTransObject._areaSize = parser.nextText();
						} else if (name.contentEquals("都市土地使用分區")) {
							currentTransObject._areaUseType = parser.nextText();
						} else if (name.contentEquals("非都市土地使用分區")) {
							currentTransObject._nonAreaUseType = parser
									.nextText();
						} else if (name.contentEquals("非都市土地使用編定")) {
							currentTransObject._nonAreaUseSetting = parser
									.nextText();
						} else if (name.contentEquals("交易年月")) {
							currentTransObject._transactionDate = parser
									.nextText();
						} else if (name.contentEquals("交易筆棟數")) {
							currentTransObject._transactionBuilding = parser
									.nextText();
						} else if (name.contentEquals("移轉層次")) {
							currentTransObject._transLayer = parser.nextText();
						} else if (name.contentEquals("總樓層數")) {
							currentTransObject._totalFloor = parser.nextText();
						} else if (name.contentEquals("建物型態")) {
							currentTransObject._buildingType = parser
									.nextText();
						} else if (name.contentEquals("主要用途")) {
							currentTransObject._usingType = parser.nextText();
						} else if (name.contentEquals("主要建材")) {
							currentTransObject._mainMaterial = parser
									.nextText();
						} else if (name.contentEquals("建築完成年月")) {
							currentTransObject._buildingPublishDate = parser
									.nextText();
						} else if (name.contentEquals("建物移轉總面積平方公尺")) {
							currentTransObject._buildingSize = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-房")) {
							currentTransObject._buildingStatusAndPattern_Room = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-廳")) {
							currentTransObject._buildingStatusAndPattern_LivingRoom = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-衛")) {
							currentTransObject._buildingStatusAndPattern_Bathroom = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-隔間")) {
							currentTransObject._buildingStatusAndPattern_Dividingroom = parser
									.nextText();
						} else if (name.contentEquals("有無管理組織")) {
							currentTransObject._isOrganization = parser
									.nextText();
						} else if (name.contentEquals("有無附傢俱")) {
							currentTransObject.setFurniture(parser.nextText());
						} else if (name.contentEquals("總價元")) {
							currentTransObject._totalPrice = parser.nextText();
						} else if (name.contentEquals("單價每平方公尺")) {
							currentTransObject._priceOfOneArea = parser
									.nextText();
						} else if (name.contentEquals("車位類別")) {
							currentTransObject._parkingType = parser.nextText();
						} else if (name.contentEquals("車位移轉總面積平方公尺")) {
							currentTransObject._parkingTransArea = parser
									.nextText();
						} else if (name.contentEquals("車位總價元")) {
							currentTransObject._parkingPrice = parser
									.nextText();
						}

					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase(XMLBuildingType)
							&& currentTransObject != null) {
						transObjectList.add(currentTransObject);
					}
				}
				eventType = parser.next();
			}
			break;

		case 2:
			XMLBuildingType = "預售屋";

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					transObjectList = new ArrayList<TransactionBuilding>();
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();

					if (name.contentEquals(XMLBuildingType)) {
						currentTransObject = new TransactionBuilding();
						currentTransObject.setXMLBuildingType(XMLBuildingType);

					} else if (currentTransObject != null) {
						if (name.contentEquals("鄉鎮市區")) {
							// Log.d("Insert", parser.nextText());
							currentTransObject._district = parser.nextText();
						} else if (name.contentEquals("交易標的")) {
							currentTransObject._transactionObject = parser
									.nextText();
						} else if (name.contentEquals("土地區段位置或建物區門牌")) {
							currentTransObject._address = parser.nextText();
						} else if (name.contentEquals("土地移轉總面積平方公尺")) {
							currentTransObject._areaSize = parser.nextText();
						} else if (name.contentEquals("都市土地使用分區")) {
							currentTransObject._areaUseType = parser.nextText();
						} else if (name.contentEquals("非都市土地使用分區")) {
							currentTransObject._nonAreaUseType = parser
									.nextText();
						} else if (name.contentEquals("非都市土地使用編定")) {
							currentTransObject._nonAreaUseSetting = parser
									.nextText();
						} else if (name.contentEquals("交易年月")) {
							currentTransObject._transactionDate = parser
									.nextText();
						} else if (name.contentEquals("交易筆棟數")) {
							currentTransObject._transactionBuilding = parser
									.nextText();
						} else if (name.contentEquals("移轉層次")) {
							currentTransObject._transLayer = parser.nextText();
						} else if (name.contentEquals("總樓層數")) {
							currentTransObject._totalFloor = parser.nextText();
						} else if (name.contentEquals("建物型態")) {
							currentTransObject._buildingType = parser
									.nextText();
						} else if (name.contentEquals("主要用途")) {
							currentTransObject._usingType = parser.nextText();
						} else if (name.contentEquals("主要建材")) {
							currentTransObject._mainMaterial = parser
									.nextText();
						} else if (name.contentEquals("建築完成年月")) {
							currentTransObject._buildingPublishDate = parser
									.nextText();
						} else if (name.contentEquals("建物移轉總面積平方公尺")) {
							currentTransObject._buildingSize = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-房")) {
							currentTransObject._buildingStatusAndPattern_Room = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-廳")) {
							currentTransObject._buildingStatusAndPattern_LivingRoom = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-衛")) {
							currentTransObject._buildingStatusAndPattern_Bathroom = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-隔間")) {
							currentTransObject._buildingStatusAndPattern_Dividingroom = parser
									.nextText();
						} else if (name.contentEquals("有無管理組織")) {
							currentTransObject._isOrganization = parser
									.nextText();
						} else if (name.contentEquals("有無附傢俱")) {
							currentTransObject.setFurniture(parser.nextText());
						} else if (name.contentEquals("總價元")) {
							currentTransObject._totalPrice = parser.nextText();
						} else if (name.contentEquals("單價每平方公尺")) {
							currentTransObject._priceOfOneArea = parser
									.nextText();
						} else if (name.contentEquals("車位類別")) {
							currentTransObject._parkingType = parser.nextText();
						} else if (name.contentEquals("車位移轉總面積平方公尺")) {
							currentTransObject._parkingTransArea = parser
									.nextText();
						} else if (name.contentEquals("車位總價元")) {
							currentTransObject._parkingPrice = parser
									.nextText();
						}

					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase(XMLBuildingType)
							&& currentTransObject != null) {
						transObjectList.add(currentTransObject);
					}
				}
				eventType = parser.next();
			}
			break;

		case 3:
			XMLBuildingType = "租賃";

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					transObjectList = new ArrayList<TransactionBuilding>();
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();

					if (name.contentEquals(XMLBuildingType)) {
						currentTransObject = new TransactionBuilding();
						currentTransObject.setXMLBuildingType(XMLBuildingType);

					} else if (currentTransObject != null) {
						if (name.contentEquals("鄉鎮市區")) {
							// Log.d("Insert", parser.nextText());
							currentTransObject._district = parser.nextText();
						} else if (name.contentEquals("租賃標的")) {
							currentTransObject._transactionObject = parser
									.nextText();
						} else if (name.contentEquals("土地區段位置或建物區門牌")) {
							currentTransObject._address = parser.nextText();
						} else if (name.contentEquals("租賃總面積平方公尺")) {
							currentTransObject._areaSize = parser.nextText();
						} else if (name.contentEquals("都市土地使用分區")) {
							currentTransObject._areaUseType = parser.nextText();
						} else if (name.contentEquals("非都市土地使用分區")) {
							currentTransObject._nonAreaUseType = parser
									.nextText();
						} else if (name.contentEquals("非都市土地使用編定")) {
							currentTransObject._nonAreaUseSetting = parser
									.nextText();
						} else if (name.contentEquals("租賃年月")) {
							currentTransObject._transactionDate = parser
									.nextText();
						} else if (name.contentEquals("租賃筆棟數")) {
							currentTransObject._transactionBuilding = parser
									.nextText();
						} else if (name.contentEquals("租賃層次")) {
							currentTransObject._transLayer = parser.nextText();
						} else if (name.contentEquals("總樓層數")) {
							currentTransObject._totalFloor = parser.nextText();
						} else if (name.contentEquals("建物型態")) {
							currentTransObject._buildingType = parser
									.nextText();
						} else if (name.contentEquals("主要用途")) {
							currentTransObject._usingType = parser.nextText();
						} else if (name.contentEquals("主要建材")) {
							currentTransObject._mainMaterial = parser
									.nextText();
						} else if (name.contentEquals("建築完成年月")) {
							currentTransObject._buildingPublishDate = parser
									.nextText();
						} else if (name.contentEquals("租賃總面積平方公尺")) {
							currentTransObject._buildingSize = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-房")) {
							currentTransObject._buildingStatusAndPattern_Room = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-廳")) {
							currentTransObject._buildingStatusAndPattern_LivingRoom = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-衛")) {
							currentTransObject._buildingStatusAndPattern_Bathroom = parser
									.nextText();
						} else if (name.contentEquals("建物現況格局-隔間")) {
							currentTransObject._buildingStatusAndPattern_Dividingroom = parser
									.nextText();
						} else if (name.contentEquals("有無管理組織")) {
							currentTransObject._isOrganization = parser
									.nextText();
						} else if (name.contentEquals("有無附傢俱")) {
							currentTransObject.setFurniture(parser.nextText());
						} else if (name.contentEquals("總額元")) {
							currentTransObject._totalPrice = parser.nextText();
						} else if (name.contentEquals("單價每平方公尺")) {
							currentTransObject._priceOfOneArea = parser
									.nextText();
						} else if (name.contentEquals("車位類別")) {
							currentTransObject._parkingType = parser.nextText();
						} else if (name.contentEquals("租賃總面積平方公尺")) {
							currentTransObject._parkingTransArea = parser
									.nextText();
						} else if (name.contentEquals("租金總額元")) {
							currentTransObject._parkingPrice = parser
									.nextText();
						}

					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase(XMLBuildingType)
							&& currentTransObject != null) {
						transObjectList.add(currentTransObject);
					}
				}
				eventType = parser.next();
			}
			break;

		default:
			break;
		}

	}

	public String[] addressToLatLon(String address) {
		Geocoder geo = new Geocoder(getActivity(), Locale.getDefault());
		String latlon[] = new String[2];
		try {
			List<Address> addresses = geo.getFromLocationName(address, 1);
			if (!addresses.isEmpty()) {
				Address adsLocation = addresses.get(0);
				double geoLatitude = adsLocation.getLatitude();
				double geoLongitude = adsLocation.getLongitude();
				latlon[0] = String.valueOf(geoLatitude);// lat
				latlon[1] = String.valueOf(geoLongitude);// lon
				Log.i("Geocoder:" + address, geoLatitude + "," + geoLongitude);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return latlon;
	}

	public void initIconGenerator() {

		IconGenerator mClusterIconGenerator = new IconGenerator(getActivity());

		FoundiMarkerView = ((LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.custom_marker_layout, null);
		imgView = (ImageView) FoundiMarkerView.findViewById(R.id.img_foundi);
		mClusterIconGenerator.setContentView(FoundiMarkerView);
		numTxt = (TextView) FoundiMarkerView.findViewById(R.id.num_txt);
	}

	public Bitmap createDrawableFromView(Activity context, View view) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		view.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
		view.layout(0, 0, displayMetrics.widthPixels,
				displayMetrics.heightPixels);
		view.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
				view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		return bitmap;
	}
}