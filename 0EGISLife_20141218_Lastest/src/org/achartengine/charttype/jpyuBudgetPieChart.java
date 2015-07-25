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

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.basic.AbstractDemoChart;
import org.achartengine.basic.DataSet;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class jpyuBudgetPieChart extends AbstractDemoChart {

	double[] values,vs = null;
	
	int[] colors ,init_colors= null;
	List<DataSet> changeByControl_ALL;
	public void prepareData() 
	{
		vs = new double[] { 12, 300, 29, 10, 500 };
	    values = new double[] { 12, 14, 11, 10, 19 };
	    colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN ,Color.RED,Color.DKGRAY,Color.BLACK,Color.TRANSPARENT};
	    init_colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };
	}
	

	public DefaultRenderer getRender(Context context) 
	{   
		DefaultRenderer renderer = buildCategoryRenderer(init_colors);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setZoomEnabled(true);

	    renderer.setLabelsTextSize(35f);
	    renderer.setLegendTextSize(40f);
		return renderer;
	}
	public DefaultRenderer getRender(Context context,List<DataSet> changeByControl_ALL) 
	{   
		DefaultRenderer renderer = buildCategoryRenderer(colors,changeByControl_ALL);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setZoomEnabled(true);

	    renderer.setLabelsTextSize(35f);
	    renderer.setLegendTextSize(40f);
		return renderer;
	}
	
	protected DefaultRenderer buildCategoryRenderer(int[] colors,List<DataSet> changeByControl_ALL) {
	    DefaultRenderer renderer = new DefaultRenderer();
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    for (int i=0;i<changeByControl_ALL.size();i++) {
	      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	      r.setColor(colors[i]);
	      renderer.addSeriesRenderer(r);
	    }
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
		CategorySeries categoryDataset = buildCategoryDataset("生活指標比例圖", values);

		DefaultRenderer renderer = getRender(context);
		GraphicalView mChartView;

		mChartView = ChartFactory.getPieChartView(context, categoryDataset, renderer);
		return mChartView;
	}
	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public GraphicalView getChartViewChange(Context context) {
		
		CategorySeries categoryDataset = buildCategoryDataset("生活指標比例圖", changeByControl_ALL);

		DefaultRenderer renderer = getRender(context,changeByControl_ALL);
		GraphicalView mChartView;

		mChartView = ChartFactory.getPieChartView(context, categoryDataset, renderer);
		return mChartView;
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

	@Override
	public Intent execute(Context context) {
	
		return null;
		
	}


	public void prepareData_v2(List<DataSet> _changeByControl_ALL) {
		// TODO Auto-generated method stub

		this.changeByControl_ALL =_changeByControl_ALL;
		
		
	}

}
