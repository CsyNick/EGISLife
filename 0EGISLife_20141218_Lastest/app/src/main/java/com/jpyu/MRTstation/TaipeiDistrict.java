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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;

import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.activity.Activity_Main;
import com.example.egislife.R;
import com.example.usaul.tool.ShowSomething;


import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

public class TaipeiDistrict {
	//chsy Modify
	static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";
	static final String SCALE_TOAST_STRING = "Scaled to: %.2ff";
	String district;
	private TextView mCurrMatrixTv;

	private PhotoViewAttacher mAttacher;

	ImageView mImageView = null;

	private Bitmap vBitmapCircle = null;
	private Bitmap vBitmapMRTstation = null;
	private MarkerList markerList;
	private MarkerList markerLifeScroeList;
	private MarkerList selectedMarkerList;
	private Activity activity = null;
	private HashMap<String, Taipei> hashmapTaipeiDistrict = null;

	public TaipeiDistrict(Activity activity, ImageView mImageView,
			TextView mCurrMatrixTv) {
		this.activity = activity;
		this.mImageView = mImageView;
		this.mCurrMatrixTv = mCurrMatrixTv;

	}

	public TaipeiDistrict() {
		// TODO Auto-generated constructor stub

	}
    public void initialize(){
    	   mAttacher = new PhotoViewAttacher(mImageView);
           // Lets attach some listeners, not required though!
           mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
           mAttacher.setOnPhotoTapListener(new PhotoTapListener());
       	// ========================================================
   		vBitmapCircle = BitmapFactory.decodeResource(activity.getResources(),
   				R.drawable.greencircle);
   		vBitmapMRTstation = BitmapFactory.decodeResource(
   				activity.getResources(), R.drawable.taipeidistrict);
    }
	public void initialize(String MRTstationBaseImageFile) {
//		MRTstationBaseImageFile = "mnt/sdcard/taipeidistrict.png";
//		mImageView.setImageURI(Uri.parse(MRTstationBaseImageFile));
		mImageView.setImageResource(R.drawable.taipeidistrict);
		// The MAGIC happens here!
		mAttacher = new PhotoViewAttacher(mImageView);
		// Lets attach some listeners, not required though!
		mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
		mAttacher.setOnPhotoTapListener(new PhotoTapListener());

		// ========================================================
		vBitmapCircle = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.greencircle);
		vBitmapMRTstation = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.taipeidistrict);

		String touchXYFile = "TaipeiDistrict/taipei.xml";
		String egisLifeScoreFile = "TaipeiDistrict/district.xml";
		String TaipeiDistrictInfoFile = "TaipeiDistrict/taipeidistrict.xml";
		// String MRTstationInfoFile = "mnt/sdcard/MRT01/MRT01.xml";
		markerLifeScroeList = new MarkerList();
		hashmapTaipeiDistrict = new HashMap<String, Taipei>();
		selectedMarkerList = new MarkerList();
		loadTouchXY(touchXYFile);
		loadTaipeiDistrictInfo(TaipeiDistrictInfoFile);
		loadLifeScore(egisLifeScoreFile);
		// drawAllGreenMarker(vBitmapCanvas, null);

		Bitmap bitmap = selectedStationImage(vBitmapMRTstation,
				null);
		if (bitmap != null) {
			// Matrix mSuppMatrix = mAttacher.getSuppMatrix();
			mImageView.setImageBitmap(bitmap);

			// mAttacher.mSuppMatrix.set(mSuppMatrix);
			Matrix mDrawMatrix = mAttacher.getDrawMatrix();
			mAttacher.setImageViewMatrix(mDrawMatrix);
			mAttacher.checkMatrixBounds();
		}
	}

	public CircleMarker addCircleMarker(Bitmap markerImage,
			int x_inSourceImage, int y_inSourceImage, String markerId) {
		if (markerList == null)
			markerList = new MarkerList();
		CircleMarker circleMarker = new CircleMarker(markerImage,
				x_inSourceImage, y_inSourceImage, markerId);
		markerList.put(circleMarker.markerId, circleMarker);
		return circleMarker;
	}
	public CircleMarker addLifeScoreMarker(Bitmap markerImage,
			int x_inSourceImage, int y_inSourceImage, String markerId) {
		if (markerLifeScroeList == null)
			markerLifeScroeList = new MarkerList();
		CircleMarker circleMarker = new CircleMarker(markerImage,
				x_inSourceImage, y_inSourceImage, markerId);
		markerLifeScroeList.put(circleMarker.markerId, circleMarker);
		return circleMarker;
	}
	public CircleMarker addCircleMarker(Bitmap markerImage,
			int x_inSourceImage, int y_inSourceImage) {
		if (markerList == null)
			markerList = new MarkerList();
		CircleMarker circleMarker = new CircleMarker(markerImage,
				x_inSourceImage, y_inSourceImage);
		markerList.put(circleMarker.markerId, circleMarker);
		return circleMarker;
	}

	public CircleMarker findSelectedMarkerInMapList(int imageX_picked,
			int imageY_picked) {
		if (markerList == null)
			return null;

		int currentMouseX_inSourceImage = imageX_picked;
		int currentMouseY_inSourceImage = imageY_picked;

		CircleMarker selectedCircleMarker = null;
		CircleMarker circleMarker = null;
		// -----------------------------------
		int num = markerList.size();
		if (num <= 0)
			return null;
		ListIterator it = markerList.iterator();
		while (it.hasNext()) {
			circleMarker = (CircleMarker) it.next();
			if (circleMarker.isPicked(currentMouseX_inSourceImage,
					currentMouseY_inSourceImage)) {
				selectedCircleMarker = circleMarker;
				Log.i("jpyuMsg", selectedCircleMarker.markerId + " is selected");
				break;
			}
			// selectedCircleMarker = circleMarker;
		}
		return selectedCircleMarker;
	}

	public void cleanup() {
		// Need to call clean-up
		mAttacher.cleanup();
	}

	private class PhotoTapListener implements OnPhotoTapListener {

		@Override
		public void onPhotoTap(View view, float x, float y) {

			Drawable d = mAttacher.getImageView().getDrawable();
			final int drawableWidth = d.getIntrinsicWidth();
			final int drawableHeight = d.getIntrinsicHeight();
			int imageX_picked = (int) (x * drawableWidth);
			int imageY_picked = (int) (y * drawableHeight);
			CircleMarker selectedCircleMarker = findSelectedMarkerInMapList(
					imageX_picked, imageY_picked);

			if (selectedCircleMarker != null)
				touchTaipeiDistrict(selectedCircleMarker.markerId);


		}

		private void touchTaipeiDistrict(String markerId) {
			// TODO Auto-generated method stub
             
			Taipei taipei = hashmapTaipeiDistrict.get(markerId);
			ShowSomething.showToast(activity, "已選擇"+taipei.District);
			Intent in = new Intent(activity, Activity_Main.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("Taipei", taipei);
			in.putExtras(bundle);
			activity.startActivity(in);

		}
	}

	private class MatrixChangeListener implements OnMatrixChangedListener {

		@Override
		public void onMatrixChanged(RectF rect) {
			if (mCurrMatrixTv != null)
				mCurrMatrixTv.setText(rect.toString());
		}
	}


	private final Rect textBounds = new Rect(); // don't new this up in a draw
												// method

	public void drawTextCentred(Canvas canvas, Paint paint, String text, int x,
			int y) {
		
		paint.getTextBounds(text, 0, text.length(), textBounds);
	
		canvas.drawText(text, x - textBounds.exactCenterX(),
				y - textBounds.exactCenterY(), paint);
	}

	public Bitmap selectedStationImage(Bitmap bitmap_base,
			CircleMarker selectedCircleMarker) {

		Bitmap vBitmap_new = Bitmap.createBitmap(bitmap_base.getWidth(),
				bitmap_base.getHeight(), Bitmap.Config.RGB_565);
		Canvas vBitmapCanvas = new Canvas(vBitmap_new);

		// =================================================

		Paint vPaintAlpha = new Paint();
		vPaintAlpha.setStyle(Paint.Style.STROKE);
		// vPaintAlpha.setAlpha(95); // Bitmap (0 ~ 100)

		vBitmapCanvas.drawColor(Color.WHITE);
		vBitmapCanvas.drawBitmap(bitmap_base, 0, 0, vPaintAlpha);
	//	drawAllGreenMarker(vBitmapCanvas, null);  //chsy Modify
		// drawSelectedMarker(vBitmapCanvas);

		// =================================================
		return vBitmap_new;
	}

	public void drawAllGreenMarker(Canvas vBitmapCanvas, Paint vPaintText) {
		if (vPaintText == null) {
			vPaintText = new Paint();
			vPaintText.setColor(Color.RED);// 0xff00ffff ); //
												// ���e���������C������
			// vPaintCircle.setStyle( Paint.Style.STROKE ); // ������������
			vPaintText.setStyle(Paint.Style.FILL); // ������������
			vPaintText.setAntiAlias(true); // ������������������
		}
        
		Paint paintCircle = new Paint();
		paintCircle.setColor(Color.GREEN);// 0xff00ffff ); //
											// ���e���������C������
		// vPaintCircle.setStyle( Paint.Style.STROKE ); // ������������
		paintCircle.setStyle(Paint.Style.FILL); // ������������
		paintCircle.setAntiAlias(true); // ������������������
		
		CircleMarker circleMarker = null;
		ListIterator it = markerLifeScroeList.iterator();
		while (it.hasNext()) {
			circleMarker = (CircleMarker) it.next();
			vBitmapCanvas.drawCircle(circleMarker.x_inSourceImage, circleMarker.y_inSourceImage, 15, paintCircle);
			drawTextCentred(vBitmapCanvas, vPaintText, String.valueOf((int) (Math.random() * 100 + 1)),
					circleMarker.x_inSourceImage, circleMarker.y_inSourceImage);
		}

	}

	void loadTouchXY(String xmlFileUrl) {

		if (markerList == null) {
			markerList = new MarkerList();
		} else {
			markerList.clear();
		}
		// =====================================================

		int readMode = 3;
		if (Info.isUseLocalXmlFiles) {

			readMode = 1;
			if (xmlFileUrl.contains("mnt/sdcard")) {
				readMode = 3;
			}
		} else {
			if (xmlFileUrl.contains("http://")) {
				readMode = 2;
			}
		}

		Log.e("jpyuMsg", "xmlFileUrl=" + xmlFileUrl);
		// =====================================================
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = null;

			switch (readMode) {
			case 1:
				InputStream inStream = TaipeiDistrict.class.getClassLoader()
						.getResourceAsStream(xmlFileUrl);
				document = builder.parse(inStream);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---local");
				break;
			case 2:
				document = builder.parse(xmlFileUrl);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---http");
				break;
			case 3:
				// Log.i("jpyuMsg: xmlFileUrl=", xmlFileUrl);
				File SDcardinStream = new File(xmlFileUrl);
				document = builder.parse(SDcardinStream);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---mnt/sdcard");
				break;
			}

			Element root = document.getDocumentElement();
			NodeList formatNodes = root.getElementsByTagName("format");
			if (formatNodes != null
					&& xmlFileUrl == "TaipeiDistrict/taipei.xml") {
				String formatStr = formatNodes.item(0).getFirstChild()
						.getNodeValue();

				Log.i("jpyuMsg", "formatStr=" + formatStr);
				if (formatStr == "v1.2") {
					parserFormatv12(document);

				} else {
					parserFormatv10(document);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("jpyuMsg", e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("jpyuMsg", e.toString());
		}
	}

	void loadLifeScore(String xmlFileUrl) {

		
		// =====================================================

		int readMode = 3;
		if (Info.isUseLocalXmlFiles) {

			readMode = 1;
			if (xmlFileUrl.contains("mnt/sdcard")) {
				readMode = 3;
			}
		} else {
			if (xmlFileUrl.contains("http://")) {
				readMode = 2;
			}
		}

		Log.e("jpyuMsg", "xmlFileUrl=" + xmlFileUrl);
		// =====================================================
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = null;

			switch (readMode) {
			case 1:
				InputStream inStream = TaipeiDistrict.class.getClassLoader()
						.getResourceAsStream(xmlFileUrl);
				document = builder.parse(inStream);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---local");
				break;
			case 2:
				document = builder.parse(xmlFileUrl);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---http");
				break;
			case 3:
				// Log.i("jpyuMsg: xmlFileUrl=", xmlFileUrl);
				File SDcardinStream = new File(xmlFileUrl);
				document = builder.parse(SDcardinStream);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---mnt/sdcard");
				break;
			}

			Element root = document.getDocumentElement();
			NodeList formatNodes = root.getElementsByTagName("format");
			if (formatNodes != null
					&& xmlFileUrl == "TaipeiDistrict/district.xml") {
			
				parserFormatv13(document);
			
			}

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("jpyuMsg", e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("jpyuMsg", e.toString());
		}
	}

	void loadTaipeiDistrictInfo(String xmlFileUrl) {

		int readMode = 3;
		if (Info.isUseLocalXmlFiles) {

			readMode = 1;
			if (xmlFileUrl.contains("mnt/sdcard")) {
				readMode = 3;
			}
		} else {
			if (xmlFileUrl.contains("http://")) {
				readMode = 2;
			}
		}

		Log.e("jpyuMsg", "xmlFileUrl=" + xmlFileUrl);
		// =====================================================
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = null;

			switch (readMode) {
			case 1:
				InputStream inStream = TaipeiDistrict.class.getClassLoader()
						.getResourceAsStream(xmlFileUrl);
				document = builder.parse(inStream);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---local");
				break;
			case 2:
				document = builder.parse(xmlFileUrl);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---http");
				break;
			case 3:
				// Log.i("jpyuMsg: xmlFileUrl=", xmlFileUrl);
				File SDcardinStream = new File(xmlFileUrl);
				document = builder.parse(SDcardinStream);
				Log.e("jpyuMsg", "readMode=" + readMode + " ---mnt/sdcard");
				break;
			}

			if (xmlFileUrl == "TaipeiDistrict/taipeidistrict.xml")
				parserTaipeiDistrict(document);

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("jpyuMsg", e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("jpyuMsg", e.toString());
		}
	}

	public void parserFormatv12(Document document) {

		String path = null;
		String id = null;
		int x = 0, y = 0;
		Element root = document.getDocumentElement();
		NodeList line_nodes = root.getElementsByTagName("line");
		for (int i = 0; i < line_nodes.getLength(); i++) {
			NodeList station_nodeList = line_nodes.item(i).getChildNodes();
			for (int eid = 0; eid < station_nodeList.getLength(); eid++) {
				Element stationElement = (Element) station_nodeList.item(i);
				// id = stationElement.getAttribute("id");
				id = XMLUtils.getChildStringValueForElement(stationElement,
						"id").trim();
				x = Integer.parseInt(XMLUtils.getChildStringValueForElement(
						stationElement, "x").trim());
				y = Integer.parseInt(XMLUtils.getChildStringValueForElement(
						stationElement, "y").trim());
				Log.i("jpyuMsg", "id=" + id);
				addCircleMarker(vBitmapCircle, x, y, id);
			}
		}

	}
	public void parserFormatv13(Document document) {

		String path = null;
		String id = null;
		int x = 0, y = 0;
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("station");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element stationElement = (Element) nodes.item(i);
			// id = stationElement.getAttribute("id");
			id = XMLUtils.getChildStringValueForElement(stationElement, "id")
					.trim();
			x = Integer.parseInt(XMLUtils.getChildStringValueForElement(
					stationElement, "x").trim());
			y = Integer.parseInt(XMLUtils.getChildStringValueForElement(
					stationElement, "y").trim());
			Log.i("jpyuMsg13", "id=" + id);
				addLifeScoreMarker(vBitmapCircle, x, y, id);
			}
		

	}
	public void parserFormatv10(Document document) {

		String path = null;
		String id = null;
		int x = 0, y = 0;
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("station");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element stationElement = (Element) nodes.item(i);
			// id = stationElement.getAttribute("id");
			id = XMLUtils.getChildStringValueForElement(stationElement, "id")
					.trim();
			x = Integer.parseInt(XMLUtils.getChildStringValueForElement(
					stationElement, "x").trim());
			y = Integer.parseInt(XMLUtils.getChildStringValueForElement(
					stationElement, "y").trim());
			Log.i("jpyuMsg10", "id=" + id);
			addCircleMarker(vBitmapCircle, x, y, id);
		}
	}

	public void parserTaipeiDistrict(Document document) {

		String id = null;
		String district = null;
		String pos = null;
		String[] latlng = null;
		;
		double lng = 0, lat = 0;
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("DATA");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element stationElement = (Element) nodes.item(i);
			// id = stationElement.getAttribute("id");
			id = stationElement.getAttribute("ID");
			district = stationElement.getAttribute("NA");
			pos = stationElement.getAttribute("POS");
			latlng = pos.split(",");
			lng = Double.parseDouble(latlng[0]);
			lat = Double.parseDouble(latlng[1]);
			Taipei taipei = new Taipei(id, district, lng, lat);
			Log.i("jpyuMsg", "id=" + id + district + lng + "," + lat);
			addTaipeiDistrictToHashmap(id, taipei);
		}
	}

	private void addTaipeiDistrictToHashmap(String id, Taipei taipei) {
		hashmapTaipeiDistrict.put(id, taipei);
		// TODO Auto-generated method stub

	}

}
