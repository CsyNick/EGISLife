package com.example.egislife;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;

import com.example.egislife.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public abstract class BaseDemoActivity extends FragmentActivity {
    private GoogleMap mMap;

    protected int getLayoutId() {
        return R.layout.basemap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap != null) {
            startDemo();
        }
    }

    /**
     * Run the demo-specific code.
     */
    protected abstract void startDemo();

    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }
}
