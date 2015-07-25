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
import org.achartengine.chart.BarChart;
import org.achartengine.chart.BubbleChart;
import org.achartengine.chart.CubicLineChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class jpyuCombinedTemperatureChart extends AbstractDemoChart {

	String[] titles = null;
	List<double[]> x = null;
	List<double[]> values = null;
	int[] colors = null;
	PointStyle[] styles = null;
	String[] types = null;
	
	XYValueSeries sunSeries = null;
	XYSeries waterSeries = null;
	XYMultipleSeriesDataset dataset = null;

	public void prepareData() 
	{
	    titles = new String[] { "Crete Air Temperature", "Skiathos Air Temperature" };
	    
	    x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
	      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
	    }
	   
	    values = new ArrayList<double[]>();
	    values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
	        13.9 });
	    values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
	    
	    colors = new int[] { Color.GREEN, Color.rgb(200, 150, 0) };
	    styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
	    
	    sunSeries = new XYValueSeries("Sunshine hours");
	    sunSeries.add(1, 35, 4.3);
	    sunSeries.add(2, 35, 4.9);
	    sunSeries.add(3, 35, 5.9);
	    sunSeries.add(4, 35, 8.8);
	    sunSeries.add(5, 35, 10.8);
	    sunSeries.add(6, 35, 11.9);
	    sunSeries.add(7, 35, 13.6);
	    sunSeries.add(8, 35, 12.8);
	    sunSeries.add(9, 35, 11.4);
	    sunSeries.add(10, 35, 9.5);
	    sunSeries.add(11, 35, 7.5);
	    sunSeries.add(12, 35, 5.5);

	    waterSeries = new XYSeries("Water Temperature");
	    waterSeries.add(1, 16);
	    waterSeries.add(2, 15);
	    waterSeries.add(3, 16);
	    waterSeries.add(4, 17);
	    waterSeries.add(5, 20);
	    waterSeries.add(6, 23);
	    waterSeries.add(7, 25);
	    waterSeries.add(8, 25.5);
	    waterSeries.add(9, 26.5);
	    waterSeries.add(10, 24);
	    waterSeries.add(11, 22);
	    waterSeries.add(12, 18);

		dataset = buildDataset(titles, x, values);
	    dataset.addSeries(0, sunSeries);
	    dataset.addSeries(0, waterSeries);
	    
	    types = new String[] { BarChart.TYPE, BubbleChart.TYPE, LineChart.TYPE,
	            CubicLineChart.TYPE };
	}

	public XYMultipleSeriesRenderer getRender(Context context) 
	{
	    XYSeriesRenderer lightRenderer = new XYSeriesRenderer();
	    lightRenderer.setColor(Color.YELLOW);
	    
	    XYSeriesRenderer waterRenderer = new XYSeriesRenderer();
	    waterRenderer.setColor(Color.argb(250, 0, 210, 250));
	    waterRenderer.setDisplayChartValues(true);
	    waterRenderer.setChartValuesTextSize(10);
	    
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
	    renderer.setPointSize(5.5f);
	    int length = renderer.getSeriesRendererCount();

	    for (int i = 0; i < length; i++) {
	      XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
	      r.setLineWidth(5);
	      r.setFillPoints(true);
	    }
	    setChartSettings(renderer, "Weather data", "Month", "Temperature", 0.5, 12.5, 0, 40,
	        Color.LTGRAY, Color.LTGRAY);

	    renderer.setXLabels(12);
	    renderer.setYLabels(10);
	    renderer.setShowGrid(true);
	    renderer.setXLabelsAlign(Align.RIGHT);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
	    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
	    renderer.setBarSpacing(0.5);
	    
	    renderer.addSeriesRenderer(0, lightRenderer);
	    renderer.addSeriesRenderer(0, waterRenderer);

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
		XYMultipleSeriesRenderer renderer = getRender(context);
			    
		GraphicalView mChartView;
		mChartView = ChartFactory.getCombinedXYChartView(context, dataset, renderer, types);

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