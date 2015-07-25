package com.example.egislife;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.example.egislife.R;
import com.example.tools.LocationTool;
import com.google.earth.viewer.ThreeDViewerActivity;
import com.jpyu.MRTstation.TaipeiActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity_HomeMenu extends Activity implements OnClickListener {
	String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/testdb"; // 将要存放于的文件夹
	private Button btnChooseDistrict;
	private Button btn3DEGISViewer;
	private Button btnMountainViewer;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_menu);
	
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);

		new LoadData().execute();

	}

	private void setView() {
		btnChooseDistrict = (Button) findViewById(R.id.btnChooseDistrict);
		btn3DEGISViewer = (Button) findViewById(R.id.btn3DEGISViewer);
		btnMountainViewer = (Button) findViewById(R.id.btnMountainViewer);
	
	}

	private void setBtnOnClickListener() {
		btnChooseDistrict.setOnClickListener(this);
		btn3DEGISViewer.setOnClickListener(this);
		btnMountainViewer.setOnClickListener(this);
		
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
			LocationTool location = new LocationTool(Activity_HomeMenu.this);
			EGISLife_Data.MyLocation = location.getMyLocation();
			testDBAssets();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setView();
			setBtnOnClickListener();
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
				Log.i("is", is.toString());
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btnChooseDistrict:
			 intent = new Intent(Activity_HomeMenu.this,
						TaipeiActivity.class);
				startActivity(intent);
			break;
		case R.id.btn3DEGISViewer:
			 intent =new Intent(Activity_HomeMenu.this, ThreeDViewerActivity.class);
			 startActivity(intent);
			 
			break;
		case R.id.btnMountainViewer:
		
			break;
	

		default:
			break;
		}
	}

}
