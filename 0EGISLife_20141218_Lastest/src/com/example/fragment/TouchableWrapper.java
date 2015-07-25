package com.example.fragment;

import com.example.activity.Activity_Main;
import com.example.adapter.Adapter_Pager;
import com.example.egislife.R;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class TouchableWrapper extends FrameLayout {

     ImageView imgView;
	public TouchableWrapper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TouchableWrapper(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TouchableWrapper(Context context) {
		super(context);

	}

	private long lastTouched = 0;
	private static final long SCROLL_TIME = 2000L; // 200 Milliseconds, but you
													// can adjust that to your
													// liking
	private UpdateMapAfterUserInterection updateMapAfterUserInterection;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
		  lastTouched = SystemClock.uptimeMillis();
		  Adapter_Pager.map_Fragment.imageView.setVisibility(View.VISIBLE);
			Adapter_Pager.map_Fragment.imageView.setImageResource(R.drawable.focus2);
		  //Log.i("jpyuMsgx", " MotionEvent.ACTION_DOWN");
		break;
		case MotionEvent.ACTION_UP:
	
			final long now = SystemClock.uptimeMillis();
			  //Log.i("jpyuMsgx", " MotionEvent.ACTION_UP now="+now +", lastTouched="+lastTouched);
			if((now - lastTouched) > SCROLL_TIME){
				// Update the map
			
			
				if(updateMapAfterUserInterection != null&&Activity_Main.isLifeScoreLayer){
					
					updateMapAfterUserInterection.onUpdateMapAfterUserInterection();
				
				}
				if(updateMapAfterUserInterection != null&&Activity_Main.isFoundiAnalysis){
					
					updateMapAfterUserInterection.onUpdateMapAfterUserInterection();
				
				}
				  Adapter_Pager.map_Fragment.imageView.setVisibility(View.INVISIBLE);
			}
		break;
	
		case MotionEvent.ACTION_MOVE:
			final long now2 = SystemClock.uptimeMillis();
			if((now2 - lastTouched) > SCROLL_TIME){
			
			  Adapter_Pager.map_Fragment.imageView.setImageResource(R.drawable.focus_red);
			}
			break;
		}
	    return super.dispatchTouchEvent(ev);
	}

	// Map Activity must implement this interface
	public interface UpdateMapAfterUserInterection {
		public void onUpdateMapAfterUserInterection();
		
	}
 
	public void setUpdateMapAfterUserInterection(
			UpdateMapAfterUserInterection mUpdateMapAfterUserInterection) {
		this.updateMapAfterUserInterection = mUpdateMapAfterUserInterection;
	}
	
	

}
