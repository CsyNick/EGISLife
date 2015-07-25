package com.chsy.ngis.rss;

import java.util.List;

import com.example.egislife.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NgisRSSActivity extends Activity implements OnItemClickListener,OnClickListener {
	ProgressDialog progressDialog;
	String Tag = "NgisRSSActivity";
	static List<NgisEvent> ngisRSSList = null;

	private ListView listView;

	private FeedListAdapter listAdapter;
 
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.ngis_rss_mainlayout);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		listView = (ListView) findViewById(R.id.list);

		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		listView.setOnItemClickListener(this);

		if (ngisRSSList == null) {
			new Background().execute();
		} else {

			listAdapter = new FeedListAdapter(NgisRSSActivity.this, ngisRSSList,listView);
			listView.setAdapter(listAdapter);
		}

	}

	public class Background extends AsyncTask<String, String, String> {

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

			NgisRSSParser parser = new NgisRSSParser();
			ngisRSSList = parser.getInfo();

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d(Tag, "Finish");

			listAdapter = new FeedListAdapter(NgisRSSActivity.this, ngisRSSList,listView);
			listView.setAdapter(listAdapter);
			progressDialog.dismiss();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse(ngisRSSList.get(position).getUrl_link());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
