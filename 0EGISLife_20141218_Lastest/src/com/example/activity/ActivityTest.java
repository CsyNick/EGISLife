package com.example.activity;

import java.util.HashMap;
import java.util.List;

import com.database.sqldb.DataList_TaipeiLandmark;
import com.database.sqldb.TaipeiLandmark;
import com.example.egislife.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActivityTest extends Activity {
	TextView textView;
	List<TaipeiLandmark> landmarks = null;
     String txt="";
     DataList_TaipeiLandmark dataList_TaipeiLandmark =null;
     HashMap<Integer, List<TaipeiLandmark>> hashMapListTaHashMap =new HashMap<Integer, List<TaipeiLandmark>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test);
		textView = (TextView) findViewById(R.id.txtActivityText);
	 dataList_TaipeiLandmark = new DataList_TaipeiLandmark(getApplicationContext());
          
	 loadALLTaipeiLandmark();
	      for (int i = 1; i <= 9; i++) {
	    	  landmarks =  hashMapListTaHashMap.get(i);
	    	  txt +=landmarks.get(0).getClass_main()+":"+landmarks.size()+"筆" + '\n';
	    	
	}
	      textView.setText(txt);
		
		
	}

	   public void loadALLTaipeiLandmark(){
		      for (int i = 1; i <=9; i++) {
		    	  landmarks = dataList_TaipeiLandmark.getTaipeimarkList(i);
		    	  Log.i("Size", landmarks.size()+"");
		    	  hashMapListTaHashMap.put(i, landmarks);
			}
	   }
	   public String loadingTaipeiLandmark(List<TaipeiLandmark> landmarks){
	    	String str="";
	    	for(TaipeiLandmark landmark :landmarks){
	    		str+= landmark.getMarkName();
	    	}
			return str;
	    	
	    	
	    }
}
