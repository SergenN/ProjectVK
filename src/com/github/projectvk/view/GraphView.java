package com.github.projectvk.view;

import com.github.projectvk.model.Simulator;
import com.xeiam.xchart.*;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GraphView extends JPanel{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private int height;
    private Simulator simulator;
    private JButton pbirthsStat, deathsStat,stepsStat;

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     */
    public GraphView(int height, Simulator simulator) {

        Chart chart = getChart();
        this.setLayout(new BorderLayout());
        this.add(new XChartPanel(chart), BorderLayout.NORTH);

        this.setBackground(new Color(210, 210, 210));

        this.height = height;
        this.simulator = simulator;
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

        // Create Chart
        //Chart chart = new QuickChart().chartType(StyleManager.ChartType.Line).width(600).height(400).title(getClass().getSimpleName()).xAxisTitle("Stap").yAxisTitle("Aantallen").build();
        Chart chart = new ChartBuilder().chartType(StyleManager.ChartType.Line).width(600).height(400).title(getClass().getSimpleName()).xAxisTitle("Stap").yAxisTitle("Aantallen").build();
        chart.addSeries("Rabbits", new double[]{0,1,2}, new double[]{3,6,5});
        chart.addSeries("Foxes", new double[]{0,1,2}, new double[]{3,3,4});
        chart.addSeries("Dodo", new double[]{0,1,2}, new double[]{7,5,3});

        return chart;
    }

}
