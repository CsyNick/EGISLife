package csy.arcgisTools;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.io.UserCredentials;

public class NGisMapTools {
//	UserCredentials.setUserToken("9BUxW8Yi4Fv_giW1T3TLSc6RtymfSGw4PX3v5LsoRfEgdsdOHeRfxikfEew33mKKPfBENKLUqyOWqwUDuQ6_ZA..","http://egis.moea.gov.tw") 
	final String token = "9BUxW8Yi4Fv_giW1T3TLSc6RtymfSGw4PX3v5LsoRfEgdsdOHeRfxikfEew33mKKPfBENKLUqyOWqwUDuQ6_ZA..";
	final String referer = "http://egis.moea.gov.tw";
	final UserCredentials creds = new UserCredentials();

	HashMap<String, Layer> EsriExistingLayers_HashMap = new HashMap<String, Layer>();
	HashMap<String, Layer> WSExistingLayers_HashMap = new HashMap<String, Layer>();

	HashMap<String, String> TiledLayerWebServiceUrl_HashMap = new HashMap<String, String>();
	HashMap<Integer, String> DynamicLayerName_HashMap = new HashMap<Integer, String>();
	HashMap<String, Integer> DynamicLayerId_HashMap = new HashMap<String, Integer>();

	HashMap<String, Boolean> radioLayerType_HashMapx = new HashMap<String, Boolean>();
	HashMap<String, LayerInfo> LayerInfo_HashMapx = new HashMap<String, LayerInfo>();

	String[] tiledLayerName = null;
	String[] tiledLayerWebServiceUrl = null;

	String dynamicMapURL = null;
	int[] dynamicLayerId = null;
	String[] dynamicLayerName = null;
	
	public static String[] tLayerName = null;
	public static String[] dLayerName = null;
			
	MapView EsriMap = null;

	public NGisMapTools(MapView map) {
		creds.setUserToken(token, referer);
		this.EsriMap = map;
		setUpMapInfo();
	}

	public String getUrl(String layerName) {
		String urlStr = null;
		urlStr = TiledLayerWebServiceUrl_HashMap.get(layerName);
		return urlStr;
	}

	public Layer addNGisLayer(String layerName) {
		Layer layer = WSExistingLayers_HashMap.get(layerName);
		if (layer != null) {
			// 表示已利用web service取得此layer，直接回傳即可
			return layer;
		}

		//---------------------------------------------------------------
		// 表示尚未利用web service取得此layer
		String urlStr = TiledLayerWebServiceUrl_HashMap.get(layerName);
		if (urlStr != null) {
			// 表示此為一種TiledLayer
			layer = new ArcGISTiledMapServiceLayer(getUrl(layerName), creds);
			WSExistingLayers_HashMap.put(layerName, layer);
			return layer;
		}
		//---------------------------------------------------------------
		Integer intObj = DynamicLayerId_HashMap.get(layerName);
		if (intObj != null) {
			// 表示此為一種DynamicLayer
			int dynamicLayerId = DynamicLayerId_HashMap.get(layerName);
			int[] layers = { dynamicLayerId };
			layer = new ArcGISDynamicMapServiceLayer(MapInfo.dynamicMapURL,
					layers, creds);
			WSExistingLayers_HashMap.put(layerName, layer);
			return layer;
		}
		//---------------------------------------------------------------
		//表示所指定的Layer不存在
		return null;
	}

	public void setOpacity(String layerName, double opacity) {
		Layer layer = EsriExistingLayers_HashMap.get( layerName);
		if(layer != null) layer.setOpacity((float)opacity);
	}
	
	public void showNGisLayer(String selectedlayerName) {
		String[] selectedLayerNameArr = selectedlayerName.split(",");
		Layer layer = null;
		for (String layerName : selectedLayerNameArr) {
			//此迴圈中會自動自Web service取得尚未存在於WSExistingLayers_HashMap中之Layer
			layer = addNGisLayer(layerName.trim());
			EsriExistingLayers_HashMap.put(layerName, layer);
		} 
       Log.i("jpyuMsg: EsriExistingLayers_HashMap=", ""+EsriExistingLayers_HashMap.size());
		//================================
		//將Layer適當分類以便加入EsriMap中
		LayerInfo layerInfo = null;
		Layer backgroundLayer = null;
		ArrayList <Layer>radioTypeLayerList = new ArrayList <Layer>();
		ArrayList <Layer> nonRadioTypeLayerList = new ArrayList <Layer>();
		for (String layerName : selectedLayerNameArr) {
			layer = WSExistingLayers_HashMap.get(layerName);
			layerInfo = LayerInfo_HashMapx.get(layerName);
			if(layerInfo.isBackgroundLayer){
				backgroundLayer  = layer;
			}else{
				if(layerInfo.isRadioType) {
					radioTypeLayerList.add(layer);
				}else{
					nonRadioTypeLayerList.add(layer);
				}
			}

			//================================
			//將選擇的Layer加入EsriMap中
			EsriMap.removeAll();
			int ic=0;
			if(backgroundLayer != null) EsriMap.addLayer(backgroundLayer);
			for(Layer layerX: radioTypeLayerList){
				EsriMap.addLayer(layerX);
			       Log.i("jpyuMsg:  ", "radioTypeLayerList["+ ++ic +"]=");
			}
			for(Layer layerX: nonRadioTypeLayerList){
				EsriMap.addLayer(layerX);
			       Log.i("jpyuMsg:  ", "nonRadioTypeLayerList["+ ++ic +"]=");
			}
		} 
	}

