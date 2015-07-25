package com.example.lifescore.tool;

import java.util.List;

import org.achartengine.basic.DataSet;

import android.util.Log;

public class LifeScoreAnalysis {
	String[] class_mainArr = { "休閒購物", "美食佳餚", "政府機關", "藝術文化", "文教機構", "交通建設",
			"公用事業", "工商服務", "醫療保健" };
	private int LeisureShopNum = 50;
	private int DelicioursFood = 50;
	private int GovernmentNum = 50;
	private int ArtCultureNum = 50;
	private int EducationInstituteNum = 50;
	private int TransportationNum = 50;
	private int PublicCareerNum = 50;
	private int CommerceIndustrialNum = 50;
	private int HosipitalNum = 50;
	int[] class_mainNum = { LeisureShopNum, DelicioursFood, GovernmentNum,
			ArtCultureNum, EducationInstituteNum, TransportationNum,
			PublicCareerNum, CommerceIndustrialNum,HosipitalNum
	};
	public double result = 0;
	public List<DataSet> dataSetList = null;

	public LifeScoreAnalysis(List<DataSet> dataSetList) {
		this.dataSetList = dataSetList;
	}

	public int calculateLifeScore() {
		double num = 0 ;
		for (int i=0; i<dataSetList.size();i++) {
			
			if(dataSetList.get(i).getValue()<class_mainNum[i]){
			num=num+(dataSetList.get(i).getValue()/class_mainNum[i]);
			Log.i("LifeScore", num+","+dataSetList.size());
			}else{
				num=num+1;
				
			}
			
		}
		result=num/dataSetList.size();

		return (int)(result*10);

	}

	public int getLeisureShopNum() {
		return LeisureShopNum;
	}

	public void setLeisureShopNum(int leisureShopNum) {
		LeisureShopNum = leisureShopNum;
	}

	public int getDelicioursFood() {
		return DelicioursFood;
	}

	public void setDelicioursFood(int delicioursFood) {
		DelicioursFood = delicioursFood;
	}

	public int getEducationInstituteNum() {
		return EducationInstituteNum;
	}

	public void setEducationInstituteNum(int educationInstituteNum) {
		EducationInstituteNum = educationInstituteNum;
	}

	public int getGovernmentNum() {
		return GovernmentNum;
	}

	public void setGovernmentNum(int governmentNum) {
		GovernmentNum = governmentNum;
	}

	public int getArtCultureNum() {
		return ArtCultureNum;
	}

	public void setArtCultureNum(int artCultureNum) {
		ArtCultureNum = artCultureNum;
	}

	public int getTransportationNum() {
		return TransportationNum;
	}

	public void setTransportationNum(int transportationNum) {
		TransportationNum = transportationNum;
	}

	public int getPublicCareerNum() {
		return PublicCareerNum;
	}

	public void setPublicCareerNum(int publicCareerNum) {
		PublicCareerNum = publicCareerNum;
	}

	public int getCommerceIndustrialNum() {
		return CommerceIndustrialNum;
	}

	public void setCommerceIndustrialNum(int commerceIndustrialNum) {
		CommerceIndustrialNum = commerceIndustrialNum;
	}

}
