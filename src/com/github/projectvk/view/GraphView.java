package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import com.github.projectvk.model.*;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.XChartPanel;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("serial")
public class GraphView extends JPanel{

    private final int GRID_VIEW_SCALING_FACTOR = 7;

    private int height;
    private Controller controller;
    private JButton birthsStat, deathsStat, stepsStat, lineStatButton, scatterStatButton, barStatButton;
    private JLabel currentStep;
    private JStyle jStyle;
    private Chart chart;
    private String dataSource = "deathsHistory";
    private String dataChartType = "line";
    private String charType = "births";
    private String headerTitle = "Births";

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
        this.chart = getChart(dataChartType, dataSource);
        this.add(new XChartPanel(chart), BorderLayout.NORTH);
    }

    public String getCharType(){
        return charType;
    }

    public void setHeaderTitle(String headerTitle){
        this.headerTitle = headerTitle;
    }

    public void makeGUI(){
        // Show current step
        currentStep = new JLabel("currentStep");
        jStyle.headerStyle(currentStep, this, 20, 455, 100, 30, new Color(161, 161, 161),14);

        // Births button
        birthsStat = new JButton("Births");
        jStyle.buttonStyle(birthsStat, "birthsStat",controller, this, 109, 404, 80, 30);

        // Deaths button
        deathsStat = new JButton("Deaths");

        jStyle.buttonStyle(deathsStat, "deathsStat",controller, this, 209, 404, 80, 30);

        // Steps button
        stepsStat = new JButton("Steps");
        jStyle.buttonStyle(stepsStat, "stepsStat",controller, this, 309, 404, 80, 30);

        //Line Button
        lineStatButton = new JButton("Line");
        jStyle.buttonStyle(lineStatButton, "drawLine",controller, this, 109, 444, 80, 30);

        //Scatter Button
        scatterStatButton = new JButton("Scatter");
        jStyle.buttonStyle(scatterStatButton, "drawScatter",controller, this, 209, 444, 80, 30);

        //Bar Button
        barStatButton = new JButton("Bar");
        jStyle.buttonStyle(barStatButton, "drawBar",controller, this, 309, 444, 80, 30);
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

    /**
     * Returns a DataChartType ( eg Line ) based on given string
     * @param dataChartType String -> line, bar, scatter
     * @return StyleManager.ChartType
     */
    public StyleManager.ChartType getChartType(String dataChartType){
        switch (dataChartType){
            case "line":
                return StyleManager.ChartType.Line;
            case "bar":
                return StyleManager.ChartType.Bar;
            case "scatter":
                return StyleManager.ChartType.Scatter;
            default:
                return StyleManager.ChartType.Bar;

        }
    }

    /**
     * Returns a Hashmap with animals and corresponding animal.class
     * @return class
     */
    private HashMap<String, Class> getAnimals(){
        HashMap<String, Class> hashMapGetAnimals = new HashMap<String, Class>();
        hashMapGetAnimals.put("Rabbits", Rabbit.class);
        hashMapGetAnimals.put("Foxes", Fox.class);
        hashMapGetAnimals.put("Dodo", Dodo.class);

        return hashMapGetAnimals;
    }

    /**
     * Sets DataSource
     * @param source - deathsHistory, birthsHistory, stepsHistory
     */
    public void setDataSource(String source){
        this.dataSource = source;
    }

    public void setDataChartType(String type){
        this.dataChartType = type;
    }

    public String getDataChartType(){
        return dataChartType;
    }

    /**
     * Returns a Chart
     * @param dataChartType
     * @return
     */
    public Chart getChart(String dataChartType, String dataSource) {

        // Put the turn steps in a double array
        double[] turns = calculateTurns();

        //if(charType.equals()) {
            // Create Chart
            chart = new ChartBuilder().chartType(getChartType(dataChartType)).width(600).height(400).title(headerTitle).xAxisTitle("Step").yAxisTitle("Amount").build();

            Iterator it = getAnimals().keySet().iterator();
            while (it.hasNext()){
                String key = (String)it.next();
                System.out.println(key);
                chart.addSeries(key , turns, controller.convertToGraphData(controller.getHistory(dataSource).get(getAnimals().get((key)))));

            }
       // }

        /*if(charType.equals("steps")) {
            // Create Chart
            chart = new ChartBuilder().chartType(StyleManager.ChartType.Bar).width(600).height(400).title("Steps").xAxisTitle("Step").yAxisTitle("Amount").build();
            chart.addSeries("Rabbits", turns, controller.convertToGraphData(controller.getHistory("stepsHistory").get(Rabbit.class)));
            chart.addSeries("Foxes", turns, controller.convertToGraphData(controller.getHistory("birthsHistory").get(Fox.class)));
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
*/
        return chart;
    }

    public void drawChart(String charType){
        this.charType = charType;
        this.chart = getChart(dataChartType, dataSource);
        this.remove(4);
        this.add(new XChartPanel(chart), BorderLayout.NORTH);
        updateSteps(controller.getCurrentSteps());
    }
}