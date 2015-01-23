package com.github.projectvk.view;

import com.github.projectvk.model.Simulator;
import com.github.projectvk.view.BirthsGraphView;
import com.github.projectvk.view.DeathsGraphView;
import com.github.projectvk.view.StepsGraphView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private JLabel sim_kop, stat_kop;
    private JButton plusEen, plusHonderd, longSim, birthsStat, deathsStat,stepsStat, start, stop;
    private int height;
    private Simulator simulator;

    public void buttonStyle(JButton button, String command){
        button.setBackground(new Color(114, 114, 114));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Helvetica", Font.PLAIN, 12));
        button.setActionCommand(command);


        button.addActionListener(simulator.getButtonHandler());

        add(button);
    }

    public void headerStyle(JComponent label){
        label.setForeground(new Color(132, 132, 132));
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        add(label);
    }


    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     */
    public ControlPanel(int height, Simulator simulator) {

        setBackground(new Color(33, 33, 33));
        this.setLayout(null);
        this.height = height;
        this.simulator = simulator;

        // FOX PICTURE
        ImageIcon icon = new ImageIcon("img\\fox.png");
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        add(thumb);

        // HEADERS
        sim_kop = new JLabel("Simulate");
        headerStyle(sim_kop);
        stat_kop = new JLabel("Statistics");
        headerStyle(stat_kop);




        /*
         *  Buttons
         */


        // + 1 button
        plusEen = new JButton("+1");
        buttonStyle(plusEen, "plusEen");


        // + 100 button
        plusHonderd = new JButton("+100");
        buttonStyle(plusHonderd, "plusHonderd");

        // Stop button
        stop = new JButton("P");
        buttonStyle(stop, "start");

        // Start button
        start = new JButton("S");
        buttonStyle(start, "stop");

        // Simuleer button
        longSim = new JButton("+1000");
        buttonStyle(longSim, "longSim");

        // Births button
        birthsStat = new JButton("Births");
        buttonStyle(birthsStat, "birthsStat");

        // Deaths button
        deathsStat = new JButton("Deaths");
        buttonStyle(deathsStat, "deathsStat");

        // Steps button
        stepsStat = new JButton("Quantity");
        buttonStyle(stepsStat, "stepsStat");


        //Positie en groote zetten
        thumb.setBounds(13, 195, 500, 500);

        sim_kop.setBounds(10, 20, 80, 30);
        plusEen.setBounds(10, 60, 80, 30);
        plusHonderd.setBounds(10, 100, 80, 30);
        longSim.setBounds(10, 140, 80, 30);
        stop.setBounds(10, 180, 35, 30);
        start.setBounds(55, 180, 35, 30);

        stat_kop.setBounds(10, 220, 80, 30);
        birthsStat.setBounds(10, 260, 80, 30);
        deathsStat.setBounds(10, 300, 80, 30);
        stepsStat.setBounds(10, 340, 80, 30);
    }


    /**
     * verkrijg de geprefereerde groote voor deze jPane
     *
     * @return preferred size of this jPane
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(100, height * GRID_VIEW_SCALING_FACTOR);
    }


    /**
     * Disables buttons while running
     */
    public void disableButton (){
        if(simulator.isRunning()) {
            plusEen.setEnabled(false);
            plusHonderd.setEnabled(false);
            start.setEnabled(true);
            stop.setEnabled(false);
            longSim.setEnabled(false);
        } else {
            plusEen.setEnabled(true);
            plusHonderd.setEnabled(true);
            start.setEnabled(false);
            stop.setEnabled(true);
            longSim.setEnabled(true);
        }
    }
}
