package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import com.github.projectvk.model.*;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

@SuppressWarnings("serial")
public class GraphView extends JPanel{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private int height;
    private Controller controller;
    private JButton birthsStat, deathsStat,stepsStat;
    private JLabel currentStep;
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
        // Show current step
        currentStep = new JLabel("currentStep");
        jStyle.headerStyle(currentStep, this, 20, 455, 100, 30, new Color(161, 161, 161),14);

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

    public void updateSteps(int newStep){
        currentStep.setText("Step: " + newStep);
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

    // This methods produces an double array which contains the stepvalue for the x-axis of the graph
    public double[] calculateTurns(){
        System.out.println("Current: " + controller.getCurrentSteps() + " | Max: " + (int)controller.getMaxTurns());

        // When there has been more than HISTORY_TURNS steps, only show the last HISTORYTURNS entries
        if(controller.getCurrentSteps() >= (int)controller.getMaxTurns()) {
            double[] turns_temp = new double[(int)controller.getMaxTurns()];

            int currentValue = controller.getCurrentSteps() - (int)controller.getMaxTurns();

            for(int i = 0; i < controller.getMaxTurns(); i++){
                turns_temp[i] = currentValue;
                currentValue++;
            }
            return turns_temp;

        } else {
            // When we're still below HISTORY_TURNS, show turns from 0 to current stepvalue
            double[] turns_temp = new double[controller.getCurrentSteps() + 1];

            for (int i = 0; i < turns_temp.length; i++){
                turns_temp[i] = i;
            }

            return turns_temp;
        }
    }

    public Chart getChart() {

        // Put the turn steps in a double array
        double[] turns = calculateTurns();

        if(charType.equals("deaths")) {
            // Create Chart
            chart = new ChartBuilder().chartType(StyleManager.ChartType.Line).width(600).height(400).title("Deaths").xAxisTitle("Step").yAxisTitle("Amount").build();
            chart.addSeries("Rabbits", turns, controller.convertToGraphData(controller.getHistory("deathsHistory").get(Rabbit.class)));
            chart.addSeries("Foxes", turns, controller.convertToGraphData(controller.getHistory("deathsHistory").get(Fox.class)));
            chart.addSeries("Dodo", turns, controller.convertToGraphData(controller.getHistory("deathsHistory").get(Dodo.class)));
        }

        if(charType.equals("steps")) {
            // Create Chart
            chart = new ChartBuilder().chartType(StyleManager.ChartType.Bar).width(600).height(400).title("Steps").xAxisTitle("Step").yAxisTitle("Amount").build();
            chart.addSeries("Rabbits", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Rabbit.class)));
            chart.addSeries("Foxes", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Fox.class)));
            chart.addSeries("Dodo", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Dodo.class)));
            chart.addSeries("Hunter", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Hunter.class)));
        }

        if(charType.equals("births")) {
            // Create Chart
            chart = new ChartBuilder().chartType(StyleManager.ChartType.Scatter).width(600).height(400).title("Births").xAxisTitle("Step").yAxisTitle("Amount").build();
            chart.addSeries("Rabbits", turns, controller.convertToGraphData(controller.getHistory("birthsHistory").get(Rabbit.class)));
            chart.addSeries("Foxes", turns, controller.convertToGraphData(controller.getHistory("birthsHistory").get(Fox.class)));
            chart.addSeries("Dodo", turns, controller.convertToGraphData(controller.getHistory("birthsHistory").get(Dodo.class)));
        }

        return chart;
    }

    public void drawChart(String charType){
        this.charType = charType;
        this.chart = getChart();
        this.remove(4);
        this.add(new XChartPanel(chart), BorderLayout.NORTH);
        updateSteps(controller.getCurrentSteps());
    }
}