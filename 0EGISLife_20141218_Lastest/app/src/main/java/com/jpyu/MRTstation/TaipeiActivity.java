/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jpyu.MRTstation;

import com.example.egislife.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TaipeiActivity extends Activity {
    private TextView mCurrMatrixTv;
    ImageView mImageView = null;

	private TaipeiDistrict myMRTstation = null;
	

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
      		actionBar.setHomeButtonEnabled(true);

      		// Hide the action bar title
      		actionBar.setDisplayShowTitleEnabled(true);
      		actionBar.setDisplayHomeAsUpEnabled(true);
        mImageView = (ImageView) findViewById(R.id.iv_photo);
        mCurrMatrixTv = (TextView) findViewById(R.id.tv_current_matrix);
        mCurrMatrixTv.setVisibility(View.INVISIBLE);
        myMRTstation = new TaipeiDistrict(this, mImageView, mCurrMatrixTv);
        myMRTstation.initialize("file://mnt/sdcard/taipeidistrict.png");
         	            
    }
    
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Need to call clean-up
        myMRTstation.cleanup();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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

		

}

