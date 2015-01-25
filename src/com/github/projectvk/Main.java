package com.github.projectvk;

import com.github.projectvk.controller.Controller;
import com.github.projectvk.model.Simulator;
import com.github.projectvk.model.Statistics;
import com.github.projectvk.view.SimulatorView;

public class Main {

    //private static Simulator simulator;

    private static Controller controller;
    private static SimulatorView simulatorView;
    private static Simulator simulator;
    private static Statistics statistics;

    //private static final

    public static Simulator getSimulator() {
        return simulator;
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

        //Create statistics
        statistics = new Statistics(controller);
        controller.setStatistics(statistics);

        // Create View
        simulatorView = new SimulatorView(80, 100, controller);
        controller.setSimulatorView(simulatorView);

        // Create Simulator
        simulator = new Simulator(controller);
        controller.setSimulator(simulator);


    }

}
