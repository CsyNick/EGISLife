package com.example.adapter;

import java.util.List;

import com.database.sqldb.TaipeiLandmark;
import com.example.egislife.R;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import android.widget.ListView;
import android.widget.TextView;

public class List_Adapter extends BaseAdapter {

	private Activity activity;
	private List<TaipeiLandmark> data;
	private static LayoutInflater inflater = null;

	ListView listView;
	static CheckBox checkBox;

	public List_Adapter(Activity a, List<TaipeiLandmark> d, ListView listView) {
		this.listView = listView;
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int index = position;
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.list_item_taipeidistrict, null);
		}
		TaipeiLandmark taipeiLandmark = data.get(index);
		TextView title = (TextView) vi.findViewById(R.id.taipeiLandmarkTitle); // title
		TextView addr = (TextView) vi.findViewById(R.id.taipeiLandmarkAddress); // artist
																				// name
		title.setText(taipeiLandmark.getMarkName());
		addr.setText(taipeiLandmark.getAddress());

		return vi;
	}
}
