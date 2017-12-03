package user_and_manager.chenhao.com.user_and_manager.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;

/**
 * Created by chenhao on 2017/3/26.
 */

public class ACharView
{
    public static int[] Colors = {
            Color.RED, Color.BLUE,
            Color.GRAY, Color.MAGENTA,
            Color.DKGRAY, Color.YELLOW,
            Color.LTGRAY, Color.RED,
            Color.BLUE, Color.GRAY,
            Color.MAGENTA, Color.DKGRAY,
            Color.YELLOW, Color.LTGRAY,
            Color.RED, Color.BLUE,
            Color.GRAY, Color.MAGENTA,
            Color.DKGRAY, Color.YELLOW,
            Color.LTGRAY, Color.RED,
            Color.BLUE, Color.GRAY,
            Color.MAGENTA, Color.DKGRAY,
            Color.YELLOW, Color.LTGRAY,
            Color.RED, Color.BLUE,
            Color.GRAY, Color.MAGENTA,
            Color.DKGRAY, Color.YELLOW,
            Color.LTGRAY, Color.RED,
            Color.BLUE, Color.GRAY,
            Color.MAGENTA, Color.DKGRAY,
            Color.YELLOW, Color.LTGRAY};

    public static View PieChart(Context context, HashMap<String, List<VioLationItem>> hashMap)
    {
        Log.e("ACharView", " hashMap.size(): " + hashMap.size());
        DefaultRenderer mRenderer = new DefaultRenderer();// PieChart的主要描绘器

        mRenderer.setZoomButtonsVisible(true);// 显示放大缩小功能按钮
        mRenderer.setStartAngle(180);// 设置为水平开始
        mRenderer.setDisplayValues(true);// 显示数据

        mRenderer.setFitLegend(true);// 设置是否显示图例
        mRenderer.setLegendTextSize(40);// 设置图例字体大小
        mRenderer.setLegendHeight(10);// 设置图例高度

        mRenderer.setChartTitle("饼图示例");// 设置饼图标题
        mRenderer.setChartTitleTextSize(14);// 设置饼图标题大小
        mRenderer.setDisplayValues(true);
        CategorySeries mSeries = new CategorySeries("统计");

        int i = 0;
        for (Map.Entry<String, List<VioLationItem>> entry : hashMap.entrySet())
        {
            String key = entry.getKey().toString();
            mSeries.add(key, hashMap.get(key).size());
            Log.e("ACharView", " hashMap.Values---size(): " + hashMap.get(key).size());
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(Colors[i++]);
            mRenderer.addSeriesRenderer(renderer);
        }

        return ChartFactory.getPieChartView(context, mSeries, mRenderer);// 构建mChartView
//        mRenderer.setClickEnabled(true);// 允许点击事件

    }


    /******************************************************************/
    public static GraphicalView xychar(Context contexts, HashMap<String, Integer>
            hashMap, String title)
    {
        hashMap.put("测试数据", 100);
        Log.e("CCC", "result:200 ");
        //多个渲染
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        //多个序列的数据集
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        //构建数据集以及渲染
        double Xmax = 10, Ymax = 0;
        int i = 1;
        for (Map.Entry<String, Integer> entry : hashMap.entrySet())
        {

            String key = entry.getKey();
            Integer tempMoney = hashMap.get(key);

            XYSeries series = new XYSeries(key);

            if (Ymax < tempMoney)
                Ymax = tempMoney;

            series.add(i++, tempMoney);
            dataset.addSeries(series);
            XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
            xyRenderer.setDisplayChartValues(true);
            xyRenderer.setChartValuesTextSize(25);
            // 设置颜色
            xyRenderer.setColor(Colors[i % 20]);
            // 设置点的样式 //
            xyRenderer.setPointStyle(PointStyle.SQUARE);
            // 将要绘制的点添加到坐标绘制中
            renderer.addSeriesRenderer(xyRenderer);
        }


        renderer.setZoomEnabled(true, false);
        renderer.setChartTitleTextSize(50);
        //设置x轴标签数
        renderer.setXLabels(10);
        renderer.setDisplayValues(true);
        //设置Y轴标签数
        renderer.setYLabels(10);
        //设置x轴的最大值
        renderer.setXAxisMax(hashMap.size());
        //设置轴的颜色
        renderer.setAxesColor(Color.BLACK);
        //设置x轴和y轴的标签对齐方式
        renderer.setXLabelsAlign(Paint.Align.CENTER);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        // 设置现实网格
        renderer.setShowGrid(true);

        renderer.setShowAxes(true);
        // 设置条形图之间的距离/
        renderer.setBarSpacing(10);
        ///renderer.setInScroll(false);
        renderer.setPanEnabled(true, false);
//		renderer.setClickEnabled(false);
        //设置x轴和y轴标签的颜色
        renderer.setXLabelsColor(Color.RED);
        renderer.setYLabelsColor(0, Color.RED);
        int length = renderer.getSeriesRendererCount();
        //设置图标的标题
        renderer.setChartTitle(title);
        renderer.setLabelsColor(Color.RED);
        renderer.setBarWidth(20);
        //设置图例的字体大小
        renderer.setLegendTextSize(30);
        //设置x轴和y轴的最大最小值
        renderer.setXAxisMin(0);
        renderer.setYAxisMin(0);
        renderer.setXAxisMax(hashMap.size() + (hashMap.size() / 2));
        renderer.setYAxisMax(Ymax + (Ymax / 3));

        renderer.setMarginsColor(0x00888888);
        for (int x = 0; x < length; x++)
        {
            SimpleSeriesRenderer ssr = renderer.getSeriesRendererAt(x);
//			ssr.setChartValuesTextAlign(Align.RIGHT);
//			ssr.setChartValuesTextSize(12);
//			ssr.setDisplayChartValues(f);
        }

        return ChartFactory.getBarChartView(contexts,
                dataset, renderer, BarChart.Type.DEFAULT);

    }
}
