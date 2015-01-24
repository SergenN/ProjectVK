package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import com.xeiam.xchart.*;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GraphView extends JPanel{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private int height;
    private Controller controller;
    private JButton birthsStat, deathsStat,stepsStat;

    public void buttonStyle(JButton button, String command){
        button.setBackground(new Color(114, 114, 114));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Helvetica", Font.PLAIN, 12));
        button.setActionCommand(command);

        //button.addActionListener(controller.getButtonHandler());

        add(button);
    }

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     */
    public GraphView(int height, Controller controller) {
        // Births button
        birthsStat = new JButton("Births");
        buttonStyle(birthsStat, "birthsStat");
        birthsStat.setEnabled(false);

        // Deaths button
        deathsStat = new JButton("Deaths");
        buttonStyle(deathsStat, "deathsStat");
        deathsStat.setEnabled(false);

        // Steps button
        stepsStat = new JButton("Quantity");
        buttonStyle(stepsStat, "stepsStat");
        stepsStat.setEnabled(false);

        birthsStat.setBounds(15, 440, 80, 30);
        deathsStat.setBounds(115, 440, 80, 30);
        stepsStat.setBounds(215, 440, 80, 30);

        Chart chart = getChart();
        this.setLayout(new BorderLayout());
        this.add(new XChartPanel(chart), BorderLayout.NORTH);

        this.setBackground(new Color(210, 210, 210));

        this.height = height;
        this.controller = controller;
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
        Chart chart = new ChartBuilder().chartType(StyleManager.ChartType.Line).width(600).height(400).title("Deaths").xAxisTitle("Stap").yAxisTitle("Aantallen").build();
        chart.addSeries("Rabbits", new double[]{0,1,2}, new double[]{3,6,5});
        chart.addSeries("Foxes", new double[]{0,1,2}, new double[]{3,3,4});
        chart.addSeries("Dodo", new double[]{0,1,2}, new double[]{7,5,3});

        return chart;
    }

}
