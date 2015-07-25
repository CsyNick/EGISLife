package com.chsy.ngis.rss;

import java.util.List;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.egislife.R;
import com.example.imgfromurl.ImageLoader;

public class FeedListAdapter extends BaseAdapter {
	public Context context;
	private LayoutInflater inflater;
	private List<NgisEvent> feedItems;
	ListView listview;

	 class ViewHolder {
		TextView name,publishDate;
		ImageView feedImageView;
        Button btn_Map;
	}

	public FeedListAdapter(Context context, List<NgisEvent> feedItems,ListView listview) {
		this.context = context;
		this.feedItems = feedItems;
		this.listview = listview;
		Log.i("FeedListAdapter", "Doing");
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView( int pos, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.ngis_rssfeed_item, null);
			vh = new ViewHolder();
			vh.name = (TextView) convertView.findViewById(R.id.name);
			vh.feedImageView = (ImageView) convertView
					.findViewById(R.id.imgBnbPhoto);
			vh.publishDate = (TextView) convertView.findViewById(R.id.txtpublishDate);
			vh.btn_Map = (Button) convertView.findViewById(R.id.btn_map);
			vh.btn_Map.setOnClickListener(mBuyButtonClickListener);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
        
		Log.i("FeedListAdapter", pos+"");
		NgisEvent item = (NgisEvent) getItem(pos);
		String[] date =item.getPubData().split(" ");
		
		vh.publishDate.setText(date[3]+" "+date[2]+" "+date[1]);
		vh.name.setText(item.getTitle());
		String image_url =  item.getImage();
		ImageLoader imgLoader = new ImageLoader(context);
		imgLoader.DisplayImage(image_url, R.drawable.noimage, vh.feedImageView);

		return convertView;
	}
	private OnClickListener mBuyButtonClickListener = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	        final int position = listview.getPositionForView(v);
	        if (position != AdapterView.INVALID_POSITION) {
	        	NgisEvent item = (NgisEvent) getItem(position);
	        	Log.i("FeedListAdapter",item.getTitle());
	        }
	    }
	};

}
