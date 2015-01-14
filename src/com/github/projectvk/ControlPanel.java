package com.github.projectvk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener{

    private final int GRID_VIEW_SCALING_FACTOR = 6;

    private Simulator simulator;
    private JButton plusEen, plusHonderd, longSim;
    private int width, height;

    public ControlPanel(int height, Simulator simulator) {
        this.simulator = simulator;
        plusEen = new JButton("+1");
        plusEen.addActionListener(this);
        plusHonderd = new JButton("+100");
        plusHonderd.addActionListener(this);
        longSim = new JButton("Simuleer");
        longSim.addActionListener(this);

        this.setLayout(null);
        add(plusEen);
        add(plusHonderd);
        add(longSim);
        plusEen.setBounds(10, 20, 80, 30);
        plusHonderd.setBounds(10, 60, 80, 30);
        longSim.setBounds(10, 100, 80, 30);
        setVisible(true);

    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(100, height * GRID_VIEW_SCALING_FACTOR);
    }

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
