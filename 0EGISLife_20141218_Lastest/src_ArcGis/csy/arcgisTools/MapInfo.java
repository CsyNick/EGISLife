 package csy.arcgisTools;

public interface MapInfo {
	//底圖切換
	//http://map.moea.gov.tw/MoeaGIS/rest/services/map2011/MapServer
	String StreetMap="http://maps.moea.gov.tw/moeagis/rest/services/StreetMap/MapServer";//電子地圖
	String KAPhoto="http://maps.moea.gov.tw/moeagis/rest/services/5KAPhoto/MapServer";//彩色正射影像
	String DTM="http://maps.moea.gov.tw/moeagis/rest/services/DTM/MapServer";//地形彩色暈渲圖
    //String twLabel10="http://map.moea.gov.tw/MoeaGIS/rest/services/twLabel10/MapServer";//道路地名標籤
    //---------------------------------------------------------------------------------
    //圖層清單(非動態)
    String larea="http://maps.moea.gov.tw/moeagis/rest/services/Layers/larea/MapServer";//現存礦區
    String m5000="http://maps.moea.gov.tw/moeagis/rest/services/Layers/m5000/MapServer";//1:5000圖框
    String basin="http://maps.moea.gov.tw/moeagis/rest/services/Layers/basin/MapServer";//河川流域
    String subdipws="http://maps.moea.gov.tw/moeagis/rest/services/Layers/subdipws/MapServer";//供水系統
    String planeslp="http://maps.moea.gov.tw/moeagis/rest/services/Layers/planeslp/MapServer";//土壤圖
    String geo="http://maps.moea.gov.tw/moeagis/rest/services/Layers/Geo/MapServer";//地質圖
    //--------------------------------------------------------------------------------
    //加值圖層
    String NGISPERC="http://maps.moea.gov.tw/moeagis/rest/services/NGISPERC/MapServer";//降雨量圖
    String NGIShours="http://maps.moea.gov.tw/moeagis/rest/services/NGIShours/MapServer"; //日照時數
    String NGIS_PRISM_TAVG1="http://maps.moea.gov.tw/moeagis/rest/services/NGIS_PRISM_TAVG1/MapServer";//一月平均氣溫圖
    String NGIS_PRISM_TAVG7="http://maps.moea.gov.tw/moeagis/rest/services/NGIS_PRISM_TAVG7/MapServer";//七月平均氣溫圖
    String DTMngis_counter="http://maps.moea.gov.tw/moeagis/rest/services/DTMngis_counter/MapServer";//等高線圖
    //--------------------------------------------------------------------------------
    //圖層清單(動態)
    String dynamicMapURL="http://maps.moea.gov.tw/moeagis/rest/services/NgisPhone/MapServer";
     int []i={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
     String []s={
    		 "雨量站","氣象站","礦物與礦產","河川水位測站","地下水位測站"
    		 ,"衛星控制點","河川水質測站","地震震央","海堤"
    		,"堤防或護岸","活動斷層","河川土石資源","水庫集水區"
    		,"地下水分區" ,"自來水區處","農田水利會"};
     //rainStn,ppobstanew,mineral,rivwlsta,gwobwell,sagrcopt
     //rivqusta,equke,coastdik＿no ,rivdike,a002b1,rrock,reswshed,GWREGION,distpws,irrig




    

    

	
}