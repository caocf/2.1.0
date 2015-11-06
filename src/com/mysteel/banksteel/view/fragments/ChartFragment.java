package com.mysteel.banksteel.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.ResourceManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.PriceTrendData;
import com.mysteel.banksteel.entity.SearchResourceData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.DateUtil;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.IResourceManagerView;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 趋势图
 * @author 作者 wushaoge
 * @date 创建时间：2015年7月28日19:01:21
 */
public class ChartFragment extends BaseFragment implements IResourceManagerView
{

	private TextView tv_title;
	private TextView tv_nodata;
	private LineChartView chart;
	private LineChartData data;

	private ResourceManagerImpl resourceManagerImpl;

	private int numberOfLines = 1;
//	private int maxNumberOfLines = 4;
//	private int numberOfPoints = 12;
//	float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
	
	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private boolean hasLines = true;
	private boolean hasPoints = true;
	private ValueShape shape = ValueShape.CIRCLE;
	private boolean isFilled = false;
	private boolean hasLabels = true;
	private boolean isCubic = false;
	private boolean hasLabelForSelected = false;
	
	public String[] days = new String[] { "10:00", "12:00", "14:00", "15:00", "17:00", "19:00", "21:00"};
	public float[] nums = new float[] { 20, 70, 60, 40, 65, 10, 90};

