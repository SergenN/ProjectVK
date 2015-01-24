package com.github.projectvk;

import com.github.projectvk.controller.Controller;
import com.github.projectvk.model.Field;
import com.github.projectvk.model.Simulator;
import com.github.projectvk.view.SimulatorView;

public class Main {

    //private static Simulator simulator;

    private static Controller controller;
    private static SimulatorView simulatorView;
    private static Simulator simulator;
    private static final int HEIGHT = 80;
    private static final int WIDTH = 100;

    //private static final

    public static Simulator getSimulator() {
        return simulator;
    }

    public static int[] getSize() {
        return new int[]{HEIGHT, WIDTH};
    }

    /**
     * Main methode
     */
    public static void main(String[] args) {

        /**
         * Initialising Script
         */

        // Create Controller Object
        controller = new Controller();

        // Create View

        simulatorView = new SimulatorView(80, 100, controller);
        controller.setSimulatorView(simulatorView);

        // Create Simulator
        simulator = new Simulator(controller);
        controller.setSimulator(simulator);
    }

}
