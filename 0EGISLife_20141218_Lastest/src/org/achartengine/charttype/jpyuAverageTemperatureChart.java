/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.achartengine.charttype;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.basic.AbstractDemoChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class jpyuAverageTemperatureChart extends AbstractDemoChart {

	String[] titles = null;
	List<double[]> x = null;
	List<double[]> values = null;
	int[] colors = null;
	PointStyle[] styles = null;

	public void prepareData() {
		titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };
		x = new ArrayList<double[]>();
		for (int i = 0; i < titles.length; i++) {
			x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
		}
		values = new ArrayList<double[]>();
		values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4,
				26.1, 23.6, 20.3, 17.2, 13.9 });
		values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14,
				11 });
		values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });
		values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });

		colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
		styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
				PointStyle.TRIANGLE, PointStyle.SQUARE };
	}

	public XYMultipleSeriesRenderer getRender(Context context) {
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		setChartSettings(renderer, "撟喳�皞怠漲", "�� ","皞怠漲", 0.5, 12.5, -10, 40,
				Color.LTGRAY, Color.LTGRAY);
		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
	    //renderer.setApplyBackgroundColor(true);
	    //renderer.setBackgroundColor(Color.rgb(222, 222, 200));

		return renderer;
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public GraphicalView getChartView(Context context) {
		prepareData();
		XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
		XYMultipleSeriesRenderer renderer = getRender(context);
		GraphicalView mChartView;
		mChartView = ChartFactory.getLineChartView(context, dataset, renderer);
		return mChartView;
	}

	@Override
	public Intent execute(Context context) {
		prepareData();
		XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
		XYMultipleSeriesRenderer renderer = getRender(context);
		Intent intent = ChartFactory.getLineChartIntent(context, dataset,
				renderer, "Average temperature");
		return intent;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
