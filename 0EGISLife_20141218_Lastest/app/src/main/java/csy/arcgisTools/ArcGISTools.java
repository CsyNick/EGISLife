package csy.arcgisTools;

import android.util.Log;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.io.UserCredentials;

public class ArcGISTools {
	
	/**
	 * UserCredentials.setUserToken("4epdtrJ0MPt2Vko1ztf3aeIxHq6tZN6R4mz-U9UU22WX6vpnCgm0-286acKXiBFyaZABnVkq82vfHZGvtWvFCg..","http://ngis.moea.gov.tw") 
	 * 
	 */
	final String token="9BUxW8Yi4Fv_giW1T3TLSc6RtymfSGw4PX3v5LsoRfEgdsdOHeRfxikfEew33mKKPfBENKLUqyOWqwUDuQ6_ZA..";
    final String referer="http://egis.moea.gov.tw";
    ArcGISTiledMapServiceLayer tileLayer=null;
	ArcGISDynamicMapServiceLayer dynamicLayer =null;
	UserCredentials creds= new UserCredentials(); 
	MapView map;
	
	MySeekBar seekbar;
	static int progress;
	public ArcGISTools(MapView map){
		this.map=map;
		//this.seekbar=seekbar;
		creds.setUserToken(token, referer);
	}
	public ArcGISTools(MapView map,MySeekBar seekbar){
		
		this.seekbar=seekbar;
		creds.setUserToken(token, referer);
		seekbar.setOnSeekBarChangeListener(new MySeekBar.OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(MySeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				  Log.i("progress", String.valueOf(progress));
				  ArcGISTools.progress=progress;
//					dynamicLayer.setOpacity((float)(progress*0.1));
//					map.addLayer(dynamicLayer);
					
			}

			@Override
			public void onStartTrackingTouch(MySeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(MySeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			   
		   });
		
		
		
		
		
		
	}
	public void addDynamicLayer(){//動態圖層
//		int[] layers=MapInfo.i;
		int[] layers={0};
		dynamicLayer=new ArcGISDynamicMapServiceLayer(MapInfo.dynamicMapURL, layers, creds);
		//map.addLayer(dynamicLayer,0);
		//dynamicLayer.getAllLayers();
		 map.addLayer(dynamicLayer);
		
	}
	public  void addvalueLayer(String mapType){
		if(mapType=="降雨量圖"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.NGISPERC,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="日照時數"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.NGIShours,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="一月平均氣溫圖"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.NGIS_PRISM_TAVG1,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="七月平均氣溫圖"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.NGIS_PRISM_TAVG7,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="等高線圖"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.DTMngis_counter,creds);
			map.addLayer(tileLayer);
		}
	}
	public void addLayer2(String mapType){
		if(mapType=="現存礦區"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.larea,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="1:5000圖框"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.m5000,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="河川流域"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.basin,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="供水系統"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.subdipws,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="土壤圖"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.planeslp,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="地質圖"){
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.geo,creds);
			map.addLayer(tileLayer);
		}
	}
	public void addLayer(String mapType){
		if(mapType=="電子地圖"){
			
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.StreetMap,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="彩色正射影像"){
			map.removeAll();
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.KAPhoto,creds);
			map.addLayer(tileLayer);
		}else if(mapType=="地形彩色暈渲圖"){
			map.removeAll();
			tileLayer = new ArcGISTiledMapServiceLayer (
					  MapInfo.DTM,creds);
			tileLayer.toString();
			map.addLayer(tileLayer);
		}else if(mapType=="道路地名標籤"){
			
//			tileLayer = new ArcGISTiledMapServiceLayer (
//					  MapInfo.twLabel10,creds);
//			map.addLayer(tileLayer);
		}
		
		
		
		
		
	}
	
	
	
	
	
	
}
