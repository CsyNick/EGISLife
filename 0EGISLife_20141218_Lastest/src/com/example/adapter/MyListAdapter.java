package com.example.adapter;

import java.util.ArrayList;

import com.example.egislife.R;
import com.example.object.MyData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<MyData>  {

	private ArrayList<MyData> items;
	private Context context;

	public MyListAdapter(Context context, int textViewResourceId,
			ArrayList<MyData> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row, null);
		}

		MyData myData = items.get(position);
		if (myData != null) {
			TextView title = (TextView) v.findViewById(R.id.title);
			TextView address = (TextView) v.findViewById(R.id.address);
			ImageView imgV = (ImageView) v.findViewById(R.id.icon);
			if (title != null) {
				title.setText(myData.getText());
				// put the id to identify the item clicked
				title.setTag(myData.getId());
			//	title.setOnClickListener(this);
			}
			if (address != null) {
				address.setText(myData.getAddress());

			}
			if(imgV!=null){
				imgV.setImageDrawable(myData.getDrawable());
				
			}
		}
		return v;
	}


}