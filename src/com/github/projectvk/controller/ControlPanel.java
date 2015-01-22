package com.github.projectvk.controller;

import com.github.projectvk.model.Simulator;
import com.github.projectvk.view.BirthsGraphView;
import com.github.projectvk.view.DeathsGraphView;
import com.github.projectvk.view.StepsGraphView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private JLabel sim_kop, stat_kop;
    private JButton plusEen, plusHonderd, longSim, birthsStat, deathsStat,stepsStat, start, stop;
    private int height;
    private Simulator simulator;

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     */
    public ControlPanel(int height, Simulator simulator) {
        setBackground(new Color(33, 33, 33));

        ImageIcon icon = new ImageIcon("img\\fox.png");
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        this.height = height;
        System.out.println("TEST");
        this.simulator = simulator;

        sim_kop = new JLabel("Simulate");
        sim_kop.setForeground(new Color(132, 132, 132));
        sim_kop.setFont(new Font("Helvetica", Font.BOLD, 16));

        stat_kop = new JLabel("Statistics");
        stat_kop.setForeground(new Color(132, 132, 132));
        stat_kop.setFont(new Font("Helvetica", Font.BOLD, 16));

        // + 1 button
        plusEen = new JButton("+1");
        plusEen.setBackground(new Color(114, 114, 114));
        plusEen.setForeground(Color.WHITE);
        plusEen.setFocusPainted(false);
        plusEen.setFont(new Font("Helvetica", Font.PLAIN, 12));
        plusEen.addActionListener(this);

        // + 100 button
        plusHonderd = new JButton("+100");
        plusHonderd.setBackground(new Color(114, 114, 114));
        plusHonderd.setForeground(Color.WHITE);
        plusHonderd.setFocusPainted(false);
        plusHonderd.setFont(new Font("Helvetica", Font.PLAIN, 12));
        plusHonderd.addActionListener(this);

        // Stop button
        stop = new JButton("P");
        stop.setBackground(new Color(114, 114, 114));
        stop.setForeground(Color.WHITE);
        stop.setFocusPainted(false);
        stop.setFont(new Font("Helvetica", Font.PLAIN, 12));
        stop.addActionListener(this);

        // Start button
        start = new JButton("S");
        start.setBackground(new Color(114, 114, 114));
        start.setForeground(Color.WHITE);
        start.setFocusPainted(false);
        start.setFont(new Font("Helvetica", Font.PLAIN, 12));
        start.addActionListener(this);

        // Simuleer button
        longSim = new JButton("+4000");//Initializeren van button
        longSim.setBackground(new Color(114, 114, 114));
        longSim.setForeground(Color.WHITE);
        longSim.setFocusPainted(false);
        longSim.setFont(new Font("Helvetica", Font.PLAIN, 12));
        longSim.addActionListener(this);//Toevoegen actieListener

        // Births button
        birthsStat = new JButton("Births");
        birthsStat.setBackground(new Color(99, 99, 99));
        birthsStat.setForeground(Color.WHITE);
        birthsStat.setFocusPainted(false);
        birthsStat.setFont(new Font("Helvetica", Font.PLAIN, 12));
        birthsStat.addActionListener(this);

        // Deaths button
        deathsStat = new JButton("Deaths");
        deathsStat.setBackground(new Color(99, 99, 99));
        deathsStat.setForeground(Color.WHITE);
        deathsStat.setFocusPainted(false);
        deathsStat.setFont(new Font("Helvetica", Font.PLAIN, 12));
        deathsStat.addActionListener(this);

        // Steps button
        stepsStat = new JButton("Quantity");
        stepsStat.setBackground(new Color(99, 99, 99));
        stepsStat.setForeground(Color.WHITE);
        stepsStat.setFocusPainted(false);
        stepsStat.setFont(new Font("Helvetica", Font.PLAIN, 12));
        stepsStat.addActionListener(this);

        this.setLayout(null);
        add(sim_kop);
        add(plusEen);
        add(plusHonderd);
        add(stop);
        add(start);
        add(longSim);//Toevoegen aan jPanel
        add(stat_kop);
        add(birthsStat);
        add(deathsStat);
        add(stepsStat);
        add(thumb);

        thumb.setBounds(13, 195, 500, 500);

        //Positie en groote zetten

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

        setVisible(true);

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
     * vang een actie(button click) op en process
     *
     * @param e - de actie
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plusEen) {
            simulator.start(1);
        }

        if (e.getSource() == plusHonderd) {
            simulator.start(100);
        }

        if (e.getSource() == longSim) {
            simulator.start(1000);
        }

        if (e.getSource() == birthsStat) {
            new BirthsGraphView();
        }

        if(e.getSource() == deathsStat){
            new DeathsGraphView();
        }

        if(e.getSource() == stepsStat){
            new StepsGraphView();
        }

        if(e.getSource() == stop){
            simulator.start();
        }

        if(e.getSource() == start){
            simulator.stop();
        }
    }
}
