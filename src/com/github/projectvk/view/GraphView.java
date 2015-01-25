package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import com.github.projectvk.model.*;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GraphView extends JPanel{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private int height;
    private Controller controller;
    private JButton birthsStat, deathsStat,stepsStat;
    private JStyle jStyle;
    private Chart chart;

    private String charType = "births";

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     */
    public GraphView(int height, Controller controller) {
        this.setBackground(new Color(210, 210, 210));
        this.height = height;
        this.controller = controller;
        this.jStyle = controller.getJStyle();

        makeGUI();
        this.setLayout(new BorderLayout());
        this.chart = getChart();
        this.add(new XChartPanel(chart), BorderLayout.NORTH);
    }

    public String getCharType(){
        return charType;
    }

    public void makeGUI(){
        // Births button
        birthsStat = new JButton("Births");
        jStyle.buttonStyle(birthsStat, "birthsStat",controller, this, 129, 424, 80, 30);

        // Deaths button
        deathsStat = new JButton("Deaths");
        jStyle.buttonStyle(deathsStat, "deathsStat",controller, this, 229, 424, 80, 30);

        // Steps button
        stepsStat = new JButton("Steps");
        jStyle.buttonStyle(stepsStat, "stepsStat",controller, this, 329, 424, 80, 30);
    }

    /**
     * verkrijg de geprefereerde groote voor deze jPane
     *
     * @return preferred size of this jPane
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(600, height * GRID_VIEW_SCALING_FACTOR);
    }

    public Chart getChart() {
        // Put the turn steps in a double array
        double[] turns = new double[ controller.convertToGraphData(controller.getHistory("steps").get(Rabbit.class)).length];
//        if(Statistics.deathsHistory.size() > 70) {
//            for (int i = Statistics.deathsHistory.size(); i > Statistics.stepsHistory.get(Rabbit.class).size() - Statistics.HISTORY_TURNS; i--) {
//                turns[i] = i;
//                System.out.println(Statistics.stepsHistory.get(Rabbit.class).size() + "-" + Statistics.HISTORY_TURNS);
//            }
//        } else {
//            for (int i = 0; i < turns.length; i++){
//                turns[i] = i;
//
//            }
//        }

        for (int i = 0; i < turns.length; i++){
            turns[i] = i;

        }

        if(charType.equals("deaths")) {
            // Create Chart
            chart = new ChartBuilder().chartType(StyleManager.ChartType.Line).width(600).height(400).title("Deaths").xAxisTitle("Stap").yAxisTitle("Aantallen").build();
            chart.addSeries("Rabbits", turns, controller.convertToGraphData(controller.getHistory("deathsHistory").get(Rabbit.class)));
            chart.addSeries("Foxes", turns, controller.convertToGraphData(controller.getHistory("deathsHistory").get(Fox.class)));
            chart.addSeries("Dodo", turns, controller.convertToGraphData(controller.getHistory("deathsHistory").get(Dodo.class)));
        }

        if(charType.equals("steps")) {
            // Create Chart
            chart = new ChartBuilder().chartType(StyleManager.ChartType.Line).width(600).height(400).title("Steps").xAxisTitle("Stap").yAxisTitle("Aantallen").build();
            chart.addSeries("Rabbits", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Rabbit.class)));
            chart.addSeries("Foxes", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Fox.class)));
            chart.addSeries("Dodo", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Dodo.class)));
            chart.addSeries("Hunter", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Hunter.class)));
        }

        if(charType.equals("births")) {
            // Create Chart
            chart = new ChartBuilder().chartType(StyleManager.ChartType.Line).width(600).height(400).title("Births").xAxisTitle("Stap").yAxisTitle("Aantallen").build();
            chart.addSeries("Rabbits", turns, controller.convertToGraphData(controller.getHistory("birthsHistory").get(Rabbit.class)));
            chart.addSeries("Foxes", turns, controller.convertToGraphData(controller.getHistory("birthsHistory").get(Fox.class)));
            chart.addSeries("Dodo", turns, controller.convertToGraphData(controller.getHistory("birthsHistory").get(Dodo.class)));
        }

        return chart;
    }

    public void drawChart(String charType){
        this.charType = charType;
        this.chart = getChart();
        this.remove(3);
        this.add(new XChartPanel(chart), BorderLayout.NORTH);
    }
}