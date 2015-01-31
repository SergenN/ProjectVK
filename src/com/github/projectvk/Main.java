package com.github.projectvk;

import com.github.projectvk.controller.Controller;
import com.github.projectvk.model.*;
import com.github.projectvk.model.Simulator;
import com.github.projectvk.model.Statistics;
import com.github.projectvk.model.file.PropertiesHandler;
import com.github.projectvk.view.SimulatorView;

public class Main {

    private static Controller controller;
    private static SimulatorView simulatorView;
    private static Simulator simulator;
    private static final int HEIGHT = 80;
    private static final int WIDTH = 100;
    private static Statistics statistics;
    private static Weather weather;
    private static Audio audio;
    public static PropertiesHandler propertiesFile;

    /**
     * the Simulator handler of the simulation
     * @return the current simulator handler
     */
    public static Simulator getSimulator() {
        return simulator;
    }

    /**
     * get the size of the frame
     * @return int array with the height as 0 and with as 1
     */
    public static int[] getSize() {
        return new int[]{HEIGHT, WIDTH};
    }

    /**
     * Main methode
     * @param args de meegegeven parameters bij het starten
     */
    public static void main(String[] args) {
        //Initialize the propertiesfile
        propertiesFile = new PropertiesHandler();

        // Create Controller Object
        controller = new Controller();
        
        // Create statistics
        statistics = new Statistics(controller);
        controller.setStatistics(statistics);

        // Create Audio class
        audio = new Audio();
        controller.setAudio(audio);

        // Create View
        simulatorView = new SimulatorView(80,100, controller);
        controller.setSimulatorView(simulatorView);

        // Create weather model
        weather = new Weather(controller);
        controller.setWeather(weather);

        // Create Simulator
        simulator = new Simulator(controller);
        controller.setSimulator(simulator);
    }

}
