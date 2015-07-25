package org.achartengine.basic;

import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.charttype.jpyuBudgetPieChart;
import com.example.egislife.R;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class LifeScoreTools {

	Activity activity = null;
	View dialog_view = null;
	LinearLayout layout = null;
	GraphicalView mChartView = null;
	public LifeScoreTools(Activity activity) {
		this.activity = activity;

		layout = (LinearLayout) activity.findViewById(R.id.tempchart);
	
	}

	public void createChart() {
		createChartTypeSpinner();
	
	}

	private void createChartTypeSpinner() {
		jpyuBudgetPieChart myjpyuBudgetPieChart = new jpyuBudgetPieChart();

		mChartView = myjpyuBudgetPieChart
				.getChartView(activity);
		layout.removeAllViews();
		layout.addView(mChartView);
		// ===================================================================
	}

	public void setData(List<DataSet> changeByControl_ALL) {
		// TODO Auto-generated method stub
		GraphicalView mChartView = null;
		jpyuBudgetPieChart myjpyuBudgetPieChart = new jpyuBudgetPieChart();
		myjpyuBudgetPieChart.prepareData();
		myjpyuBudgetPieChart.prepareData_v2(changeByControl_ALL);
		mChartView = myjpyuBudgetPieChart.getChartViewChange(activity);
		layout.removeAllViews();
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

	}

}
