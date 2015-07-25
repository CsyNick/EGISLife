package com.givemepass.FragmentTabs;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.etsy.android.grid.StaggeredGridView;
import com.example.egislife.R;
import com.example.object.GridItem;

import csy.main.Arcgis_Grid_Adapter;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;


public class NgisLayerFragment extends Fragment implements
AbsListView.OnItemClickListener, OnItemSelectedListener {
	private StaggeredGridView mGridView;
	List<GridItem> gridItemList = null;
	Map<String, String> mapNameAndContent =new HashMap<String, String>();
	Arcgis_Grid_Adapter grid_Adapter;
	private int[] ngislayer_drawagle_array = new int[] { R.drawable.ppobstanew,
			R.drawable.mineral, R.drawable.rivwlsta, R.drawable.gwobwell,
			R.drawable.sagrcopt, R.drawable.rivqusta, R.drawable.equke,
			R.drawable.a002b1, R.drawable.reswshed, R.drawable.gwregion,
			R.drawable.distpws, R.drawable.irrig,R.drawable.planeslp,R.drawable.basin};
	private String[] Arcgis_ngislayer_name_array,Arcgis_ngislayer_Content_array;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.arcgis_gridview, container, false);
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initGridView();
	}

	void initGridView() {
		
		mGridView = (StaggeredGridView) getActivity().findViewById(R.id.grid_view);
		prepareGridViewData();
		grid_Adapter = new Arcgis_Grid_Adapter(getActivity(), R.id.txt_line1,
				prepareGridViewData());
		fillAdapter();
		mGridView.setAdapter(grid_Adapter);
		mGridView.setOnItemClickListener(this);
	}
	private void findView() {

		
	}

	private void fillAdapter() {
		for (GridItem data : gridItemList) {
			grid_Adapter.add(data.getName());
		}
	}
	private List<GridItem> prepareGridViewData() {
		Arcgis_ngislayer_name_array = getResources().getStringArray(
				R.array.ARCGIS_NGISLayer_Name_array);
		Arcgis_ngislayer_Content_array = getResources().getStringArray(R.array.ARCGIS_NGISLayer_Content_array);
	
	
		GridItem gridItem;
		gridItemList = new ArrayList<GridItem>();
		for (int i = 0; i < Arcgis_ngislayer_name_array.length; i++) {
			
			mapNameAndContent.put(Arcgis_ngislayer_name_array[i],Arcgis_ngislayer_Content_array[i]);
			gridItem = new GridItem(Arcgis_ngislayer_name_array[i],
					ngislayer_drawagle_array[i]);
			gridItemList.add(gridItem);
		}

		return gridItemList;

	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView txt =(TextView) view.findViewById(R.id.txt_line1);
		Log.i("LayerName", txt.getText().toString());
		String filter ="地形彩色暈渲圖,"+txt.getText().toString().trim();
		Log.i("LayerName", filter);
		ArcgisMapFragment.myNGisMapTools.showNGisLayer(filter);
		ArcgisActivity.actionBar.setTitle(txt.getText().toString());
		ArcgisMapFragment.layerContent.setText(mapNameAndContent.get(txt.getText().toString()));
		getActivity().getActionBar().setSelectedNavigationItem(1);
	}
}