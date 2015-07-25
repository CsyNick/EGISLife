package samples.jawsware.interactiveoverlay;

/*
Copyright 2011 jawsware international

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.example.egislife.R;
import com.jawsware.core.share.OverlayService;
import com.jawsware.core.share.OverlayView;

public class SampleOverlayView extends OverlayView {
	public static final String SD_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public static final String FILE_PATH = "/GoogleEarthDB/01/";
	public static final String INPUT_FILENAME = "doc.kml";
	private ImageView info;
	private Context context;
	boolean isTouch = true;
	public SampleOverlayView(OverlayService service) {
		super(service, R.layout.overlay, 1);
	}
  
	
	
	public SampleOverlayView(SampleOverlayService sampleOverlayService,
			Context applicationContext) {
		super(sampleOverlayService,  R.layout.overlay, 1);
		  this.context = applicationContext;
		// TODO Auto-generated constructor stub
	}

//	public int getGravity() {
//		return Gravity.TOP + Gravity.RIGHT;
//	}
	
	@Override
	protected void onInflateView() {
		info = (ImageView) this.findViewById(R.id.image_info);
	}

	@Override
	protected void refreshViews() {
//		info.setText("WAITING\nWAITING");
	}

	@Override
	protected void onTouchEvent_Up(MotionEvent event) {
//		info.setText("UP\nPOINTERS: " + event.getPointerCount());
		info.setAlpha(1.0f);
	}

	@Override
	protected void onTouchEvent_Move(MotionEvent event) {
//		info.setText("MOVE\nPOINTERS: " + event.getPointerCount());
	}

	@Override
	protected void onTouchEvent_Press(MotionEvent event) {
//		info.setText("DOWN\nPOINTERS: " + event.getPointerCount());
		info.setAlpha(0.5f);
		if(isTouch){
			openKmlToGEbySDCard(SD_PATH
					+ "/3DEGIS/"+"TaipeiDistrictDescription.kml");
			isTouch = false;
		}else{
			openKmlToGEbySDCard(SD_PATH
					+ "/3DEGIS/"+"TaipeiDistrictPeople.kml");
			isTouch = true;
		}
	
		
	}

	@Override
	public boolean onTouchEvent_LongPress() {
//		info.setText("LONG\nPRESS");

		return true;
	}
	public void openKmlToGEbySDCard(String path) {
		File file = new File(path);
		Uri uri = Uri.fromFile(file);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		context.startActivity(it);
	}
}
