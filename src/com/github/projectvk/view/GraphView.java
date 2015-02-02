package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import com.github.projectvk.view.graph.CustomPieChart;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
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
    private CustomPieChart pieChart;
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
        setBackground(new Color(210, 210, 210));
        this.height = height;
        this.controller = controller;
        jStyle = controller.getJStyle();
        this.colors = colors;
        makeGUI();
        setLayout(new BorderLayout());
        
        Object currentChart = getChart(dataChartType, dataSource);
        if(currentChart instanceof CustomPieChart){
            this.chart = null;
            pieChart = (CustomPieChart)currentChart;
            add(pieChart);
        } else {
            chart = (Chart)currentChart;
            add(new XChartPanel(chart), BorderLayout.NORTH);
        }
    }

    /**
     * Returns CharType
     * @return charType - Return the chart type
     */
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
    public Object getChart(String dataChartType, String dataSource) {
        if(dataChartType.equalsIgnoreCase("pie")){
            return getPieChart(dataSource);
        }
        // Put the turn steps in a double array
        double[] turns = calculateTurns();

        // Create Chart
        chart = new ChartBuilder().chartType(getGraphChartType(dataChartType)).width(600).height(400).title(headerTitle).xAxisTitle("Step (Currently " + controller.getCurrentSteps() + ")").yAxisTitle("Amount").build();
        
        Set<String> it = controller.fetchClassDefinitions().keySet();
        for (String key : it){
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
     * Because piecharts are not covered by xCharts here a custom function for our piecharts 
     * @param dataSource the source of data to be displayed
     * @return a panel with the piechart
     */
    private Component getPieChart(String dataSource){
        // Put the turn steps in a double array
        double[] turns = calculateTurns();
        //init the hashmap of color -> data which is needed for the chart
        HashMap<String, HashMap<Color, Integer>> data = new HashMap<>();
        Set<String> it = controller.fetchClassDefinitions().keySet();
        for (String key : it){
            if(headerTitle.equals("Steps")){
                Color color = colors.get(controller.fetchClassDefinitions().get(key));
                int amount = getAverage(controller.convertToGraphData(controller.getHistory(dataSource).get(controller.fetchClassDefinitions().get((key)))));
                data.put(key, new HashMap<>());
                data.get(key).put(color, amount);
            } else {
                if (!(key.equalsIgnoreCase("Hunter"))) {
                    Color color = colors.get(controller.fetchClassDefinitions().get(key));
                    int amount = getAverage(controller.convertToGraphData(controller.getHistory(dataSource).get(controller.fetchClassDefinitions().get((key)))));
                    data.put(key, new HashMap<>());
                    data.get(key).put(color, amount);
                }
            }
        }
        Component pieChart = new CustomPieChart(data, controller);
        return pieChart;
    }
    
    /**
     * This function will call getChart to make a new graph.
     * @param chartType - What kind of chart do you want to draw?
     */
    public void drawChart(String chartType){
        remove(9);
        this.chartType = chartType;
        
        Object currentChart = getChart(dataChartType, dataSource);
        if(currentChart instanceof CustomPieChart){
            this.chart = null;
            pieChart = (CustomPieChart)currentChart;
            add(pieChart);
        } else {
            chart = (Chart)currentChart;
            add(new XChartPanel(chart), BorderLayout.NORTH);
        }
    }

    /**
     * convert a double array to average int 
     * @param input double array of data
     * @return int of the average of array content
     */
    public int getAverage(double[] input){
        int toReturn = 0;
        int total = 0;
        if(input.length > 0){
            for (double in : input){
                total += Math.round(in);
            }
            toReturn = total;
        }
        return toReturn;
    }

    /**
     * Method to add a new Jlabel to the GraphView
     * @param element kind of element
     * @param xposition x position of element
     * @param yposition y position of element
     * @param width width of element
     * @param height height of element
     * @param color color of element
     * @param fontSize font size of element
     */
    public void addComponent(JComponent element, int xposition, int yposition, int width, int height, Color color, int fontSize){
        jStyle.headerStyle(element, this, xposition, yposition, width, height, color, fontSize);
    }
}