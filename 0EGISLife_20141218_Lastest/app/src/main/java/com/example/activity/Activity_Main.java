package com.example.activity;

import java.util.List;
import com.database.sqldb.DataList_TaipeiLandmark;
import com.database.sqldb.TaipeiLandmark;
import com.example.adapter.Adapter_Pager;
import com.example.adapter.GridAdapter;
import com.example.egislife.CustomViewPager;

import com.example.egislife.R;
import com.example.fragment.Fragment_Control;
import com.example.fragment.Map_Fragment;
import com.example.fragment.MyDialogFragment.EditNameDialogListener;

import com.example.usaul.tool.ShowSomething;
import com.google.android.gms.maps.model.CameraPosition;
import com.jpyu.MRTstation.Taipei;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Activity_Main extends FragmentActivity implements
		ActionBar.TabListener ,EditNameDialogListener{

	private ActionBar actionBar;
	private MenuItem mapSwitch;
	private Adapter_Pager mAdapter;
	private CustomViewPager mPager;
	public static List<TaipeiLandmark> listTaipeiData = null;
	public static int selection = 0;
	public static String district = null;// default
	public static Taipei taipei = null;
	public static double range = 0.5;// km
	public static boolean isHeatMap = false;
	public static boolean isLifeScoreLayer = true;
	public static boolean isFoundiAnalysis = false;
	public static Tab mapTypeTab = null;
	public static String[] lifeScoreString;
	public static GridAdapter ga;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_pager);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		taipei = (Taipei) getIntent().getSerializableExtra("Taipei");
		district = taipei.District;

		setTaipeiDistrictAndClassMainByRange(district, selection, taipei.lng,
				taipei.lat, range);
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setTitle(taipei.District);

		initView();
		initAnalysisTerms();
		
	}

	private void initView() {

		mAdapter = new Adapter_Pager(getSupportFragmentManager());
		mPager = (CustomViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.requestTransparentRegion(mPager);

		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mapTypeTab = actionBar.newTab().setText("生活機能指數").setTabListener(this);
		actionBar.addTab(mapTypeTab, 0, true);

		Tab conditionTab = actionBar.newTab().setText("分析條件")
				.setTabListener(this);
		actionBar.addTab(conditionTab, 1, false);

		mPager.setSwipeable(false);
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	

	/**
	 * 
	 * @param selectoin
	 *            0.休閒購物 1.美食佳餚 2.政府機關 3.藝術文化 4.文教機構 5.交通建設 6.公用事業 7.工商服務 8.醫療保健
	 * 
	 * 
	 */
	public void setTaipeiDistrictAndClassMain(String district, int selection) {

		DataList_TaipeiLandmark dataList_TaipeiLandmark = new DataList_TaipeiLandmark(
				getApplicationContext());

		listTaipeiData = dataList_TaipeiLandmark
				.getTaipeimarkListByDistrictAndClassMain(district, selection);
		Log.i("Size", listTaipeiData.size() + "");

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
				getApplicationContext());

		listTaipeiData = dataList_TaipeiLandmark
				.getTaipeimarkListByDistrictAndClassMainWithRange(district,
						selection, centerLng, centerLat, km);
		Log.i("Size", listTaipeiData.size() + "");

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:
			this.finish();
			return true;

		case R.id.menuitem_LandMark:

			if (isHeatMap == true) {
			
				mapSwitch.setTitle("地標模式");
				mapTypeTab.setText("熱區圖");
				isHeatMap = false;
				isLifeScoreLayer = false;
				isFoundiAnalysis = false;
				
				Adapter_Pager.map_Fragment.showHeatMap();
			} else if (!isHeatMap) {
			
				mapSwitch.setTitle("熱區圖");
				mapTypeTab.setText("地標模式");
				isHeatMap = true;
				isLifeScoreLayer = false;
				isFoundiAnalysis = false;
				Adapter_Pager.map_Fragment.showMarker();
			}

			return true;

		case R.id.menuitem_LifeScoreLayer:
			isLifeScoreLayer = true;
			isFoundiAnalysis = false;
			CameraPosition cameraPosition = Map_Fragment.googleMap.getCameraPosition();
			double lat = cameraPosition.target.latitude;
			double lon = cameraPosition.target.longitude;
			Adapter_Pager.control_fragment.changeByControl_ALL(
					Fragment_Control.sfilter, lon, lat);
			Map_Fragment.textView.setText("生活機能指數");
			mapTypeTab.setText("生活機能指數");
			ShowSomething.showToast(getApplicationContext(), "生活機能指數");
			return true;

		case R.id.menuitem_Foundi:
			Map_Fragment.textView.setText("實價登錄");
			mapTypeTab.setText("實價登錄");
	
			isLifeScoreLayer = false;
			isFoundiAnalysis = true;
			Adapter_Pager.map_Fragment.setFoundiAnalysis();
			
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		mapSwitch = menu.findItem(R.id.menuitem_LandMark);
		return super.onPrepareOptionsMenu(menu);
	}

	// ==================
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public void initAnalysisTerms() {

		lifeScoreString = this.getResources().getStringArray(R.array.lifeScore);
		ga = new GridAdapter(this, lifeScoreString);
		ga.setChecked(0, true);
		ga.setChecked(1, true);

	}

	@Override
	public void onFinishEditDialog(String inputText) {
		// TODO Auto-generated method stub
		ShowSomething.showToast(this, inputText);
	}
	
	


}
