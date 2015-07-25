package com.google.earth.viewer;

import java.util.ArrayList;
import java.util.List;

import samples.jawsware.interactiveoverlay.SampleOverlayService;


import com.chsy.adapter.Grid_Adapter;
import com.chsy.kml.Tour;
import com.etsy.android.grid.StaggeredGridView;
import com.example.egislife.R;
import com.example.object.GridItem;
import com.example.usaul.tool.ShowSomething;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;

public class ThreeDViewerActivity extends Activity implements
		AbsListView.OnItemClickListener, OnItemSelectedListener {
	ArrayList<String> list = new ArrayList<String>();
	private StaggeredGridView mGridView;
	List<GridItem> gridItemList = null;
	Grid_Adapter grid_Adapter;
	private String[] ngislayer_name_array;
	private String[] ngisayer_URL_Normal_array;
	private MenuItem actionSwitch;
	public static boolean isNavigationMode = true;
	Tour tour;
	private int[] ngislayer_drawagle_array = new int[] { R.drawable.taiwans02,
			R.drawable.taipeidistrict3d, R.drawable.ngis01, R.drawable.ngis10, R.drawable.ngis22,
			R.drawable.ngis02, R.drawable.ngis03, R.drawable.ngis04,
			R.drawable.ngis05, R.drawable.ngis06, R.drawable.ngis07,
			R.drawable.ngis08, R.drawable.ngis09, R.drawable.ngis12,
			R.drawable.ngis13, R.drawable.ngis14, R.drawable.ngis15,
			R.drawable.ngis17, R.drawable.ngis19 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.threedviwer_gridview);
		initGridView();
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	void initGridView() {

		tour = new Tour(getApplicationContext());
		mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
		prepareGridViewData();
		grid_Adapter = new Grid_Adapter(this, R.id.txt_line1,
				prepareGridViewData());
		fillAdapter();
		mGridView.setAdapter(grid_Adapter);
		mGridView.setOnItemClickListener(this);
	}

	private void fillAdapter() {
		for (GridItem data : gridItemList) {
			grid_Adapter.add(data.getName());
		}
	}

	private List<GridItem> prepareGridViewData() {
		ngislayer_name_array = getResources().getStringArray(
				R.array.NGISLayer_Name_array);
		ngisayer_URL_Normal_array = getResources().getStringArray(
				R.array.NGISLayer_URL_Normal_array);
		GridItem gridItem;
		gridItemList = new ArrayList<GridItem>();
		for (int i = 0; i < ngislayer_name_array.length; i++) {
			gridItem = new GridItem(ngislayer_name_array[i],
					ngislayer_drawagle_array[i]);
			gridItemList.add(gridItem);
		}

		return gridItemList;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:
			SampleOverlayService.stop();
			actionSwitch.setTitle("啟動GE控制元件");
			isNavigationMode = true;
			this.finish();
			return true;

		case R.id.menuitem_ActionMode:

			if (isNavigationMode == true) {
				ShowSomething.showToast(getApplicationContext(),
						"Open");
				actionSwitch.setTitle("關閉GE控制元件");
				startService(new Intent(this, SampleOverlayService.class));
				
				isNavigationMode = false;
			} else if (!isNavigationMode) {
				ShowSomething.showToast(getApplicationContext(), "Close");
				SampleOverlayService.stop();
				actionSwitch.setTitle("啟動GE控制元件");
				isNavigationMode = true;
			}

			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.threed_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		actionSwitch = menu.findItem(R.id.menuitem_ActionMode);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.d("Chsy", "Click" + position);
		/*
		 * <item>降雨量圖</item> <item>一月氣溫圖</item> <item>台北市行政區概況</item>
		 * <item>日照時數圖</item> <item>地質圖</item> <item>土壤圖</item>
		 * <item>供水系統</item> <item>河川流域</item> <item>現存礦區</item>
		 * <item>地形起伏</item> <item>地形圖</item> <item>等高線圖</item> <item>雨量站</item>
		 * <item>礦物礦產</item> <item>地震震央</item> <item>河川水質測站</item>
		 * <item>氣象站</item> <item>地下水分區</item>
		 */

		

			switch (position) {
			case 0:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[0]);
				break;
			case 1:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[1]);
				break;
			case 2:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[2]);
				break;
			case 3:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[3]);
				break;
			case 4:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[4]);
				break;
			case 5:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[5]);
				break;
			case 6:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[6]);
				break;
			case 7:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[7]);
				break;
			case 8:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[8]);
				break;
			case 9:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[9]);
				break;
			case 10:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[10]);
				break;
			case 11:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[11]);
				break;
			case 12:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[12]);
				break;
			case 13:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[13]);
				break;
			case 14:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[14]);
				break;
			case 15:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[15]);
				break;
			case 16:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[16]);
				break;
			case 17:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[17]);
				break;

			case 18:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[18]);
				break;
			case 19:
				tour.openKmlToGEbySDCard(Tour.SD_PATH + "/3DEGIS/"
						+ ngisayer_URL_Normal_array[19]);
				break;
			}
		 

	}

}
