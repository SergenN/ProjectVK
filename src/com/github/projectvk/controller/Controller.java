package com.github.projectvk.controller;

import com.github.projectvk.Main;
import com.github.projectvk.model.*;
import com.github.projectvk.view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by kevin on 22-1-15.
 */
public class Controller {

    private Simulator simulator;
    private ControlPanel panel;
    private SimulatorView simulatorView;
    private Listener listener;
    private JStyle jStyle = new JStyle();
    private GraphView graphView;
    private Statistics statistics;
    private Field field;
    private FieldStats fieldStats;

    public Controller(){
        fieldStats = new FieldStats();
        field = new Field(Main.getSize()[0], Main.getSize()[1]);
        // Make new ButtonHandler to catch ButtonEvents
        this.listener = new Listener(this);
    }

    public void setSimulatorView(SimulatorView simulatorView) {
        this.simulatorView = simulatorView;
    }

    public SimulatorView getSimulatorView() {return simulatorView;}

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    public void setStatistics(Statistics statistics) { this.statistics = statistics; }

    public JStyle getJStyle(){return jStyle;}

    protected void controllerDo(String doThis) {
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
            case "reset":
                simulator.reset();
                statistics.resetStats();
                simulator.start(0);
                break;
            case "settings":
                new SettingsFrame();
                break;
            case "birthsStat":
                simulatorView.getGraphView().setHeaderTitle("Births");
                simulatorView.getGraphView().setDataSource("birthsHistory");
                break;
            case "deathsStat":
                simulatorView.getGraphView().setHeaderTitle("Deaths");
                simulatorView.getGraphView().setDataSource("deathsHistory");
                break;
            case "stepsStat":
                simulatorView.getGraphView().setHeaderTitle("Steps");
                simulatorView.getGraphView().setDataSource("stepsHistory");
                break;
            case "aliveStat":
                //simulatorView.getGraphView().drawChart("steps");
                simulatorView.getGraphView().setHeaderTitle("Alive");
                simulatorView.getGraphView().setDataSource("aliveHistory");

                break;

            case "drawScatter":
                simulatorView.getGraphView().setDataChartType("scatter");
                setStatisticHistoryTurns(20);
                break;
            case "drawBar":
                simulatorView.getGraphView().setDataChartType("bar");
                setStatisticHistoryTurns(1);
                break;
            case "drawLine":
                simulatorView.getGraphView().setDataChartType("line");
                setStatisticHistoryTurns(20);
                break;
        }
    }

    /**
     * Resets Field
     *
     * @param step
     */
    public void showStatus(int step) {

        simulatorView.showStatus(step);

    }

    /**
     * Returns ButtonListener
     *
     * @return
     */
    public Listener getListener() {
        return listener;
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
     * Returns hashmap of Class.class objects
     *
     * @return Class[]
     */ // Todo make this hashmap compatible
    public HashMap<String, Class> fetchClassDefinitions() {
        HashMap<String, Class> classes = new HashMap<String, Class>(){};
        classes.put("Rabbit", Rabbit.class);
        classes.put("Fox", Fox.class);
        classes.put("Dodo", Dodo.class);
        classes.put("Hunter", Hunter.class);
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


    // These are all methods related to statistics.java
    public void updateData(){
        statistics.updateData();
    }

    public HashMap<Class, ArrayList<Double>> getHistory(String type){ return statistics.getHistory(type);}

    public double[] convertToGraphData(List<Double> list){
        return statistics.convertToGraphData(list);
    }

    public double getMaxTurns(){return Statistics.HISTORY_TURNS;}

    public int getCurrentSteps(){ return statistics.getCurrentStep();}

    public void resetData(){ statistics.resetData();}

    public void setStatisticHistoryTurns(int historyTurns){
        Statistics.setHistoryTurns(historyTurns);
    }

}
