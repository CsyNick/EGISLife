   package com.example.fragment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.achartengine.basic.DataSet;
import org.achartengine.basic.LifeScoreTools;

import com.database.sqldb.DataList_TaipeiLandmark;
import com.database.sqldb.TaipeiLandmark;
import com.example.activity.Activity_Main;
import com.example.adapter.Adapter_Pager;
import com.example.adapter.GridAdapter;

import com.example.egislife.R;
import com.example.lifescore.tool.LifeScoreAnalysis;
import com.example.usaul.tool.ShowSomething;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Control extends Fragment implements
		AdapterView.OnItemClickListener {
	DFragment dFragment;
	View view;
	View dialog_view = null;
	LinearLayout layout = null;
	
	CheckBox checkAllLeisureShop;

	View rootView;

	public  GridAdapter ga;
	SeekBar seekBar;
	int filter;
	TextView seekerStatus;
	static double fp;
	HashMap<String, Integer> hashMapClasskey;
	DataList_TaipeiLandmark dataList_TaipeiLandmark;
	private static List<TaipeiLandmark> listTaipeiData = null;
	GridView gridView = null;
	Button btn_analysis,btn_modify = null;
	public static String sfilter ;
	String lifeScoreString[] ;
	LifeScoreTools life;
	HashMap<String, Integer> hashMapClassAndListSize =null;
	static List<DataSet> dataSetList=null; 
	TextView txtAddress;
	FragmentManager fm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_control_v2, container,
				false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		findView();
		dataList_TaipeiLandmark = new DataList_TaipeiLandmark(getActivity());
		super.onActivityCreated(savedInstanceState);

	}
    public void init(){
		filter = 0;
		sfilter ="";
	
		int ic = 0;
		for (int i = 0; i < ga.getCount(); i++) {
			
			if (ga.mCheckStates.get(i) == true) {

				ic++;
				sfilter += (ic == 1 ? "" : ",")
						+ hashMapClasskey.get(ga.getItem(i).toString());
			}

		}
		Log.i("sfilter", sfilter);
    }
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 fm = getActivity().getSupportFragmentManager();
		init();
		initData();
		 life = new LifeScoreTools(getActivity());
		 life.createChart();
		 life.setData(getDataSetList());
		gridView.setTextFilterEnabled(true);
		gridView.setOnItemClickListener(this);
		gridView.setAdapter(ga);
		btn_analysis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				filter = 0;
				sfilter ="";
			
				int ic = 0;
				for (int i = 0; i < ga.getCount(); i++) {
					
					if (ga.mCheckStates.get(i) == true) {

						ic++;
						sfilter += (ic == 1 ? "" : ",")
								+ hashMapClasskey.get(ga.getItem(i).toString());
					}

				}
				Log.i("sfilter", sfilter);	
				changeByControl_ALL(sfilter);
				life.setData(getDataSetList());
				Activity_Main.ga = ga;
				
				Map_Fragment.setFragmentControl();
				LifeScoreAnalysis analysis =new LifeScoreAnalysis(dataSetList);
				Log.i("Fragment_Control", analysis.calculateLifeScore()+"!!");
			    Adapter_Pager.map_Fragment.nineGridTool.showNineXNineGridLayerAtMapCenter(analysis.calculateLifeScore());
			}
			
		});
		
		btn_modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showEditDialog();
				
			}
		});

	}
   
	public  void initData() {
		dataSetList =new ArrayList<DataSet>();
       DataSet dataSet;
		Activity_Main.range =fp;
		if(sfilter!=""){
			
		
		String[] strArr = sfilter.split(",");
		Log.d("strArr", strArr.length+"");
		for(int i=0; i< strArr.length; i++){
			Log.i("filter",strArr[i]);
			dataSet=getTaipeiDistrictAndClassMainByRange_DataSet(Activity_Main.district,Integer.parseInt(strArr[i]),
					Activity_Main.taipei.lng, Activity_Main.taipei.lat,
					Activity_Main.range);
			dataSetList.add(dataSet);
		}
		
		}else{
		ShowSomething.showToast(getActivity(),"請設定條件");
		}
		

	}


	public  void changeByControl_ALL(String sfilter) {
		dataSetList =new ArrayList<DataSet>();
       DataSet dataSet;
		Activity_Main.range =fp;
		if(sfilter!=""){
			
		
		String[] strArr = sfilter.split(",");
		Log.d("strArr", strArr.length+"");
		for(int i=0; i< strArr.length; i++){
			Log.i("filter",strArr[i]);
			dataSet=getTaipeiDistrictAndClassMainByRange_DataSet(Activity_Main.district,Integer.parseInt(strArr[i]),
					Activity_Main.taipei.lng, Activity_Main.taipei.lat,
					Activity_Main.range);
			dataSetList.add(dataSet);
		}
		
		}else{
		ShowSomething.showToast(getActivity(),"請設定條件");
		}
		

	}
     
	public  void changeByControl_ALL(String sfilter,double lng,double lat) {
		dataSetList =new ArrayList<DataSet>();
       DataSet dataSet;
		Activity_Main.range =fp;
		if(sfilter!=""){
			
		
		String[] strArr = sfilter.split(",");
		Log.d("strArr", strArr.length+"");
		for(int i=0; i< strArr.length; i++){
			Log.i("filter",strArr[i]);
			dataSet=getTaipeiDistrictAndClassMainByRange_DataSet(Activity_Main.district,Integer.parseInt(strArr[i]),
					lng, lat,
					Activity_Main.range);
			dataSetList.add(dataSet);
		}
		LifeScoreAnalysis analysis =new LifeScoreAnalysis(dataSetList);
		Log.i("Fragment_Control", analysis.calculateLifeScore()+"!!");
	    Adapter_Pager.map_Fragment.nineGridTool.showNineXNineGridLayerAtMapCenter(analysis.calculateLifeScore());
		}else{
		ShowSomething.showToast(getActivity(),"請設定條件");
		}
		

	}
	public static List<DataSet> getDataSetList(){
		
		return dataSetList;
	}
	public void setTaipeiDistrictAndClassMainByRange(String district,
			int selection, double centerLng, double centerLat, double km) {

		listTaipeiData = dataList_TaipeiLandmark
				.getTaipeimarkListByDistrictAndClassMainWithRange(district,
						selection, centerLng, centerLat, km);
		Log.i("Size", listTaipeiData.size() + "");
		Toast.makeText(getActivity(), listTaipeiData.size() + "",
				Toast.LENGTH_LONG).show();

	}
	public HashMap<String, Integer> setTaipeiDistrictAndClassMainByRange_Size(String district,
			int selection, double centerLng, double centerLat, double km) {
		 hashMapClassAndListSize =new HashMap<String, Integer>();
		listTaipeiData = dataList_TaipeiLandmark
				.getTaipeimarkListByDistrictAndClassMainWithRange(district,
						selection, centerLng, centerLat, km);
		
		hashMapClassAndListSize.put(lifeScoreString[selection], listTaipeiData.size());
		Log.i("Size", lifeScoreString[selection]+","+listTaipeiData.size() + "");
	

		return hashMapClassAndListSize;

	}
	public DataSet getTaipeiDistrictAndClassMainByRange_DataSet(String district,
			int selection, double centerLng, double centerLat, double km) {
		
		listTaipeiData = dataList_TaipeiLandmark
				.getTaipeimarkListByDistrictAndClassMainWithRange(district,
						selection, centerLng, centerLat, km);
		
		  DataSet dataSet =new DataSet();
		  dataSet.setCategoryName(lifeScoreString[selection]);
		  dataSet.setValue(listTaipeiData.size());
		 
		Log.i("Size", lifeScoreString[selection]+","+listTaipeiData.size() + "");
		return dataSet;

	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	void findView() {
		btn_modify = (Button)getActivity().findViewById(R.id.btn_modify);
		btn_analysis = (Button) getActivity().findViewById(R.id.button1);
		gridView = (GridView) getActivity().findViewById(R.id.mycontent1);
		seekBar = (SeekBar) getActivity().findViewById(R.id.seekBar1);
		seekerStatus = (TextView) getActivity().findViewById(
				R.id.txtDistanceLimit);
		txtAddress = (TextView) getActivity().findViewById(R.id.textAddress);
		
		try {
			txtAddress.setText("所在地:"+ getLatLngToChineseAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 lifeScoreString = getActivity().getResources().getStringArray(
				R.array.lifeScore);
	     ga = Activity_Main.ga;
		hashMapClasskey = ga.getClassMainKey();
		setSeekBar();

	}

	public void setSeekBar() {
		seekBar.setProgress(5);
		double initSeekBarValue = seekBar.getProgress();
		
		double putvalue = initSeekBarValue * 0.1;
		DecimalFormat df = new DecimalFormat("#0.0");
		fp = new Double(df.format(putvalue));

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progressvalue,
					boolean fromUser) {
				double putvalue = progressvalue * 0.1;
				DecimalFormat df = new DecimalFormat("#0.0");
				fp = new Double(df.format(putvalue));
				seekerStatus.setText("搜尋範圍:" + "" + fp + "公里");
				// TODO Auto-generated method stub

			}
		});

	}
	public void isSameFilter(String filter)
	{
		
		String[] strArr = filter.split(",");
	
		for(int i=0; i< strArr.length; i++){
			Log.i("filter",strArr[i]);
		
		}


	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ga.toggle(position);

	}
	public String getLatLngToChineseAddress() throws IOException {

		Geocoder gc = new Geocoder(getActivity(), Locale.TRADITIONAL_CHINESE);
		List<Address> lstAddress = gc.getFromLocation(Activity_Main.taipei.lat,
				Activity_Main.taipei.lng, 1);
		String returnAddress = lstAddress.get(0).getAddressLine(0);

		//lstAddress.get(0).getCountryName(); // 台灣省
		//lstAddress.get(0).getAdminArea(); // 台北市
		lstAddress.get(0).getLocality(); // 中正區
		lstAddress.get(0).getThoroughfare(); // 信陽街(包含路巷弄)
		lstAddress.get(0).getFeatureName(); // 會得到33(號)

		return returnAddress;

	}

	public  void showEditDialog() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		MyDialogFragment editNameDialog = new MyDialogFragment();
		editNameDialog.show(fm, "fragment_edit_name");
	}

	
}