	public void setUpMapInfo() {

		// ================================================
		String[] LayerName_radioType = {
				// 底圖切換
				"電子地圖", "彩色正射影像", "地形彩色暈渲圖"
				// 圖層清單(非動態)
				, "現存礦區", "1:5000圖框", "河川流域", "供水系統", "土壤圖", "地質圖"
				// 加值圖層
				, "降雨量圖", "日照時數", "一月平均氣溫圖", "七月平均氣溫圖", "等高線圖"

		};

		for (int i = 0; i < LayerName_radioType.length; i++) {
			radioLayerType_HashMapx.put(LayerName_radioType[i], true);
		}
		// ================================================
		String[] tLayerName = {
				// 底圖切換
				"電子地圖", "彩色正射影像", "地形彩色暈渲圖"
				// 圖層清單(非動態)
				, "現存礦區", "1:5000圖框", "河川流域", "供水系統", "土壤圖", "地質圖"
				// 加值圖層
				, "降雨量圖", "日照時數", "一月平均氣溫圖", "七月平均氣溫圖", "等高線圖" };
      NGisMapTools.tLayerName = tLayerName;
      
		String[] tLayerWebServiceUrl = {
				// 底圖切換
				MapInfo.StreetMap,
				MapInfo.KAPhoto,
				MapInfo.DTM
				
				// 圖層清單(非動態)
				,
				MapInfo.larea,
				MapInfo.m5000,
				MapInfo.basin,
				MapInfo.subdipws,
				MapInfo.planeslp,
				MapInfo.geo
				// ---------------------------------------------------------------------------------
				// 加值圖層
				,
				MapInfo.NGISPERC,
				MapInfo.NGIShours,
				MapInfo.NGIS_PRISM_TAVG1,
				MapInfo.NGIS_PRISM_TAVG7,
				MapInfo.DTMngis_counter
		// --------------------------------------------------------------------------------

		};

		LayerInfo layerInfo = null;
		tiledLayerName = tLayerName;
		tiledLayerWebServiceUrl = tLayerWebServiceUrl;
		for (int i = 0; i < tiledLayerName.length; i++) {
			TiledLayerWebServiceUrl_HashMap.put(tiledLayerName[i].trim(), tiledLayerWebServiceUrl[i]);
			layerInfo = new LayerInfo();
			layerInfo.isDynamicLayer = false;
			layerInfo.isBackgroundLayer = false;
			if(i == 0 || i == 1 || i == 2) layerInfo.isBackgroundLayer = true;
			if(radioLayerType_HashMapx.get(tiledLayerName[i])  == null){
				layerInfo.isRadioType = false;
			}else{
				layerInfo.isRadioType = true;
			}
			LayerInfo_HashMapx.put(tiledLayerName[i].trim(), layerInfo);
		}

		// 圖層清單(動態)
		int[] dLayerId = { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		String[] dLayerName = { "雨量站", "氣象站", "礦物與礦產", "河川水位測站", "地下水位測站",
				"衛星控制點", "河川水質測站", "地震震央", "海堤", "堤防或護岸", "活動斷層", "河川土石資源",
				"水庫集水區", "地下水分區", "自來水區處", "農田水利會" };
		NGisMapTools.dLayerName = dLayerName;
		
		dynamicMapURL = MapInfo.dynamicMapURL;
		dynamicLayerId = dLayerId;
		dynamicLayerName = dLayerName;
		for (int i = 0; i < dynamicLayerId.length; i++) {
			DynamicLayerName_HashMap.put(new Integer(dynamicLayerId[i]), dynamicLayerName[i].trim());
			layerInfo = new LayerInfo();
			layerInfo.isDynamicLayer = true;
			layerInfo.isBackgroundLayer = false;
			if(radioLayerType_HashMapx.get(dynamicLayerName[i])  == null){
				layerInfo.isRadioType = false;
			}else{
				layerInfo.isRadioType = true;
			}
			LayerInfo_HashMapx.put(dynamicLayerName[i].trim(), layerInfo);
		}
		for (int i = 0; i < dynamicLayerId.length; i++) {
			DynamicLayerId_HashMap.put(dynamicLayerName[i].trim(), new Integer(dynamicLayerId[i]));
		}


	}

	class LayerInfo{
		boolean isDynamicLayer = true;
		boolean isRadioType = true;
		boolean isBackgroundLayer = true;
	
    }

}
