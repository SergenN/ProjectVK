package com.github.projectvk.view;


/**
 * Created by Thijs on 18-1-2015.
 */

import com.github.projectvk.model.Simulator;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;
import com.xeiam.xchart.demo.charts.ExampleChart;

import java.util.List;


public class BirthsGraphView implements ExampleChart {


    private List<Double> rabbitsList = Simulator.births;

    public BirthsGraphView(){
        Chart chart = getChart();
        new SwingWrapper(chart).displayChart();
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