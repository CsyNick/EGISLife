package com.example.adapter;

import com.example.fragment.Fragment_Control;

import com.example.fragment.Map_Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Adapter_Pager extends FragmentPagerAdapter {
    public static Map_Fragment map_Fragment = null;
    public static Fragment_Control control_fragment = null;
	public Adapter_Pager(FragmentManager fm) {
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
			map_Fragment = new Map_Fragment();
			return map_Fragment;

		case 1:
			control_fragment = new Fragment_Control();
			return control_fragment;
		default:
			return null;
		}
	}

}
