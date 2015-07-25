package com.example.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.example.egislife.R;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageTool {
	
	String TAG = "ImageTool";
	TextView txtView;
	View view;
	Context activity;
	ImageView imgView;
	
	public ImageTool(Activity activity){
		this.activity = activity;
		txtView = (TextView)activity.findViewById(R.id.txt);
		imgView = (ImageView)activity.findViewById(R.id.imageView1);
		view = activity.findViewById(R.id.marker_view);
		Typeface fontFace = Typeface.createFromAsset(activity.getAssets(), "fonts/postoffice.ttf");
		txtView.setTypeface(fontFace);
		//view = activity.getLayoutInflater().inflate(R.layout.marker, null);
	}
	
	public ImageTool(TextView txtView, View view, ImageView imgView){
		this.txtView = txtView;
		this.view = view;
		this.imgView = imgView;
	}
	
	public Bitmap CreateBitmap(int Value, int Pixel_width, int Pixel_heigh){
		int scale = 600/Pixel_width;
		Log.i(TAG, scale+"");
		txtView.setText(Integer.toString(Value));
		txtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtView.getTextSize()/scale);
		view.setDrawingCacheEnabled(true);
		view.measure(MeasureSpec.makeMeasureSpec(Pixel_width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(Pixel_heigh, MeasureSpec.EXACTLY));
		
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache(true);
		
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		return bitmap;
	}
	
	public Bitmap[] CreateBitmap(int[] numberArray){
		
		Bitmap[] bitmapArrray = new Bitmap[numberArray.length];
		
		for(int i=0; i<numberArray.length; i++){
			txtView.setText(Integer.toString(numberArray[i]));
			view.setDrawingCacheEnabled(true);
			
			view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
			view.buildDrawingCache(true);
			
			Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
			
			bitmapArrray[i] = bitmap;
		}
		return bitmapArrray;
	}
	
	public void ConvertToImage(Bitmap bitmap){
		
		view.setDrawingCacheEnabled(false);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "project.png");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
        }
	}
	
	
}
