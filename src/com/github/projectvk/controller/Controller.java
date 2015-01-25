package com.github.projectvk.controller;

import com.github.projectvk.Main;
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
    private Field field;
    private FieldStats fieldStats;
    //private boolean simulatorRunning = false;

    public Controller(){

        fieldStats = new FieldStats();
        field = new Field(Main.getSize()[0], Main.getSize()[1]);
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
        //todo MAKE THIS EQUALS
        //todo MAKE THIS SWITCH ?
        System.out.println(doThis);
        switch (doThis) {

            case "plusEen":
                simulator.start(1);
                break;
            case "plusHonderd":
                simulator.start(100);
                break;
            case "start":
                simulator.start();
                break;
            case "stop":
                simulator.stop();
                break;
            case "longSim":
                simulator.start(1000);
                break;

            case "birthsStat":
                simulatorView.getGraphView().drawChart("births");
                break;
            case "deathsStat":
                simulatorView.getGraphView().drawChart("deaths");
                break;
            case "stepsStat":
                simulatorView.getGraphView().drawChart("steps");
                break;
        }





    }

    /**
     * Resets Field
     *
     * @param step
     * @param field
     */
    public void showStatus(int step) {

        simulatorView.showStatus(step);

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
     * @return int FieldHeight
     */
    public int getFieldHeight() {
        return Main.getSize()[0];
    }

    /**
     * Returns Field Width
     *
     * @return int getFieldWidth
     */
    public int getFieldWidth() {
        return Main.getSize()[1];
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

    /**
     * Returns FieldStats
     *
     * @return FieldStats
     */
    public FieldStats getFieldStats() {
        return fieldStats;
    }

    /**
     * Returns Field Object
     *
     * @return Field field
     */
    public Field getField() {
        return field;}


}
