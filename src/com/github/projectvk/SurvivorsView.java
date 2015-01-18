package com.github.projectvk;


/**
 * Created by Thijs on 18-1-2015.
 */

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;
import com.xeiam.xchart.demo.charts.ExampleChart;


public class SurvivorsView implements ExampleChart {

    public SurvivorsView(){
        System.out.println("test");
        Chart chart = getChart();
        new SwingWrapper(chart).displayChart();
    }

    public Chart getChart() {
        double []  rabbits = { 7, 10, 8, 15, 12 };
        double []  foxes = { 8, 4, 6, 6, 8 };
        double []  hunters = { 0, 5, 2, 3, 5 };

        // Create Chart
        Chart chart = new ChartBuilder().chartType(ChartType.Area).width(600).height(400).title(getClass().getSimpleName()).xAxisTitle("Stap").yAxisTitle("Aantallen").build();
        chart.addSeries("Vossen", new double[] { 0, 3, 5, 7, 9 }, foxes);
        chart.addSeries("Konijnen", new double[] { 0, 3, 5, 7, 9 }, rabbits);
        chart.addSeries("Jagers", new double[] { 0, 1, 3, 8, 9 }, hunters);

        // Customize Chart
        chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyleManager().setAxisTitlesVisible(false);

        return chart;
    }

}