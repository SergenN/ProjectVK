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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SurvivorsView implements ExampleChart {


    private List<Double> rabbitsList = new ArrayList<Double>();

    public SurvivorsView(){
        allData();
        Chart chart = getChart();
        new SwingWrapper(chart).displayChart();
    }

    public void allData(){
        for (int i = 0; i < 20; i++) {
            rabbitsList.add(32.0);
            rabbitsList.add(22.0);
            rabbitsList.add(42.0);
            rabbitsList.add(32.0);
            rabbitsList.add(23.0);
        }
    }

    public Chart getChart() {
        double[] turns = new double[rabbitsList.size()];
        double[] rabbits = new double[rabbitsList.size()];
        double []  foxes = { 8, 4, 6, 6, 8 };
        double []  hunters = { 0, 5, 2, 3, 5 };

        // Sum of turns
        for (int i = 0; i < turns.length; i++){
            turns[i] = i;
        }

        // Fill Rabbit Data
        for (int i = 0; i < rabbits.length; i++){
            rabbits[i] = rabbitsList.get(i);
        }

        // Create Chart
        Chart chart = new ChartBuilder().chartType(ChartType.Area).width(600).height(400).title(getClass().getSimpleName()).xAxisTitle("Stap").yAxisTitle("Aantallen").build();
        chart.addSeries("Konijnen", turns, rabbits);

        // Customize Chart
        chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyleManager().setAxisTitlesVisible(false);

        return chart;
    }

}