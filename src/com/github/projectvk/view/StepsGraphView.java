package com.github.projectvk.view;


/**
 * Created by Thijs on 18-1-2015.
 */

import com.github.projectvk.model.Statistics;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;
import com.xeiam.xchart.demo.charts.ExampleChart;

public class StepsGraphView implements ExampleChart {

    public StepsGraphView(){
        Chart chart = getChart();
        new SwingWrapper(chart).displayChart();
    }

    public Chart getChart() {

        // Retrieve all data from the Simulator class
        double[] foxes = Statistics.convertToGraphData(Statistics.fox_steps_history);
        double[] rabbits = Statistics.convertToGraphData(Statistics.rabbit_steps_history);
        double[] dodos = Statistics.convertToGraphData(Statistics.dodo_steps_history);

        // Put the turn steps in a double array
        double[] turns = new double[foxes.length];
        for (int i = 0; i < turns.length; i++){
            turns[i] = i;
        }

        // Create Chart
        Chart chart = new ChartBuilder().chartType(ChartType.Scatter).width(600).height(400).title(getClass().getSimpleName()).xAxisTitle("Stap").yAxisTitle("Aantallen").build();
        chart.addSeries("Rabbits", turns, rabbits);
        chart.addSeries("Foxes", turns, foxes);
        chart.addSeries("Dodo", turns, dodos);

        // Customize Chart
        chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyleManager().setAxisTitlesVisible(false);

        return chart;
    }

}