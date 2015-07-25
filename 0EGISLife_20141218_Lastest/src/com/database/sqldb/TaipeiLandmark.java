package com.database.sqldb;

import android.graphics.drawable.Drawable;

import com.example.tools.NGISTool;

public class TaipeiLandmark {

	private String id; // col_1
	private String markName; // col_2
	private String district; // col_3
	private String address; // col_4
	private String x; // col_9
	private String y; // col_10
	private String url_webpage; // col_11
	private String village; // col_16
	private String class_main; // col_17
	private String class_middle; // col_18
	private String class_small; // col_19
	private String class_detail; // col_20
	private Drawable drawable;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarkName() {
		return markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getUrl_webpage() {
		return url_webpage;
	}

	public void setUrl_webpage(String url_webpage) {
		this.url_webpage = url_webpage;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getClass_main() {
		return class_main;
	}

	public void setClass_main(String class_main) {
		this.class_main = class_main;
	}

	public String getClass_middle() {
		return class_middle;
	}

	public void setClass_middle(String class_middle) {
		this.class_middle = class_middle;
	}

	public String getClass_detail() {
		return class_detail;
	}

	public void setClass_detail(String class_detail) {
		this.class_detail = class_detail;
	}

	public String getClass_small() {
		return class_small;
	}

	public void setClass_small(String class_small) {
		this.class_small = class_small;
	}
	public double[] getlnglat(){
		  
		 return NGISTool.TWD97TM2toWGS84(Double.parseDouble(x), Double.parseDouble(y));
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
   
}
