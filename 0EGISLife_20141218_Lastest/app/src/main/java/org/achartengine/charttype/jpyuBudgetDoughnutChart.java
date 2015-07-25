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
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class jpyuBudgetDoughnutChart extends AbstractDemoChart {

	List<double[]> values = null;
	List<String[]> titles = null;
	int[] colors = null;
	MultipleCategorySeries categoryDataset = null;

	public void prepareData() 
	{
	    values = new ArrayList<double[]>();
	    values.add(new double[] { 12, 14, 11, 10, 19 });
	    values.add(new double[] { 10, 9, 14, 20, 11 });
	    
	    titles = new ArrayList<String[]>();
	    titles.add(new String[] { "P1", "P2", "P3", "P4", "P5" });
	    titles.add(new String[] { "Project1", "Project2", "Project3", "Project4", "Project5" });
	    
	    colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };

	    categoryDataset = buildMultipleCategoryDataset("Project budget", titles, values);

	}

	public DefaultRenderer getRender(Context context) 
	{   
	    DefaultRenderer renderer = buildCategoryRenderer(colors);
	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.rgb(222, 222, 200));
	    //renderer.setLabelsColor(Color.GRAY);
	    renderer.setLabelsColor(Color.BLACK);
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
		DefaultRenderer renderer = getRender(context);
		GraphicalView mChartView;
		mChartView = ChartFactory.getDoughnutChartView(context, categoryDataset, renderer);
		return mChartView;
	}

	@Override
	public Intent execute(Context context) {
		prepareData();
		DefaultRenderer renderer = getRender(context);
		GraphicalView mChartView;
		Intent intent = ChartFactory.getDoughnutChartIntent(context, categoryDataset, renderer
				, "Average temperature");
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