	private SearchResourceData.Data.Datas datas;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View currentView = inflater.inflate(R.layout.fragment_chart, container, false);
        initView(currentView);
        return currentView;
    }

	private void initView(View currentView) {

		tv_title = (TextView) currentView.findViewById(R.id.tv_title);
		tv_nodata = (TextView) currentView.findViewById(R.id.tv_nodata);
		
		chart = (LineChartView) currentView.findViewById(R.id.chart);
		chart.setOnValueTouchListener(new ValueTouchListener());
		chart.setZoomType(ZoomType.HORIZONTAL);
		chart.setZoomEnabled(false);

		//默认显示无数据
		tv_title.setVisibility(View.GONE);
		tv_nodata.setVisibility(View.VISIBLE);
		chart.setVisibility(View.GONE);

		Bundle bundle = getArguments();
		datas = (SearchResourceData.Data.Datas)bundle.getSerializable("chartBundle");
		//generateValues();
		Map<String,String> map = new HashMap<String, String>();
		map.put("categoryName", datas.getBreedName());
		map.put("spec", datas.getSpec());
		map.put("brand", datas.getBrand());
		map.put("warehouseCity", datas.getWarehouse());
		String dateFrom = DateUtil.getFormatDate("yyyyMMdd", DateUtil.dateAdd(3, -7, new Date()));
		String dateTo = DateUtil.getFormatDate("yyyyMMdd",DateUtil.dateAdd(3, -1, new Date()));
		map.put("orderDateForm", dateFrom);
		map.put("orderDateTo", dateTo);

		resourceManagerImpl = new ResourceManagerImpl(getActivity(),this);
		String url = RequestUrl.getInstance(getActivity()).getUrl_PriceTrend(getActivity(), map);
		LogUtils.e(url);
		resourceManagerImpl.getPriceTrend(url, Constants.INTERFACE_createPriceTrend);

		//先把X轴Y轴初始化
		/*initXY();
		tv_title.setVisibility(View.VISIBLE);
		tv_nodata.setVisibility(View.GONE);
		chart.setVisibility(View.VISIBLE);

		generateValues();
		for(int i = 0;i<days.length;i++){
			LogUtils.e(days[i]+"");
		}
		for(int i = 0;i<nums.length;i++){
			LogUtils.e(nums[i]+"");
		}

		generateData();
		chart.setViewportCalculationEnabled(false);
		Arrays.sort(nums);
		resetViewport(nums[nums.length-1]);*/

	}


	private void showChatDatas(){
		tv_title.setVisibility(View.VISIBLE);
		tv_nodata.setVisibility(View.GONE);
		chart.setVisibility(View.VISIBLE);

		for(int i = 0;i<days.length;i++){
			//LogUtils.e(days[i]+"");
		}
		for(int i = 0;i<nums.length;i++){
			//LogUtils.e(nums[i]+"");
		}
		generateData();
		chart.setViewportCalculationEnabled(false);
		//Arrays.sort(nums);
		int max=0;
		for(int i=0;i<nums.length;i++){
			if(nums[i]>nums[max]){
				max=i;
			}
		}
		resetViewport(nums[max]);
	}


	/**
	 * 先把X轴Y轴初始化
	 */
	private void initXY(){
		for(int i = 0;i<nums.length;i++){
			nums[i] = 50+i;
		}
		for(int i = 0;i<days.length;i++){
			days[i] = DateUtil.getFormatDate("dd日",DateUtil.dateAdd(3,i-7,new Date()));
		}

	}
	
	/**
	 * 测试数据
	 */
	private void generateValues() {
		/*for (int i = 0; i < maxNumberOfLines; ++i) {
			for (int j = 0; j < numberOfPoints; ++j) {
				randomNumbersTab[i][j] = (float) Math.random() * 100f;
			}
		}*/
		//测试
		days = new String[] { "10:00", "12:00", "14:00", "15:00", "17:00", "19:00", "21:00"};
		nums = new float[] { 20, 70, 60, 40, 65, 10, 90};
		nums[0] = 99.5f;
		nums[1] = 2349.78f;
		nums[2] = -1;
		nums[3] = -1;
		nums[4] = 2100.0f;
		nums[5] = -1;
		nums[6] = -1;
		//nums[4] = 0;

	}
	
	
	private void generateData() {
		List<Line> lines = new ArrayList<Line>();

		List<PointValue> values = new ArrayList<PointValue>();

		for (int j = 0; j < nums.length; j++) {
			if(nums[j]>=0){
				PointValue val = new PointValue(j, nums[j]);
				String lable = nums[j]+"";
				val.setLabel(lable.toCharArray());
				values.add(val);
			}
			else{
//				values.add(new PointValue(j, nums[j]));
			}
		}

		Line line = new Line(values);
		line.setColor(Color.parseColor("#ff5555"));
		line.setShape(shape);
		line.setCubic(isCubic);
		line.setFilled(isFilled);
		line.setHasLabels(hasLabels);
		line.setHasLabelsOnlyForSelected(hasLabelForSelected);
		line.setHasLines(hasLines);
		line.setHasPoints(hasPoints);
		lines.add(line);

		data = new LineChartData(lines);
		
		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("Axis X");
			}
			
			data.setAxisXBottom(axisX);
			data.setAxisYLeft(axisY);
			
			List<AxisValue> axisValues = new ArrayList<AxisValue>();
			for (int j = 0; j < days.length; j++) {
				axisValues.add(new AxisValue(j, days[j].toCharArray()));
			}
			data.setAxisXBottom(new Axis(axisValues).setTextSize(12).setHasLines(true));
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}

		data.setBaseValue(Float.NEGATIVE_INFINITY);
		chart.setLineChartData(data);

	}
	
	private void resetViewport(float max) {
		// Reset viewport height range to (0,100)
		final Viewport v = new Viewport(chart.getMaximumViewport());
		v.bottom = 0;
		v.top = max+500;
		chart.setMaximumViewport(v);
		chart.setCurrentViewport(v, false);
		chart.setValueSelectionEnabled(hasLabelForSelected);
	}

	@Override
	public void matchResource(SearchResourceData data) {

	}

	@Override
	public void searchResource(SearchResourceData data) {

	}

	@Override
	public void updateView(BaseData data) {
		PriceTrendData priceTrendData = (PriceTrendData)data;
		if(priceTrendData!=null&&priceTrendData.getData()!=null&&priceTrendData.getData().getDatas()!=null&&priceTrendData.getData().getDatas().size()>0){
			List<PriceTrendData.Data.Datas> listDats = priceTrendData.getData().getDatas();
			Collections.sort(listDats, new Comparator<PriceTrendData.Data.Datas>() {
				public int compare(PriceTrendData.Data.Datas arg0, PriceTrendData.Data.Datas arg1) {
					if(DateUtil.dateDiff(3,DateUtil.getDate("yyyyMMdd",arg0.getOrderDate()),DateUtil.getDate("yyyyMMdd",arg1.getOrderDate()))>0){
						return -1;
					}else{
						return 1;
					}
				}
			});

			days = new String[listDats.size()];
			nums = new float[listDats.size()];
			for(int i = 0;i<listDats.size();i++){
				days[i] = DateUtil.getFormatDate("dd日", DateUtil.getDate("yyyyMMdd", priceTrendData.getData().getDatas().get(i).getOrderDate()));
				if(!TextUtils.isEmpty(priceTrendData.getData().getDatas().get(i).getAvgPrice())){
					nums[i] = Float.parseFloat(priceTrendData.getData().getDatas().get(i).getAvgPrice());
				}else{
					nums[i] = -1;
				}
			}
			showChatDatas();
		}else{
			//Tools.showToast(getActivity(),"暂无数据!");
//			generateValues();
//			generateData();
			tv_title.setVisibility(View.GONE);
			tv_nodata.setVisibility(View.VISIBLE);
			chart.setVisibility(View.GONE);
		}
	}

	@Override
	public void isShowDialog(boolean flag) {

	}


	private class ValueTouchListener implements LineChartView.LineChartOnValueTouchListener {

		@Override
		public void onValueTouched(int selectedLine, int selectedValue, PointValue value) {
			//Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onNothingTouched() {

		}

	}
    
    
    
}
