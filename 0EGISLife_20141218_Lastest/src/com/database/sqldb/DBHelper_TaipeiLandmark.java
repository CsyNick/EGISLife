package com.database.sqldb;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_TaipeiLandmark extends SQLiteOpenHelper {
	List<TaipeiLandmark> dataList = null;
	String[] class_mainArr = { "休閒購物", "美食佳餚", "政府機關", "藝術文化", "文教機構", "交通建設",
			"公用事業", "工商服務", "醫療保健" };
	String[] class_districtArr = { "北投區", " 中山區", "大同區", "萬華區", "中正區", "大安區",
			"士林區", "松山區", "內湖區", "信義區", "南港區", "文山區" };
	String[] class_middleArr = {};
	private static final String SQL_DBFile = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/testdb/"
			+ "citymark";
	private Cursor cursor = null;
	private static final String MAIN_DATA_TABLE_NAME = "citymark";// 表名。
	private static final String MAIN_DATA_ID = "col_1";// 表的4个字段
	private static final String MAIN_DATA_MARKNAME = "col_2";
	private static final String MAIN_DATA_DISTRICT = "col_3";
	private static final String MAIN_DATA_ADDRESS = "col_4";
	private static final String MAIN_DATA_X = "col_9";
	private static final String MAIN_DATA_Y = "col_10";
	private static final String MAIN_DATA_URL = "col_11";
	private static final String MAIN_DATA_VILLAGE = "col_16";
	private static final String MAIN_DATA_CLASS_MAIN = "col_17";
	private static final String MAIN_DATA_CLASS_MIDDLE = "col_18";
	private static final String MAIN_DATA_CLASS_LITTLE = "col_19";
	private static final String MAIN_DATA_CLASS_DETAIL = "col_20";

	public DBHelper_TaipeiLandmark(Context context) {
		super(context, SQL_DBFile, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param selectoin
	 *            1.休閒購物 2.美食佳餚 3.政府機關 4.藝術文化 5.文教機構 6.交通建設 7.公用事業 8.工商服務 9.醫療保健
	 * 
	 * @return Cursor
	 */
	public Cursor getClassMainData(int selectoin) {
		Cursor cursor = null;
		switch (selectoin) {
		case 1:
			cursor = getAllLeisureShop();
			break;
		case 2:
			cursor = getAllDeliciousFood();
			break;
		case 3:
			cursor = getAllGovernment();
			break;

		case 4:
			cursor = getAllArtCulture();
			break;
		case 5:
			cursor = getAllEductionInstitute();
			break;
		case 6:
			cursor = getAllTransportation();
			break;
		case 7:
			cursor = getALLPublicCareer();
			break;
		case 8:
			cursor = getCommerceIndustrial();
			break;

		case 9:
			cursor = getAllHospital();
			break;
		default:
			break;
		}

		return cursor;

	}
	/**
	 * 
	 * @param selectoin
	 *            1.休閒購物 2.美食佳餚 3.政府機關 4.藝術文化 5.文教機構 6.交通建設 7.公用事業 8.工商服務 9.醫療保健
	 * 
	 * @return Cursor
	 */
	public Cursor getClassMainDatabyDistrict(String district,int selectoin) {
		Cursor cursor = null;
	
			cursor = getInfoFromDBbyDistrictAndClassName(district,class_mainArr[selectoin]);
		

		return cursor;

	}
	private Cursor getAllHospital() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[8]);
		return cursor;
	}

	private Cursor getCommerceIndustrial() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[7]);
		return cursor;
	}

	private Cursor getALLPublicCareer() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[6]);
		return cursor;
	}

	private Cursor getAllTransportation() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[5]);
		return cursor;
	}

	private Cursor getAllEductionInstitute() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[4]);
		return cursor;
	}

	private Cursor getAllArtCulture() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[3]);
		return cursor;
	}

	private Cursor getAllGovernment() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[2]);
		return cursor;
	}

	private Cursor getAllDeliciousFood() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[1]);
		return cursor;
	}

	private Cursor getAllLeisureShop() {
		// TODO Auto-generated method stub
		cursor = getInfoFromDB(class_mainArr[0]);
		return cursor;
	}

	public Cursor getAllLandmarker(String district) {
		cursor = getInfoFromDBbyDistrict(district);
		return cursor;

	}

	private Cursor getInfoFromDB(String keyword) {
		File name = new File(SQL_DBFile);
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(name, null);
		return db.query(MAIN_DATA_TABLE_NAME, // 資料表名稱
				new String[] { MAIN_DATA_ID, MAIN_DATA_MARKNAME,
						MAIN_DATA_DISTRICT, MAIN_DATA_ADDRESS, MAIN_DATA_X,
						MAIN_DATA_Y, MAIN_DATA_URL, MAIN_DATA_VILLAGE,
						MAIN_DATA_CLASS_MAIN, MAIN_DATA_CLASS_MIDDLE,
						MAIN_DATA_CLASS_LITTLE, }, // 欄位名稱
				MAIN_DATA_CLASS_MAIN + "=\'" + keyword + "\'", // WHERE
				null, // WHERE 的參數
				null, // GROUP BY
				null, // HAVING
				null // ORDOR BY
				);

	}

	private Cursor getInfoFromDBbyDistrict(String keyword) {
		File name = new File(SQL_DBFile);
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(name, null);
		return db.query(MAIN_DATA_TABLE_NAME, // 資料表名稱
				new String[] { MAIN_DATA_ID, MAIN_DATA_MARKNAME,
						MAIN_DATA_DISTRICT, MAIN_DATA_ADDRESS, MAIN_DATA_X,
						MAIN_DATA_Y, MAIN_DATA_URL, MAIN_DATA_VILLAGE,
						MAIN_DATA_CLASS_MAIN, MAIN_DATA_CLASS_MIDDLE,
						MAIN_DATA_CLASS_LITTLE, }, // 欄位名稱
				MAIN_DATA_DISTRICT + "=\'" + keyword + "\'", // WHERE
				null, // WHERE 的參數
				null, // GROUP BY
				null, // HAVING
				null // ORDOR BY
				);

	}

	private Cursor getInfoFromDBbyDistrictAndClassName(String district, String class_name) {
		File name = new File(SQL_DBFile);
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(name, null);
		return db.query(MAIN_DATA_TABLE_NAME, // 資料表名稱
				new String[] { MAIN_DATA_ID, MAIN_DATA_MARKNAME,
						MAIN_DATA_DISTRICT, MAIN_DATA_ADDRESS, MAIN_DATA_X,
						MAIN_DATA_Y, MAIN_DATA_URL, MAIN_DATA_VILLAGE,
						MAIN_DATA_CLASS_MAIN, MAIN_DATA_CLASS_MIDDLE,
						MAIN_DATA_CLASS_LITTLE, }, // 欄位名稱
				MAIN_DATA_DISTRICT + "=\'" + district + "\'" + " and "
						+ MAIN_DATA_CLASS_MAIN + "=\'" + class_name + "\'", // WHERE
				null, // WHERE 的參數
				null, // GROUP BY
				null, // HAVING
				null // ORDOR BY
				);

	}
}
