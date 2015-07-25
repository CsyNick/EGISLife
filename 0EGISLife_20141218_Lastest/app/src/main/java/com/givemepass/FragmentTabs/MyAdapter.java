package com.givemepass.FragmentTabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A simple FragmentPagerAdapter that returns two TextFragment and a
 * SupportMapFragment.
 */
public  class MyAdapter extends FragmentPagerAdapter {
	public static NgisLayerFragment ngisLayerFragment;
	public static ArcgisMapFragment arcgisMapFragment;
	public MyAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			ngisLayerFragment =new NgisLayerFragment();
			return ngisLayerFragment;
			// break;
		case 1:
			arcgisMapFragment = new ArcgisMapFragment();
			return arcgisMapFragment;
	
		default:
			return null;
		}
	}
}