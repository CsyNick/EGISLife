package com.example.adapter;

import java.util.HashMap;

import com.example.egislife.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class GridAdapter  extends ArrayAdapter implements CompoundButton.OnCheckedChangeListener {
	private Context _con;
	private String[] _itemString;
	private Drawable[] _itemDrawables;
	private int[] _itemIcons;
	 public SparseBooleanArray mCheckStates;
	private LayoutInflater mInflater;
	public GridAdapter(Context con, String[] itemString, int[] itemIcons) {
		super(con,0,itemString);
		_con = con;
		_itemString = itemString;
		_itemIcons = itemIcons;
		_itemDrawables = null;
		  mInflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
	}
	
	public GridAdapter(Context con, String[] itemString,
			Drawable[] itemDrawables) {
		super(con,0,itemString);
		_con = con;
		_itemString = itemString;
		_itemIcons = null;
		_itemDrawables = itemDrawables;
		 mInflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public GridAdapter(Context con, String[] lifeScoreString) {
		super(con,0,lifeScoreString);
	       mCheckStates = new SparseBooleanArray(lifeScoreString.length);
		_con = con;
		_itemString = lifeScoreString;
		_itemIcons = null;
		_itemDrawables = null;
		 mInflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return _itemString.length;
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
    	
        return _itemString[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub

        return position;
    }
    
    public HashMap<String, Integer> getClassMainKey(){
    	HashMap<String, Integer> hashMapKey =new HashMap<String, Integer>();
    	
    	for(int i=0;i<_itemString.length;i++){
    		//Log.i("HashMap", _itemString[i]+i);
    		hashMapKey.put(_itemString[i], i);
    	}
		return hashMapKey;
    	
    }
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder vh;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.info_item, parent, false);
			vh = new ViewHolder();
			vh.cb = (CheckBox) convertView.findViewById(R.id.item_checkBox);
			vh.cb.setTag(position);
			vh.cb.setText(_itemString[position]);
			
			vh.cb.setChecked(mCheckStates.get(position, false));
	 
			vh.cb.setOnCheckedChangeListener(this);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	   public boolean isChecked(int position) {
           return mCheckStates.get(position, false);
       }

       public void setChecked(int position, boolean isChecked) {
           mCheckStates.put(position, isChecked);

       }

       public void toggle(int position) {
           setChecked(position, !isChecked(position));

       }
	class ViewHolder {
		CheckBox cb;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		 mCheckStates.put((Integer) buttonView.getTag(), isChecked);   
	}

}
