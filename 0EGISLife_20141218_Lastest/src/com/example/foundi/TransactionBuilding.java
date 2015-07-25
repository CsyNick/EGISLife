package com.example.foundi;

public class TransactionBuilding extends Building {

	/**
	 * <租賃> <鄉鎮市區>松山區</鄉鎮市區> <租賃標的>建物</租賃標的>
	 * <土地區段位置或建物區門牌>臺北市松山區南京東路五段331~360號</土地區段位置或建物區門牌>
	 * <租賃總面積平方公尺>0.0</租賃總面積平方公尺> <都市土地使用分區>商</都市土地使用分區> <非都市土地使用分區/>
	 * <非都市土地使用編定/> <租賃年月>10307</租賃年月> <租賃筆棟數>土地0建物0車位0</租賃筆棟數> <租賃層次>十層</租賃層次>
	 * <總樓層數>15</總樓層數> <建物型態>辦公商業大樓</建物型態> <主要用途>商業用</主要用途> <主要建材>鋼筋混凝土造</主要建材>
	 * <建築完成年月>0800912</建築完成年月> <租賃總面積平方公尺>257.9</租賃總面積平方公尺>
	 * <建物現況格局-房>4</建物現況格局-房> <建物現況格局-廳>1</建物現況格局-廳> <建物現況格局-衛>0</建物現況格局-衛>
	 * <建物現況格局-隔間>有</建物現況格局-隔間> <有無管理組織>有</有無管理組織> <有無附傢俱>有</有無附傢俱>
	 * <總額元>70000</總額元> <單價每平方公尺>271</單價每平方公尺> <車位類別/>
	 * <租賃總面積平方公尺>0.0</租賃總面積平方公尺> <租金總額元>0</租金總額元> </租賃>
	 */
	public String _furniture;
	public	String _XMLBuildingType;
	public	String _lat;
	public	String _lon;
	public TransactionBuilding() {
		// TODO Auto-generated constructor stub
	}

	public TransactionBuilding(int parseInt, String string, String string2,
			String string3, String string4, String string5, String string6,
			String string7, String string8, String string9, String string10,
			String string11, String string12, String string13, String string14,
			String string15, String string16, String string17, String string18,
			String string19, String string20, String string21, String string22,
			String string23, String string24, String string25, String string26) {
		super(parseInt, string, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, string14, string15, string16, string17, string18, string19, string20, string21, string22, string23, string24, string25, string26);
		// TODO Auto-generated constructor stub
	}

	public void setFurniture(String _furniture) {
		this._furniture = _furniture;
	}
	public void setXMLBuildingType(String _XMLBuildingType){
		this._XMLBuildingType=_XMLBuildingType;
	}
	public void setLAT(String _lat){
		this._lat=_lat;
	}
	
	public void setLON(String _lon){
		this._lon=_lon;
	}
	
}
