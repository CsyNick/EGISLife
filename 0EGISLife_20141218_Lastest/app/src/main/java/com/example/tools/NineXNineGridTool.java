package com.example.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import com.example.activity.Activity_Main;
import com.example.fragment.Map_Fragment;
import com.example.object.NineGridItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

public class NineXNineGridTool {
	GoogleMap googleMap;
	Activity context;
	double lat;
	double lng;
	private List<NineGridItem> ninePositionList = new ArrayList<NineGridItem>();
	public final double MARGIN = 0.001;
	public final double LNGSHIFT = 0.01 + MARGIN;
	public final double LATSHIFT = 0.009 + MARGIN;
	public NineGridItem nineGridItem;
	public Bitmap vBitmapCircle;
	ImageTool tool;

	public NineXNineGridTool(GoogleMap googleMap, Activity context) {
		// TODO Auto-generated constructor stub
		this.googleMap = googleMap;
		this.context = context;
	}

	public void showNineXNineGridLayerAtMapCenter() {
		Map_Fragment.t2b.setVisibility(View.INVISIBLE);
		Map_Fragment.ViewTypeGallery.setVisibility(View.INVISIBLE);
		CameraPosition cameraPosition = googleMap.getCameraPosition();
		double lat = cameraPosition.target.latitude;
		double lon = cameraPosition.target.longitude;
		showNineXNineGridLayer(lat, lon);
	}

	public void showNineXNineGridLayer() {
		createNineXNineGridLayer();
	}

	public void showNineXNineGridLayer(int lifescore, double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
		setNineGridPoistion(lat, lng);
		createNineXNineGridLayer(lifescore);
	}

	public void showNineXNineGridLayer(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
		setNineGridPoistion(lat, lng);
		createNineXNineGridLayer();
	}

	public void initLocation(double lat, double lng) {
		// TODO Auto-generated method stub
		this.lat = lat;
		this.lng = lng;
		setNineGridPoistion(lat, lng);
	}

	public void setNineGridPoistion(double lat, double lng) {
		ninePositionList.clear();
		// center
		// ninePositionList.add(new NineGridItem(lat + LATSHIFT, lng));
		ninePositionList.add(new NineGridItem(lat, lng));
		// ninePositionList.add(new NineGridItem(lat - LATSHIFT, lng));

		// left
		// ninePositionList.add(new NineGridItem(lat + LATSHIFT, lng -
		// LNGSHIFT));
		// ninePositionList.add(new NineGridItem(lat, lng - LNGSHIFT));
		// ninePositionList.add(new NineGridItem(lat - LATSHIFT, lng -
		// LNGSHIFT));

		// right
		// ninePositionList.add(new NineGridItem(lat + LATSHIFT, lng +
		// LNGSHIFT));
		// ninePositionList.add(new NineGridItem(lat, lng + LNGSHIFT));
		// ninePositionList.add(new NineGridItem(lat - LATSHIFT, lng +
		// LNGSHIFT));

	}

	

	public void createNineXNineGridLayer(int lifescore) {
		googleMap.clear();

		CameraUpdate center = CameraUpdateFactory
				.newLatLng(new LatLng(lat, lng));

		CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
		double lat = 0.0;
		double lng = 0.0;
         int color=0;
	

			lat = ninePositionList.get(0).lat;
			lng = ninePositionList.get(0).lng;
			switch (lifescore) {
			case 1:
				color =0x9053585f;
				break;
			case 2:
				color =0x90a6aaa9;
				break;
			case 3:
				color =0x900465c0;
				break;
			case 4:
				color =0x9051a7f9;
				break;
			case 5:
				color =0x90dcbd23;
				break;
			case 6:
				color =0x90f5d328;
				break;
			case 7:
				color =0x90de6a10;
				break;
			case 8:
				color =0x90f39019;
				break;
			case 9:
				color =0x90c82506;
				break;
			case 10:
				color =0x90ec5d57;
				break;
			default:
				break;
			}
			
			CircleOptions circleOptions = new CircleOptions()
					.center(new LatLng(lat, lng))
					.fillColor(color)
					.strokeColor(Color.BLUE).strokeWidth(5)
					.radius(Activity_Main.range * 1000); // In meters default

			googleMap.addCircle(circleOptions);
			// googleMap.addCircle(circlePosition);
			Bitmap bitmapx = drawTextImage(String.valueOf(lifescore));
			setGroundOverLay(bitmapx, lat, lng, true);
		
		googleMap.moveCamera(center);
		googleMap.animateCamera(zoom);
	}

