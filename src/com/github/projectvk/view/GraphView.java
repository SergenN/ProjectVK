package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.XChartPanel;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.util.*;

@SuppressWarnings("serial")
public class GraphView extends JPanel{
    private final int GRID_VIEW_SCALING_FACTOR = 6;
    private int height;
    private Controller controller;
    private JButton birthsStat, deathsStat, stepsStat, lineStatButton, scatterStatButton, barStatButton;
    private JLabel currentStep;
    private JStyle jStyle;
    private Chart chart;
    private String dataSource = "stepsHistory";
    private String dataChartType = "bar";
    private String chartType = "steps";
    private String headerTitle = "Steps";
    private Map<Class, Color> colors;

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     */
    public GraphView(int height, Controller controller, Map<Class, Color> colors) {
        this.setBackground(new Color(210, 210, 210));
        this.height = height;
        this.controller = controller;
        this.jStyle = controller.getJStyle();
        this.colors = colors;

        makeGUI();
        this.setLayout(new BorderLayout());
        this.chart = getChart(dataChartType, dataSource);
        this.add(new XChartPanel(chart), BorderLayout.NORTH);
    }


    /**
     * Returns CharType
      * @return
     */
    public String getChartType(){
        return chartType;
    }

    /**
     * Changes the HeaderTitle
     * @param headerTitle
     */
    public void setHeaderTitle(String headerTitle){
        this.headerTitle = headerTitle;
    }

    /**
     * Creates the GUI
     */
    public void makeGUI(){
        // IMAGES
        ImageIcon lineIcon = new ImageIcon("img/line.png");
        ImageIcon barIcon = new ImageIcon("img/bar.png");
        ImageIcon scatterIcon = new ImageIcon("img/scatter.png");

        // Show current step
        currentStep = new JLabel("currentStep");
        jStyle.headerStyle(currentStep, this, 20, 455, 100, 30, new Color(161, 161, 161),14);

        // Births button
        birthsStat = new JButton("Births");
        jStyle.buttonStyle(birthsStat, "birthsStat",controller, this, 83, 404, 80, 30);

        // Deaths button
        deathsStat = new JButton("Deaths");

        jStyle.buttonStyle(deathsStat, "deathsStat",controller, this, 183, 404, 80, 30);

        // Steps button
        stepsStat = new JButton("Steps");
        jStyle.buttonStyle(stepsStat, "stepsStat",controller, this, 283, 404, 80, 30);

        // Alive button
        stepsStat = new JButton("Alive");
        jStyle.buttonStyle(stepsStat, "aliveStat",controller, this, 383, 404, 80, 30);

        //Line Button
        lineStatButton = new JButton("");
        jStyle.buttonStyle(lineStatButton, "drawLine",controller, this, 133, 445, 80, 30);
        lineStatButton.setIcon(lineIcon);

        //Scatter Button
        scatterStatButton = new JButton("");
        jStyle.buttonStyle(scatterStatButton, "drawScatter",controller, this, 233, 445, 80, 30);
        scatterStatButton.setIcon(scatterIcon);

        //Bar Button
        barStatButton = new JButton("");
        jStyle.buttonStyle(barStatButton, "drawBar",controller, this, 333, 445, 80, 30);
        barStatButton.setIcon(barIcon);
    }

    /**
     * Updates the amount of steps shown
     * @param newStep
     */
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

    /**
     * This methods produces an double array which contains the stepvalue for the x-axis of the graph
     * @return double[] turns
     */
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
    public StyleManager.ChartType getGraphChartType(String dataChartType){
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
     * Sets DataSource
     * @param source - deathsHistory, birthsHistory, stepsHistory
     */
    public void setDataSource(String source){
        this.dataSource = source;
    }

    /**
     * Sets the Datacharttype
     * @param type
     */
    public void setDataChartType(String type){
        this.dataChartType = type;
    }

    /**
     * Gets the Datacharttype
     * @return
     */
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



            // Create Chart
            chart = new ChartBuilder().chartType(getGraphChartType(dataChartType)).width(600).height(400).title(headerTitle).xAxisTitle("Step").yAxisTitle("Amount").build();

            Iterator it = controller.fetchClassDefinitions().keySet().iterator();
            while (it.hasNext()){
                String key = (String)it.next();


                if (key == "Hunter" && dataSource != "stepsStat") { } else {
                    Color classColor = colors.get(controller.fetchClassDefinitions().get(key));
                    chart.addSeries(key, turns, controller.convertToGraphData(controller.getHistory(dataSource).get(controller.fetchClassDefinitions().get((key))))).
                            setLineColor(classColor).setMarkerColor(classColor);

                }
            }

        return chart;
    }

    public void drawChart(String chartType){
        this.chartType = chartType;
        this.chart = getChart(dataChartType, dataSource);

        this.add(new XChartPanel(chart), BorderLayout.NORTH);
        updateSteps(controller.getCurrentSteps());
    }
}