package com.example.foundi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class XMLtoSQLiteHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "BuildingManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "transactionTable";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_district = "_district";
	private static final String KEY_transactionObject = "_transactionObject";
	private static final String KEY_address = "_address";
	private static final String KEY_areaSize = "_areaSize";
	private static final String KEY_areaUseType = "_areaUseType";
	private static final String KEY_nonAreaUseType = "_nonAreaUseType";
	private static final String KEY_nonAreaUseSetting = "_nonAreaUseSetting";
	private static final String KEY_transactionDate = "_transactionDate";
	private static final String KEY_transactionBuilding = "_transactionBuilding";
	private static final String KEY_transLayer = "_transLayer";
	private static final String KEY_totalFloor = "_totalFloor";
	private static final String KEY_buildingType = "_buildingType";
	private static final String KEY_usingType = "_usingType";
	private static final String KEY_mainMaterial = "_mainMaterial";
	private static final String KEY_buildingPublishDate = "_buildingPublishDate";
	private static final String KEY_buildingSize = "_buildingSize";
	private static final String KEY_buildingStatusAndPattern_Room = "_buildingStatusAndPattern_Room";
	private static final String KEY_buildingStatusAndPattern_LivingRoom = "_buildingStatusAndPattern_LivingRoom";
	private static final String KEY_buildingStatusAndPattern_Bathroom = "_buildingStatusAndPattern_Bathroom";
	private static final String KEY_buildingStatusAndPattern_Dividingroom = "_buildingStatusAndPattern_Dividingroom";
	private static final String KEY_isOrganization = "_isOrganization";
	private static final String KEY_furniture = "_furniture";
	private static final String KEY_totalPrice = "_totalPrice";
	private static final String KEY_priceOfOneArea = "_priceOfOneArea";
	private static final String KEY_parkingType = "_parkingType";
	private static final String KEY_parkingTransArea = "_parkingTransArea";
	private static final String KEY_parkingPrice = "_parkingPrice";
	private static final String KEY_XMLBuildingType = "_XMLBuildingType";
    private static final String KEY_LAT = "_Lat";
    private static final String KEY_LON = "_Lon";
	public XMLtoSQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_district + " TEXT,"
				+ KEY_transactionObject + " TEXT," + KEY_address + " TEXT,"
				+ KEY_areaSize + " TEXT," + KEY_areaUseType + " TEXT,"
				+ KEY_nonAreaUseType + " TEXT," + KEY_nonAreaUseSetting
				+ " TEXT," + KEY_transactionDate + " TEXT,"
				+ KEY_transactionBuilding + " TEXT," + KEY_transLayer
				+ " TEXT," + KEY_totalFloor + " TEXT," + KEY_buildingType
				+ " TEXT," + KEY_usingType + " TEXT," + KEY_mainMaterial
				+ " TEXT," + KEY_buildingPublishDate + " TEXT,"
				+ KEY_buildingSize + " TEXT,"
				+ KEY_buildingStatusAndPattern_Room + " TEXT,"
				+ KEY_buildingStatusAndPattern_LivingRoom + " TEXT,"
				+ KEY_buildingStatusAndPattern_Bathroom + " TEXT,"
				+ KEY_buildingStatusAndPattern_Dividingroom + " TEXT,"
				+ KEY_isOrganization + " TEXT," + KEY_furniture + " TEXT,"
				+ KEY_totalPrice + " TEXT," + KEY_priceOfOneArea + " TEXT,"
				+ KEY_parkingType + " TEXT," + KEY_parkingTransArea + " TEXT,"
				+ KEY_parkingPrice + " TEXT," + KEY_XMLBuildingType + " TEXT,"
				+ KEY_LAT + " TEXT,"+ KEY_LON + " TEXT"
				+ ")";
		      db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
