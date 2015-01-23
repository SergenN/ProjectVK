package com.github.projectvk.controller;

import com.github.projectvk.model.Field;
import com.github.projectvk.model.Simulator;
import com.github.projectvk.view.*;


/**
 * Created by kevin on 22-1-15.
 */
public class Controller {

    private Simulator simulator;
    private ControlPanel panel;
    private SimulatorView simulatorView;
    private ButtonHandler buttonHandler;
    //private boolean simulatorRunning = false;

    public Controller(){

        // Make new ButtonHandler to catch ButtonEvents
        this.buttonHandler = new ButtonHandler(this);

    }

    public void setSimulatorView(SimulatorView simulatorView) {
        this.simulatorView = simulatorView;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    protected void controllerDo(String doThis) {

        System.out.println(doThis);
        if (doThis == "plusEen") simulator.start(1);
        if (doThis == "plusHonderd") simulator.start(100);
        if (doThis == "stop") simulator.stop();
        if (doThis == "start") simulator.start();
        if (doThis == "longSim") simulator.start(1000);
        if (doThis == "birthsStat") new BirthsGraphView();
        if (doThis == "deathsStat") new DeathsGraphView();
        if (doThis == "stepsStat") new StepsGraphView();

        //if (doThis == "")


    }

    public void showStatus(int step, Field field){

        simulatorView.showStatus(step, field);

    }

    public ButtonHandler getButtonHandler() {
        return buttonHandler;
    }

    public boolean isSimulatorIsRunning() {
        System.out.println("Sim is running!");
        return simulator.isRunning();

    }

    /**
     * Returns Int[] with the HEIGHT on pos 0, and WIDTH on pos 1
     * @return int[]
     */
    public int[] getFieldSize(){
        return ( new int[]{simulatorView.getHeight(), simulatorView.getWidth()});
    }

    public int getFieldHeight(){ return simulatorView.getHeight();}
    public int getFieldWidth(){  return simulatorView.getWidth();}

    public void setView(int step, Field field){
        simulatorView.showStatus(step, field);
    }

    public void disableButtons(){simulatorView.getControlPanel().disableButton();}

}
