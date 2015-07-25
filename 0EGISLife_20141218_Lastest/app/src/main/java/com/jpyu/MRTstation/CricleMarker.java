package com.jpyu.MRTstation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;


class CircleMarker{
	String markerId;
	Bitmap markerImage = null;
	int x_inSourceImage = 0;
	int y_inSourceImage = 0;
	int width, height;
	int xmin, xmax;
	int ymin, ymax;

	
	public CircleMarker(Bitmap markerImage, int x_inSourceImage, int y_inSourceImage){
		this.markerImage = markerImage;
		this.width = markerImage.getWidth();
		this.height = markerImage.getHeight();
		xmin = x_inSourceImage - this.width/2;
		xmax = x_inSourceImage + this.width/2;
		ymin = y_inSourceImage - this.height/2;
		ymax = y_inSourceImage + this.height/2;
		
		this.x_inSourceImage = x_inSourceImage;
		this.y_inSourceImage = y_inSourceImage;
		
		this.markerId = getTimeId();
	}
	
	public CircleMarker(Bitmap markerImage, int x_inSourceImage, int y_inSourceImage, String markerId){
		this.markerImage = markerImage;
		this.width = markerImage.getWidth();
		this.height = markerImage.getHeight();
		xmin = x_inSourceImage - this.width/2;
		xmax = x_inSourceImage + this.width/2;
		ymin = y_inSourceImage - this.height/2;
		ymax = y_inSourceImage + this.height/2;
		
		this.x_inSourceImage = x_inSourceImage;
		this.y_inSourceImage = y_inSourceImage;
		
		this.markerId = markerId;
	}

	public static String getTimeId(){
		// create a date object for testing 
		Date date = new Date(); 
		// create a date instance using default style 
		    // parsing date yyyy-MM-dd HH:mm:ss 
		    SimpleDateFormat simpleFormat = (SimpleDateFormat) DateFormat.getDateInstance(); 
		    simpleFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		    simpleFormat.applyPattern("MM-dd_HH:mm:ss");  
		 return simpleFormat.format(date).toString();

	}
	public boolean isPicked(int x, int y){
		if(x > xmin && x < xmax){
			if(y > ymin && y < ymax){
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		String str = "";
//		str += "<station title='"+markerId+"'>";
//		str += x_inSourceImage+","+y_inSourceImage;
//		str += "</station>";
		str += "station "+markerId+" "+x_inSourceImage+" "+y_inSourceImage;
		return str;
	}
}