//
//		// Create tables again
//		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	
	public 	void addContact(TransactionBuilding contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_district, contact._district);
		values.put(KEY_transactionObject, contact._transactionObject);
		values.put(KEY_address, contact._address);
		values.put(KEY_areaSize, contact._areaSize);
		values.put(KEY_areaUseType, contact._areaUseType);
		values.put(KEY_nonAreaUseType, contact._nonAreaUseType);
		values.put(KEY_nonAreaUseSetting, contact._nonAreaUseSetting);
		values.put(KEY_transactionDate, contact._transactionDate);
		values.put(KEY_transactionBuilding, contact._transactionBuilding);
		values.put(KEY_transLayer, contact._transLayer);
		values.put(KEY_totalFloor, contact._totalFloor);
		values.put(KEY_buildingType, contact._buildingType);
		values.put(KEY_usingType, contact._usingType);
		values.put(KEY_mainMaterial, contact._mainMaterial);
		values.put(KEY_buildingPublishDate, contact._buildingPublishDate);
		values.put(KEY_buildingSize, contact._buildingSize);
		values.put(KEY_buildingStatusAndPattern_Room,
				contact._buildingStatusAndPattern_Room);
		values.put(KEY_buildingStatusAndPattern_LivingRoom,
				contact._buildingStatusAndPattern_LivingRoom);
		values.put(KEY_buildingStatusAndPattern_Bathroom,
				contact._buildingStatusAndPattern_Bathroom);
		values.put(KEY_buildingStatusAndPattern_Dividingroom,
				contact._buildingStatusAndPattern_Dividingroom);
		values.put(KEY_isOrganization, contact._isOrganization);
		values.put(KEY_furniture, contact._furniture);
		values.put(KEY_totalPrice, contact._totalPrice);
		values.put(KEY_priceOfOneArea, contact._priceOfOneArea);
		values.put(KEY_parkingType, contact._parkingType);
		values.put(KEY_parkingTransArea, contact._parkingTransArea);
		values.put(KEY_parkingPrice, contact._parkingPrice);
		values.put(KEY_XMLBuildingType, contact._XMLBuildingType);
		values.put(KEY_LAT, contact._lat);
		values.put(KEY_LON, contact._lon);
		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	TransactionBuilding getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_district, KEY_transactionObject,KEY_address, 
				KEY_areaSize,KEY_areaUseType,KEY_nonAreaUseType,KEY_nonAreaUseSetting,KEY_transactionDate
				,KEY_transactionBuilding,KEY_transLayer,KEY_totalFloor,KEY_buildingType,KEY_usingType
				,KEY_mainMaterial,KEY_buildingPublishDate,KEY_buildingSize,KEY_buildingStatusAndPattern_Room
				,KEY_buildingStatusAndPattern_LivingRoom,KEY_buildingStatusAndPattern_Bathroom
				,KEY_buildingStatusAndPattern_Dividingroom,KEY_isOrganization,KEY_furniture
				,KEY_totalPrice,KEY_priceOfOneArea,KEY_parkingType,KEY_parkingTransArea,KEY_parkingPrice
				,KEY_XMLBuildingType,KEY_LAT,KEY_LON
				
				}, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		TransactionBuilding transObject = new TransactionBuilding(
				Integer.parseInt(cursor.getString(0)), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5), cursor.getString(6), cursor.getString(7),
				cursor.getString(8), cursor.getString(9), cursor.getString(10),
				cursor.getString(11), cursor.getString(12),
				cursor.getString(13), cursor.getString(14),
				cursor.getString(15), cursor.getString(16),
				cursor.getString(17), cursor.getString(18),
				cursor.getString(19),cursor.getString(20),
				cursor.getString(21), cursor.getString(23),
				cursor.getString(24), cursor.getString(25),
				cursor.getString(26), cursor.getString(27));
		transObject.setFurniture(cursor.getString(22));
		transObject.setXMLBuildingType(cursor.getString(28));
		transObject.setLAT(cursor.getString(29));
		transObject.setLON(cursor.getString(30));
		// return contact
		return transObject;
	}
	


	public List<TransactionBuilding>  getTransactionBuildingbyDistrcit(String district) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<TransactionBuilding> transObjectList = new ArrayList<TransactionBuilding>();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_district, KEY_transactionObject,KEY_address, 
				KEY_areaSize,KEY_areaUseType,KEY_nonAreaUseType,KEY_nonAreaUseSetting,KEY_transactionDate
				,KEY_transactionBuilding,KEY_transLayer,KEY_totalFloor,KEY_buildingType,KEY_usingType
				,KEY_mainMaterial,KEY_buildingPublishDate,KEY_buildingSize,KEY_buildingStatusAndPattern_Room
				,KEY_buildingStatusAndPattern_LivingRoom,KEY_buildingStatusAndPattern_Bathroom
				,KEY_buildingStatusAndPattern_Dividingroom,KEY_isOrganization,KEY_furniture
				,KEY_totalPrice,KEY_priceOfOneArea,KEY_parkingType,KEY_parkingTransArea,KEY_parkingPrice
				,KEY_XMLBuildingType,KEY_LAT,KEY_LON
				
				}, KEY_district + "=?",
				new String[] { district }, null, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TransactionBuilding transObject = new TransactionBuilding();
				transObject._id = Integer.parseInt(cursor.getString(0));
				transObject._district = cursor.getString(1);
				transObject._transactionObject = cursor.getString(2);
				transObject._address = cursor.getString(3);
				transObject._areaSize = cursor.getString(4);
				transObject._areaUseType = cursor.getString(5);
				transObject._nonAreaUseType = cursor.getString(6);
				transObject._nonAreaUseSetting = cursor.getString(7);
				transObject._transactionDate = cursor.getString(8);
				transObject._transactionBuilding = cursor.getString(9);
				transObject._transLayer = cursor.getString(10);
				transObject._totalFloor = cursor.getString(11);
				transObject._buildingType = cursor.getString(12);
				transObject._usingType = cursor.getString(13);
				transObject._mainMaterial = cursor.getString(14);
				transObject._buildingPublishDate = cursor.getString(15);
				transObject._buildingSize = cursor.getString(16);
				transObject._buildingStatusAndPattern_Room = cursor.getString(17);
				transObject._buildingStatusAndPattern_LivingRoom = cursor.getString(18);
				transObject._buildingStatusAndPattern_Bathroom = cursor.getString(19);
				transObject._buildingStatusAndPattern_Dividingroom = cursor.getString(20);
				transObject._isOrganization =  cursor.getString(21);
				transObject._totalPrice = cursor.getString(23);
				transObject._priceOfOneArea = cursor.getString(24);
				transObject._parkingType = cursor.getString(25);
				transObject._parkingTransArea = cursor.getString(26);
				transObject._parkingPrice = cursor.getString(27);
				transObject.setFurniture( cursor.getString(22));
				transObject.setXMLBuildingType(cursor.getString(28));
				transObject.setLAT(cursor.getString(29));
				transObject.setLON(cursor.getString(30));
				
				// Adding contact to list
				transObjectList.add(transObject);
			} while (cursor.moveToNext());
		}

		// return contact list
		return transObjectList;
	}


	public List<TransactionBuilding>  getTransactionBuildingbyTransType(String transType) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<TransactionBuilding> transObjectList = new ArrayList<TransactionBuilding>();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_district, KEY_transactionObject,KEY_address, 
				KEY_areaSize,KEY_areaUseType,KEY_nonAreaUseType,KEY_nonAreaUseSetting,KEY_transactionDate
				,KEY_transactionBuilding,KEY_transLayer,KEY_totalFloor,KEY_buildingType,KEY_usingType
				,KEY_mainMaterial,KEY_buildingPublishDate,KEY_buildingSize,KEY_buildingStatusAndPattern_Room
				,KEY_buildingStatusAndPattern_LivingRoom,KEY_buildingStatusAndPattern_Bathroom
				,KEY_buildingStatusAndPattern_Dividingroom,KEY_isOrganization,KEY_furniture
				,KEY_totalPrice,KEY_priceOfOneArea,KEY_parkingType,KEY_parkingTransArea,KEY_parkingPrice
				,KEY_XMLBuildingType,KEY_LAT,KEY_LON
				
				}, KEY_XMLBuildingType + "=?",
				new String[] { transType }, null, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TransactionBuilding transObject = new TransactionBuilding();
				transObject._id = Integer.parseInt(cursor.getString(0));
				transObject._district = cursor.getString(1);
				transObject._transactionObject = cursor.getString(2);
				transObject._address = cursor.getString(3);
				transObject._areaSize = cursor.getString(4);
				transObject._areaUseType = cursor.getString(5);
				transObject._nonAreaUseType = cursor.getString(6);
				transObject._nonAreaUseSetting = cursor.getString(7);
				transObject._transactionDate = cursor.getString(8);
				transObject._transactionBuilding = cursor.getString(9);
				transObject._transLayer = cursor.getString(10);
				transObject._totalFloor = cursor.getString(11);
				transObject._buildingType = cursor.getString(12);
				transObject._usingType = cursor.getString(13);
				transObject._mainMaterial = cursor.getString(14);
				transObject._buildingPublishDate = cursor.getString(15);
				transObject._buildingSize = cursor.getString(16);
				transObject._buildingStatusAndPattern_Room = cursor.getString(17);
				transObject._buildingStatusAndPattern_LivingRoom = cursor.getString(18);
				transObject._buildingStatusAndPattern_Bathroom = cursor.getString(19);
				transObject._buildingStatusAndPattern_Dividingroom = cursor.getString(20);
				transObject._isOrganization =  cursor.getString(21);
				transObject._totalPrice = cursor.getString(23);
				transObject._priceOfOneArea = cursor.getString(24);
				transObject._parkingType = cursor.getString(25);
				transObject._parkingTransArea = cursor.getString(26);
				transObject._parkingPrice = cursor.getString(27);
				transObject.setFurniture( cursor.getString(22));
				transObject.setXMLBuildingType(cursor.getString(28));
				transObject.setLAT(cursor.getString(29));
				transObject.setLON(cursor.getString(30));
				
				// Adding contact to list
				transObjectList.add(transObject);
			} while (cursor.moveToNext());
		}

		// return contact list
		return transObjectList;
	}


	public List<TransactionBuilding>  getTransactionBuildingbyRange(String district,double centerLng,double centerLat,double range) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<TransactionBuilding> transObjectList = new ArrayList<TransactionBuilding>();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_district, KEY_transactionObject,KEY_address, 
				KEY_areaSize,KEY_areaUseType,KEY_nonAreaUseType,KEY_nonAreaUseSetting,KEY_transactionDate
				,KEY_transactionBuilding,KEY_transLayer,KEY_totalFloor,KEY_buildingType,KEY_usingType
				,KEY_mainMaterial,KEY_buildingPublishDate,KEY_buildingSize,KEY_buildingStatusAndPattern_Room
				,KEY_buildingStatusAndPattern_LivingRoom,KEY_buildingStatusAndPattern_Bathroom
				,KEY_buildingStatusAndPattern_Dividingroom,KEY_isOrganization,KEY_furniture
				,KEY_totalPrice,KEY_priceOfOneArea,KEY_parkingType,KEY_parkingTransArea,KEY_parkingPrice
				,KEY_XMLBuildingType,KEY_LAT,KEY_LON
				
				}, KEY_district + "=?",
				new String[] { district }, null, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TransactionBuilding transObject = new TransactionBuilding();
				transObject._id = Integer.parseInt(cursor.getString(0));
				transObject._district = cursor.getString(1);
				transObject._transactionObject = cursor.getString(2);
				transObject._address = cursor.getString(3);
				transObject._areaSize = cursor.getString(4);
				transObject._areaUseType = cursor.getString(5);
				transObject._nonAreaUseType = cursor.getString(6);
				transObject._nonAreaUseSetting = cursor.getString(7);
				transObject._transactionDate = cursor.getString(8);
				transObject._transactionBuilding = cursor.getString(9);
				transObject._transLayer = cursor.getString(10);
				transObject._totalFloor = cursor.getString(11);
				transObject._buildingType = cursor.getString(12);
				transObject._usingType = cursor.getString(13);
				transObject._mainMaterial = cursor.getString(14);
				transObject._buildingPublishDate = cursor.getString(15);
				transObject._buildingSize = cursor.getString(16);
				transObject._buildingStatusAndPattern_Room = cursor.getString(17);
				transObject._buildingStatusAndPattern_LivingRoom = cursor.getString(18);
				transObject._buildingStatusAndPattern_Bathroom = cursor.getString(19);
				transObject._buildingStatusAndPattern_Dividingroom = cursor.getString(20);
				transObject._isOrganization =  cursor.getString(21);
				transObject._totalPrice = cursor.getString(23);
				transObject._priceOfOneArea = cursor.getString(24);
				transObject._parkingType = cursor.getString(25);
				transObject._parkingTransArea = cursor.getString(26);
				transObject._parkingPrice = cursor.getString(27);
				transObject.setFurniture( cursor.getString(22));
				transObject.setXMLBuildingType(cursor.getString(28));
				transObject.setLAT(cursor.getString(29));
				transObject.setLON(cursor.getString(30));
				
				if(transObject._lat!=null&&!cursor.isNull(24)){
					double distance=GetDistance(centerLat,centerLng,Double.parseDouble(transObject._lat),Double.parseDouble(transObject._lon));
					
					if(distance<range)
					transObjectList.add(transObject);
				}
				
				//Value is null
			
				
			} while (cursor.moveToNext());
		}

		// return contact list
		return transObjectList;
	}
	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		float[] results = new float[1];
		Location.distanceBetween(lat1, lng1, lat2, lng2, results);
		return results[0] / 1000;
	}
	
	// Getting All Contacts
	public List<TransactionBuilding> getAllContacts() {
		List<TransactionBuilding> transObjectList = new ArrayList<TransactionBuilding>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TransactionBuilding transObject = new TransactionBuilding();
				transObject._id = Integer.parseInt(cursor.getString(0));
				transObject._district = cursor.getString(1);
				transObject._transactionObject = cursor.getString(2);
				transObject._address = cursor.getString(3);
				transObject._areaSize = cursor.getString(4);
				transObject._areaUseType = cursor.getString(5);
				transObject._nonAreaUseType = cursor.getString(6);
				transObject._nonAreaUseSetting = cursor.getString(7);
				transObject._transactionDate = cursor.getString(8);
				transObject._transactionBuilding = cursor.getString(9);
				transObject._transLayer = cursor.getString(10);
				transObject._totalFloor = cursor.getString(11);
				transObject._buildingType = cursor.getString(12);
				transObject._usingType = cursor.getString(13);
				transObject._mainMaterial = cursor.getString(14);
				transObject._buildingPublishDate = cursor.getString(15);
				transObject._buildingSize = cursor.getString(16);
				transObject._buildingStatusAndPattern_Room = cursor.getString(17);
				transObject._buildingStatusAndPattern_LivingRoom = cursor.getString(18);
				transObject._buildingStatusAndPattern_Bathroom = cursor.getString(19);
				transObject._buildingStatusAndPattern_Dividingroom = cursor.getString(20);
				transObject._isOrganization =  cursor.getString(21);
				transObject._totalPrice = cursor.getString(23);
				transObject._priceOfOneArea = cursor.getString(24);
				transObject._parkingType = cursor.getString(25);
				transObject._parkingTransArea = cursor.getString(26);
				transObject._parkingPrice = cursor.getString(27);
				transObject.setFurniture( cursor.getString(22));
				transObject.setXMLBuildingType(cursor.getString(28));
				transObject.setLAT(cursor.getString(29));
				transObject.setLON(cursor.getString(30));
				
				// Adding contact to list
				transObjectList.add(transObject);
			} while (cursor.moveToNext());
		}

		// return contact list
		return transObjectList;
	}

//	// Updating single contact
//	public int updateContact(Building contact) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_NAME, contact.getName());
//		values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//		// updating row
//		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//				new String[] { String.valueOf(contact.getID()) });
//	}

//	// Deleting single contact
//	public void deleteContact(Building contact) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//				new String[] { String.valueOf(contact.getID()) });
//		db.close();
//	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	
	

}
