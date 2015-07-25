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
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.basic.AbstractDemoChart;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Sales demo bar chart.
 */
public class jpyuBarChart extends AbstractDemoChart {

	List<double[]> values = null;
	String[] titles = null;
	int[] colors = null;
	XYMultipleSeriesDataset dataset = null;
	private static final int SERIES_NR = 2;

	public void prepareData() 
	{
	    titles = new String[] { "2007", "2008" };
	    
	    values = new ArrayList<double[]>();
	    values.add(new double[] { 5230, 7300, 9240, 10540, 7900, 9200, 12030, 11200, 9500, 10500,
	        11600, 13500 });
	    values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200, 22030, 21200, 19500, 15500,
	        12600, 14000 });
	    
	    colors = new int[] { Color.CYAN, Color.BLUE };

	    dataset = new XYMultipleSeriesDataset();
	    final int nr = 10;
	    Random r = new Random();
	    for (int i = 0; i < SERIES_NR; i++) {
	      CategorySeries series = new CategorySeries("Demo series " + (i + 1));
	      for (int k = 0; k < nr; k++) {
	        series.add(100 + r.nextInt() % 100);
	      }
	      dataset.addSeries(series.toXYSeries());
	    }
	}

	public XYMultipleSeriesRenderer getRender(Context context) 
	{ 
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] {20, 30, 15, 0});

	    renderer.setChartTitle("Chart demo");
	    renderer.setXTitle("x values");
	    renderer.setYTitle("y values");
	    renderer.setXAxisMin(0.5);
	    renderer.setXAxisMax(10.5);
	    renderer.setYAxisMin(0);
	    renderer.setYAxisMax(210);
	    
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(r);
	    r = new SimpleSeriesRenderer();
	    r.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(r);
	    return renderer;
	}
	
  public GraphicalView getChartView(Context context) 
  {
	  prepareData();
	  XYMultipleSeriesRenderer renderer = getRender(context);
	  GraphicalView mChartView;
	  mChartView = ChartFactory.getBarChartView(context, dataset, renderer, Type.DEFAULT);

	  return mChartView;
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  @Override
public Intent execute(Context context) {
	  prepareData();
	  XYMultipleSeriesRenderer renderer = getRender(context);
      return ChartFactory.getBarChartIntent(context, dataset, renderer,
        Type.DEFAULT);
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
