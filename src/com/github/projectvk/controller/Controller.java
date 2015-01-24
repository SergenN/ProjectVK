package com.github.projectvk.controller;

import com.github.projectvk.model.*;
import com.github.projectvk.view.*;


/**
 * Created by kevin on 22-1-15.
 */
public class Controller {

    private Simulator simulator;
    private ControlPanel panel;
    private SimulatorView simulatorView;
    private ButtonHandler buttonHandler;
    private JStyle jStyle = new JStyle();
    private GraphView graphView;

    private FieldStats fieldStats;
    //private boolean simulatorRunning = false;

    public Controller(){

        fieldStats = new FieldStats();
        // Make new ButtonHandler to catch ButtonEvents
        this.buttonHandler = new ButtonHandler(this);

    }

    public void setSimulatorView(SimulatorView simulatorView) {
        this.simulatorView = simulatorView;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    public JStyle getJStyle(){return jStyle;}

    protected void controllerDo(String doThis) {

        System.out.println(doThis);
        if (doThis == "plusEen") simulator.start(1);
        if (doThis == "plusHonderd") simulator.start(100);
        if (doThis == "stop") simulator.stop();
        if (doThis == "start") simulator.start();
        if (doThis == "longSim") simulator.start(1000);
        if (doThis == "birthsStat") simulatorView.getGraphView().drawChart("births");
        if (doThis == "deathsStat") simulatorView.getGraphView().drawChart("deaths");
        if (doThis == "stepsStat") simulatorView.getGraphView().drawChart("steps");

        //if (doThis == "")
    }

    /**
     * Resets Field
     *
     * @param step
     * @param field
     */
    public void showStatus(int step, Field field){

        simulatorView.showStatus(step, field);

    }

    /**
     * Returns ButtonListener
     *
     * @return
     */
    public ButtonHandler getButtonHandler() {
        return buttonHandler;
    }

    /**
     * Returns true or false depending on a running simulator
     * @return
     */
    public boolean isSimulatorRunning() {
        //System.out.println("Sim is running!");
        return simulator.isRunning();
    }

    /**
     * Returns Int[] with the HEIGHT on pos 0, and WIDTH on pos 1
     * @return int[]
     */
    /*public int[] getFieldSize(){
        return ( new int[]{simulatorView.getHeight(), simulatorView.getWidth()});
    }
    */

    /**
     * Returns Field Height
     *
     * @return
     */
    public int getFieldHeight() {
        return simulatorView.getFieldHeight();
    }

    /**
     * Returns Field Width
     *
     * @return
     */
    public int getFieldWidth() {
        return simulatorView.getFieldWidth();
    }

    /*public void setView(int step, Field field){
        simulatorView.showStatus(step, field);
    }*/

    /**
     * Calls method which disables buttons on ControlPanel
     */
    public void disableButtons() {
        simulatorView.getControlPanel().disableButton();
    }

    /**
     * Returns array of Class.class objects
     *
     * @return Class[]
     */
    public Class[] fetchClassDefinitions() {

        Class[] classes = new Class[]{Rabbit.class, Fox.class, Dodo.class, Hunter.class};

        return classes;


    }

    public FieldStats getFieldStats() {
        return fieldStats;
    }

}
