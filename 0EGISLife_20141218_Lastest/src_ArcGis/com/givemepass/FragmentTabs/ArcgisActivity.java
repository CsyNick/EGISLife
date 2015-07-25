package com.givemepass.FragmentTabs;

import com.example.egislife.R;



import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments.  It uses a trick (see the code below) to allow
 * the tabs to switch between fragments instead of simple views.
 */
public class ArcgisActivity extends FragmentActivity implements
ActionBar.TabListener, ActionBar.OnNavigationListener{

	private MyAdapter mAdapter;
	public static  ActionBar actionBar;
	private CustomViewPager mPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.arcgis_fragment_tabs);
        
        ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
      
		   
       

    }

	public void initView() {

		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (CustomViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		// This is required to avoid a black flash when the map is loaded. The
		// flash is due
		// to the use of a SurfaceView as the underlying view of the map.
		mPager.requestTransparentRegion(mPager);

		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		actionBar.addTab(actionBar.newTab().setText("圖資中心")
				.setTabListener(this), 0, true);
		actionBar.addTab(actionBar.newTab().setText("地圖")
				.setTabListener(this), 1, false);


		mPager.setSwipeable(false);
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int selectedIndex = getActionBar().getSelectedNavigationIndex();
			if (selectedIndex > 0) {
				getActionBar().setSelectedNavigationItem(0);
			} else {
				finish();
			}
		}
		return false;
	}
}