package com.github.projectvk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private Simulator simulator;
    private JLabel sim_kop, stat_kop;
    private JButton plusEen, plusHonderd, longSim, survivorStat;
    private int height;

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     * @param simulator - Simulator klasse
     */
    public ControlPanel(int height, Simulator simulator) {
        //setBackground(new Color(39, 39, 39));
        setBackground(new Color(33, 33, 33));

        ImageIcon icon = new ImageIcon("img\\fox.png");
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);

        this.height = height;
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
        plusEen.setFont(new Font("Helvetica", Font.PLAIN, 10));
        plusEen.addActionListener(this);

        // + 100 button
        plusHonderd = new JButton("+100");
        plusHonderd.setBackground(new Color(114, 114, 114));
        plusHonderd.setForeground(Color.WHITE);
        plusHonderd.setFocusPainted(false);
        plusHonderd.setFont(new Font("Helvetica", Font.PLAIN, 10));
        plusHonderd.addActionListener(this);

        // Simuleer button
        longSim = new JButton("+4000");//Initializeren van button
        longSim.setBackground(new Color(114, 114, 114));
        longSim.setForeground(Color.WHITE);
        longSim.setFocusPainted(false);
        longSim.setFont(new Font("Helvetica", Font.PLAIN, 10));
        longSim.addActionListener(this);//Toevoegen actieListener

        // SurvivorStat button
        survivorStat = new JButton("Survivors");
        survivorStat.setBackground(new Color(99, 99, 99));
        survivorStat.setForeground(Color.WHITE);
        survivorStat.setFocusPainted(false);
        survivorStat.setFont(new Font("Helvetica", Font.PLAIN, 10));
        survivorStat.addActionListener(this);

        this.setLayout(null);
        add(sim_kop);
        add(plusEen);
        add(plusHonderd);
        add(longSim);//Toevoegen aan jPanel
        add(stat_kop);
        add(survivorStat);
        add(thumb);

        thumb.setBounds(13, 195, 500, 500);

        sim_kop.setBounds(10, 0, 80, 30);
        plusEen.setBounds(10, 40, 80, 30);
        plusHonderd.setBounds(10, 80, 80, 30);
        longSim.setBounds(10, 120, 80, 30);//Positie en groote zetten

        stat_kop.setBounds(10, 160, 80, 30);
        survivorStat.setBounds(10, 200, 80, 30);

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
            simulator.simulateOneStep();
        }

        if (e.getSource() == plusHonderd) {
            simulator.simulate(100);
        }

        if (e.getSource() == longSim) {
            simulator.runLongSimulation();
        }

        if (e.getSource() == survivorStat) {
            SurvivorsView sView = new SurvivorsView();
        }
    }
}