	public void createNineXNineGridLayer() {
		googleMap.clear();
		Random ran = new Random();
		CameraUpdate center = CameraUpdateFactory
				.newLatLng(new LatLng(lat, lng));

		CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
		double lat = 0.0;
		double lng = 0.0;

		for (int i = 0; i < ninePositionList.size(); i++) {

			lat = ninePositionList.get(i).lat;
			lng = ninePositionList.get(i).lng;

			CircleOptions circleOptions = new CircleOptions()
					.center(new LatLng(lat, lng)).fillColor(0x40ff0000)
					// semi-transparent
					.strokeColor(Color.BLUE).strokeWidth(5)
					.radius(Activity_Main.range * 1000); // In meters default

			googleMap.addCircle(circleOptions);

			Bitmap bitmapx = drawTextImage(String.valueOf(ran.nextInt(10) + 1));
			setGroundOverLay(bitmapx, lat, lng, true);
		}
		googleMap.moveCamera(center);
		googleMap.animateCamera(zoom);
	}

	private final Rect textBounds = new Rect(); // don't new this up in a draw
												// method

	public Bitmap drawTextImage(String text) {
		Bitmap vBitmap_new = Bitmap.createBitmap(144, 144,
				Bitmap.Config.ALPHA_8);
		// 144, 144, Bitmap.Config.RGB_565);
		Canvas vBitmapCanvas = new Canvas(vBitmap_new);
		int centerx = 144 / 2;
		int centery = 144 / 2;
		// =================================================
		// 建立 Paint 物件
		Paint vPaintAlpha = new Paint();
		vPaintAlpha.setStyle(Paint.Style.STROKE); // 空心
		vPaintAlpha.setAlpha(60); // Bitmap 透明度 (0 ~ 100)

		// Bitmap vBitmapCircle =
		// BitmapFactory.decodeResource(context.getResources()
		// , R.drawable.green_svg144x144);
		// vBitmapCanvas.drawBitmap( vBitmapCircle, 0, 0, vPaintAlpha); // 有透明

		// vBitmapCanvas.drawColor(Color.WHITE);
		Paint vPaintText = new Paint();
		vPaintText.setColor(Color.RED);// 0xff00ffff ); // 畫筆顏色
		vPaintText.setStyle(Paint.Style.STROKE); // 空心
		vPaintText.setStyle(Paint.Style.FILL); // 空心
		vPaintText.setAntiAlias(true); // 反鋸齒
		vPaintText.setTextSize(50f);

		vPaintText.getTextBounds(text, 0, text.length(), textBounds);
		vBitmapCanvas.drawText(text, centerx - textBounds.exactCenterX(),
				centery - textBounds.exactCenterY(), vPaintText);

		// =================================================
		return vBitmap_new;
	}

	public void setGroundOverLay(Bitmap bitmap, double lat, double lng,
			boolean isVisible) {

		LatLng DEFAULFPLACE = new LatLng(lat, lng);

		BitmapDescriptor image = BitmapDescriptorFactory.fromBitmap(bitmap);

		GroundOverlayOptions newarkMap = new GroundOverlayOptions()
				.image(image).transparency(0.3f)
				.position(DEFAULFPLACE, 1000f, 1000f);
		GroundOverlay mGroundOverlay = googleMap.addGroundOverlay(newarkMap);
		mGroundOverlay.setVisible(isVisible);

	}

	public void showNineXNineGridLayerAtMapCenter(int calculateLifeScore) {
		// TODO Auto-generated method stub
		Map_Fragment.t2b.setVisibility(View.INVISIBLE);
		Map_Fragment.ViewTypeGallery.setVisibility(View.INVISIBLE);
		CameraPosition cameraPosition = googleMap.getCameraPosition();
		double lat = cameraPosition.target.latitude;
		double lon = cameraPosition.target.longitude;
		showNineXNineGridLayer(calculateLifeScore, lat, lon);
	}

	

}
