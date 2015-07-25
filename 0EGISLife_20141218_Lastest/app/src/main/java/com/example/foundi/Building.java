package com.example.foundi;

public class Building {
	/**
	 *   <買賣>
    <鄉鎮市區>文山區</鄉鎮市區>
    <交易標的>房地(土地+建物)</交易標的>
    <土地區段位置或建物區門牌>臺北市文山區育英街1~30號</土地區段位置或建物區門牌>
    <土地移轉總面積平方公尺>36.34</土地移轉總面積平方公尺>
    <都市土地使用分區>住</都市土地使用分區>
    <非都市土地使用分區/>
    <非都市土地使用編定/>
    <交易年月>10307</交易年月>
    <交易筆棟數>土地1建物1車位0</交易筆棟數>
    <移轉層次>三層</移轉層次>
    <總樓層數>004</總樓層數>
    <建物型態>公寓(5樓含以下無電梯)</建物型態>
    <主要用途>住家用</主要用途>
    <主要建材>鋼筋混凝土造</主要建材>
    <建築完成年月/>
    <建物移轉總面積平方公尺>92.72</建物移轉總面積平方公尺>
    <建物現況格局-房>3</建物現況格局-房>
    <建物現況格局-廳>2</建物現況格局-廳>
    <建物現況格局-衛>1</建物現況格局-衛>
    <建物現況格局-隔間>有</建物現況格局-隔間>
    <有無管理組織>無</有無管理組織>
    <總價元>14400000</總價元>
    <單價每平方公尺>155306</單價每平方公尺>
    <車位類別/>
    <車位移轉總面積平方公尺>0.0</車位移轉總面積平方公尺>
    <車位總價元>0</車位總價元>
  </買賣>
	 */
	//private variables
	public int _id;
	public String _district;
	public String _transactionObject;
	public	String _address;
	public	String _areaSize;
	public	String _areaUseType;
	public	String _nonAreaUseType;
	public	String _nonAreaUseSetting;
	public	String _transactionDate;
	public	String _transactionBuilding;
	public	String _transLayer;
	public	String _totalFloor;
	public	String _buildingType;
	public	String _usingType;
	public	String _mainMaterial;
	public	String _buildingPublishDate;
	public	String _buildingSize;
	public	String _buildingStatusAndPattern_Room;
	public	String _buildingStatusAndPattern_LivingRoom;
	public	String _buildingStatusAndPattern_Bathroom;
	public	String _buildingStatusAndPattern_Dividingroom;
	public	String _isOrganization;
	public	String _totalPrice="0s";
	public	String _priceOfOneArea="0";
	public	String _parkingType;
	public	String _parkingTransArea;
	public	String _parkingPrice;

	// Empty constructor
	public Building(){
		
	}
	// constructor
	public Building(int _id, String _district, String _transactionObject,
			String _address, String _areaSize, String _areaUseType,
			String _nonAreaUseType, String _nonAreaUseSetting,
			String _transactionDate, String _transactionBuilding,
			String _transLayer, String _totalFloor, String _buildingType,
			String _usingType, String _mainMaterial,
			String _buildingPublishDate,String _buildingSize, String _buildingStatusAndPattern_Room,
			String _buildingStatusAndPattern_LivingRoom,
			String _buildingStatusAndPattern_Bathroom,
			String _buildingStatusAndPattern_Dividingroom,
			String _isOrganization, String _totalPrice, String _priceOfOneArea,
			String _parkingType, String _parkingTransArea, String _parkingPrice) {
	
		this._id = _id;
		this._district = _district;
		this._transactionObject = _transactionObject;
		this._address = _address;
		this._areaSize = _areaSize;
		this._areaUseType = _areaUseType;
		this._nonAreaUseType = _nonAreaUseType;
		this._nonAreaUseSetting = _nonAreaUseSetting;
		this._transactionDate = _transactionDate;
		this._transactionBuilding = _transactionBuilding;
		this._transLayer = _transLayer;
		this._totalFloor = _totalFloor;
		this._buildingType = _buildingType;
		this._usingType = _usingType;
		this._mainMaterial = _mainMaterial;
		this._buildingPublishDate = _buildingPublishDate;
		this._buildingSize = _buildingSize;
		this._buildingStatusAndPattern_Room = _buildingStatusAndPattern_Room;
		this._buildingStatusAndPattern_LivingRoom = _buildingStatusAndPattern_LivingRoom;
		this._buildingStatusAndPattern_Bathroom = _buildingStatusAndPattern_Bathroom;
		this._buildingStatusAndPattern_Dividingroom = _buildingStatusAndPattern_Dividingroom;
		this._isOrganization = _isOrganization;
		this._totalPrice = _totalPrice;
		this._priceOfOneArea = _priceOfOneArea;
		this._parkingType = _parkingType;
		this._parkingTransArea = _parkingTransArea;
		this._parkingPrice = _parkingPrice;
	}
    


}
