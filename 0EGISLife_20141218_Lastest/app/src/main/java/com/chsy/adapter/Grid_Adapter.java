package com.chsy.adapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.example.egislife.R;
import com.example.object.GridItem;


/***
 * ADAPTER
 */

public class Grid_Adapter extends ArrayAdapter<String> {

    private static final String TAG = "SampleAdapter";
    List<GridItem> gridItemList;
    static class ViewHolder {
    	TextView txtLineOne;
    
        DynamicHeightImageView imgView;
        Button btnGo;
        
    }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    private Context context;

    public Grid_Adapter(final Context context, final int textViewResourceId, List<GridItem> gridItemList) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.white);
        this.gridItemList= gridItemList;
        this.context=context;
    }
    
    public Grid_Adapter(final Context context, final int textViewResourceId, final String[] title, final String[] time) {
    	super(context, textViewResourceId);
    	mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.white);
     
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_threed, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (TextView) convertView.findViewById(R.id.txt_line1);
            vh.imgView = (DynamicHeightImageView) convertView.findViewById(R.id.staggerImgView);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);
        
        vh.imgView.setHeightRatio(positionHeight);
    
        setImage(gridItemList.get(position),vh.imgView);
        
        
        int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

        convertView.setBackgroundResource(mBackgroundColors.get(backgroundIndex));

        Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

        //vh.txtLineOne.setHeightRatio(1.5);
        vh.txtLineOne.setText(getItem(position));
       
        return convertView;
    }
    
    private void setImage(GridItem c, DynamicHeightImageView v){
    	
    	Drawable dr = context.getResources().getDrawable(c.getDrawable());
    
    	//Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
    	v.setImageDrawable(dr);
	}

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return 1.2; // height will be 1.0 - 1.5 the width
    }
}