package com.github.projectvk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private Simulator simulator;
    private JButton plusEen, plusHonderd, longSim;
    private int height;

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     * @param simulator - Simulator klasse
     */
    public ControlPanel(int height, Simulator simulator) {
        this.height = height;
        this.simulator = simulator;
        plusEen = new JButton("+1");
        plusEen.addActionListener(this);
        plusHonderd = new JButton("+100");
        plusHonderd.addActionListener(this);
        longSim = new JButton("Simuleer");//Initializeren van button
        longSim.addActionListener(this);//Toevoegen actieListener

        this.setLayout(null);
        add(plusEen);
        add(plusHonderd);
        add(longSim);//Toevoegen aan jPanel
        plusEen.setBounds(10, 20, 80, 30);
        plusHonderd.setBounds(10, 60, 80, 30);
        longSim.setBounds(10, 100, 80, 30);//Positie en groote zetten
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
    }
}
