package com.alfred.egis_googleearth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chsy.ngis.rss.NgisRSSActivity;
import com.example.tools.LocationTool;
import com.givemepass.FragmentTabs.ArcgisActivity;
import com.google.earth.viewer.ThreeDViewerActivity;
import com.jpyu.MRTstation.TaipeiActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class EGISLife_MainActivity extends Activity {
	String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/testdb"; // db folder
	private static final String TAG = "EGISLife_MainActivity";

	private static final boolean DEBUG = false;

	private static void DEBUG_LOG(String msg) {
		if (DEBUG) {
			Log.v(TAG, msg);
		}
	}

	private ProgressDialog progressDialog;

	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.egis_mainlayout);

		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);



		new LoadData().execute();

	}

	public void onLifeScore(View view) {
		intent = new Intent(EGISLife_MainActivity.this, TaipeiActivity.class);
		startActivity(intent);
	}

	public void onThreeDViewer(View view){
		intent =new Intent(EGISLife_MainActivity.this, ThreeDViewerActivity.class);
		 startActivity(intent);
	}
	public void onArcGIS(View view){
		 intent =new Intent(EGISLife_MainActivity.this,ArcgisActivity.class);
		 startActivity(intent);
	}
	public void onEgisRss(View view){
		 intent =new Intent(EGISLife_MainActivity.this,NgisRSSActivity.class);
		 startActivity(intent);
	}
	public class LoadData extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("資料讀取中...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			LocationTool location = new LocationTool(EGISLife_MainActivity.this);
			EGISLife_Data.MyLocation = location.getMyLocation();
			testDBAssets();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			progressDialog.dismiss();
		}

	}

	public Boolean testDBAssets() {

		try {

			String DATABASE_FILENAME = "citymark"; // 文件名
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/testdb目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/testdb目录中不存在
			// test.db文件，则从asset\db目录中复制这个文件到
			// SD卡的目录（/sdcard/testdb）
			if (!(new File(databaseFilename)).exists()) {
				// 获得封装testDatabase.db文件的InputStream对象
				AssetManager asset = getAssets();
				InputStream is = asset.open("db/citymark");
				DEBUG_LOG(is.toString());

				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[4096];
				int count = 0;
				// 开始复制testDatabase.db文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				asset.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// deleteDatabase("testDatabase.db");//删除数据库
		return true;
	}

	

}
