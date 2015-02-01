package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class GraphView extends JPanel{
    private static final int GRID_VIEW_SCALING_FACTOR = 6;
    private int height;
    private Controller controller;
    protected JButton birthsStat, deathsStat, stepsStat, lineStatButton, scatterStatButton, barStatButton, areaStatButton, pieStatButton;
    private JStyle jStyle;
    private Chart chart;
    private String dataSource = "stepsHistory";
    private String dataChartType = "bar";
    private String chartType = "steps";
    private String headerTitle = "Steps";
    private Map<Class, Color> colors;

    /**
     * Constructor of this GraphView
     *
     * @param height - Height of the simulator
     * @param controller - the linked controller
     * @param colors - Colors of all entities
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
     * @return charType - Return the chart type
     */
    @SuppressWarnings("UnusedDeclaration")
    public String getChartType(){
        return chartType;
    }

    /**
     * Changes the HeaderTitle
     * @param headerTitle - Change the header title of a graph
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
        ImageIcon areaIcon = new ImageIcon("img/area.png");
        ImageIcon pieIcon = new ImageIcon("img/piechart.png");

        // Births button
        birthsStat = new JButton("Births");
        jStyle.buttonStyle(birthsStat, "birthsStat",controller, this, 110, 404, 80, 30);

        // Deaths button
        deathsStat = new JButton("Deaths");

        jStyle.buttonStyle(deathsStat, "deathsStat",controller, this, 210, 404, 80, 30);

        // Steps button
        stepsStat = new JButton("Steps");
        jStyle.buttonStyle(stepsStat, "stepsStat",controller, this, 310, 404, 80, 30);

        // Alive button
        stepsStat = new JButton("Alive");
        jStyle.buttonStyle(stepsStat, "aliveStat",controller, this, 410, 404, 80, 30);

        //Line Button
        lineStatButton = new JButton("");
        jStyle.buttonStyle(lineStatButton, "drawLine",controller, this, 60, 445, 80, 30);
        lineStatButton.setIcon(lineIcon);

        //Scatter Button
        scatterStatButton = new JButton("");
        jStyle.buttonStyle(scatterStatButton, "drawScatter",controller, this, 160, 445, 80, 30);
        scatterStatButton.setIcon(scatterIcon);

        //Bar Button
        barStatButton = new JButton("");
        jStyle.buttonStyle(barStatButton, "drawBar",controller, this, 260, 445, 80, 30);
        barStatButton.setIcon(barIcon);

        //Area Button
        areaStatButton = new JButton("");
        jStyle.buttonStyle(areaStatButton, "drawArea",controller, this, 360, 445, 80, 30);
        areaStatButton.setIcon(areaIcon);

        //Pie Chart Button
        pieStatButton = new JButton("");
        jStyle.buttonStyle(pieStatButton, "pieChart",controller, this, 458, 445, 80, 30);
        pieStatButton.setIcon(pieIcon);
    }

    /**
     * Get the preferred size of this jPane
     *
     * @return preferred size of this jPane
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(600, height * GRID_VIEW_SCALING_FACTOR);
    }

    /**
     * This methods produces an double array which contains the stepvalue for the x-axis of the graph
     * @return double[] turns - The X axis of the graph
     */
    public double[] calculateTurns(){
        // When there has been more than HISTORY_TURNS steps, only show the last HISTORYTURNS entries
        if(controller.getCurrentSteps() >= (int)controller.getMaxTurns()) {
            double[] turns_temp = new double[(int) controller.getMaxTurns()];
            int currentValue = controller.getCurrentSteps() - (int) controller.getMaxTurns();
            for (int i = 0; i < controller.getMaxTurns(); i++) {
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
     * @param dataChartType String - line, bar, scatter
     * @return StyleManager.ChartType - Return a chartype, like bar or line
     */
    public StyleManager.ChartType getGraphChartType(String dataChartType){
        switch (dataChartType){
            case "line":
                return StyleManager.ChartType.Line;
            case "bar":
                return StyleManager.ChartType.Bar;
            case "scatter":
                return StyleManager.ChartType.Scatter;
            case "area":
                return StyleManager.ChartType.Area;
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
     * @param type - The wished type
     */
    public void setDataChartType(String type){
        this.dataChartType = type;
    }

    /**
     * Gets the Datacharttype
     * @return dataChartType - The type of a chart
     */
    public String getDataChartType(){
        return dataChartType;
    }

    /**
     * Returns a Chart
     * @param dataChartType - What kind of graph do you want? By example: bar, line, scatter
     * @param dataSource - What do you want the graph to show? By example the deaths of the rabbits
     * @return chart - This returns the newly built graph
     */
    public Chart getChart(String dataChartType, String dataSource) {
        // Put the turn steps in a double array
        double[] turns = calculateTurns();

        // Create Chart
        chart = new ChartBuilder().chartType(getGraphChartType(dataChartType)).width(600).height(400).title(headerTitle).xAxisTitle("Step (Current step: " + controller.getCurrentSteps() + ")").yAxisTitle("Amount").build();
        Set<String> it = controller.fetchClassDefinitions().keySet();
        for (String key : it){
//            if (!(key.equalsIgnoreCase("Hunter") && !dataSource.equalsIgnoreCase("stepsStat"))) {
//                Color classColor = colors.get(controller.fetchClassDefinitions().get(key));
//                chart.addSeries(key, turns, controller.convertToGraphData(controller.getHistory(dataSource).get(controller.fetchClassDefinitions().get((key))))).setLineColor(classColor).setMarkerColor(classColor);
//            }

            if(headerTitle.equals("Steps")){
                Color classColor = colors.get(controller.fetchClassDefinitions().get(key));
                chart.addSeries(key, turns, controller.convertToGraphData(controller.getHistory(dataSource).get(controller.fetchClassDefinitions().get((key))))).setLineColor(classColor).setMarkerColor(classColor);
            } else {
                if (!(key.equalsIgnoreCase("Hunter"))) {
                    Color classColor = colors.get(controller.fetchClassDefinitions().get(key));
                    chart.addSeries(key, turns, controller.convertToGraphData(controller.getHistory(dataSource).get(controller.fetchClassDefinitions().get((key))))).setLineColor(classColor).setMarkerColor(classColor);
                }
            }
        }
        return chart;
    }

    /**
     * This function will call getChart to make a new graph.
     * @param chartType - What kind of chart do you want to draw?
     */
    public void drawChart(String chartType){
        this.remove(9);
        this.chartType = chartType;
        this.chart = getChart(dataChartType, dataSource);
        this.add(new XChartPanel(chart), BorderLayout.NORTH);
    }
}