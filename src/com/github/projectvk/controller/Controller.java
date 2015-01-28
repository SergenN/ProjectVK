package com.github.projectvk.controller;

import com.github.projectvk.Main;
import com.github.projectvk.model.*;
import com.github.projectvk.view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by kevin on 22-1-15.
 * Class: Controller
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
    private Weather weather;
    private Audio audio;

    //private boolean simulatorRunning = false;

    /**
     * the constructor of the controller
     */
    public Controller(){
        fieldStats = new FieldStats();
        field = new Field(Main.getSize()[0], Main.getSize()[1]);
        this.listener = new Listener(this);
    }

    /**
     * set the simulatorView of this controller
     * @param simulatorView the simulator view to be set
     */
    public void setSimulatorView(SimulatorView simulatorView) {
        this.simulatorView = simulatorView;
    }

    /**
     * get the simulatorview of the controller
     * @return the simulator view of this controller
     */
    //TODO unused
    /*public SimulatorView getSimulatorView() {return simulatorView;}*/

    /**
     * set the simulator of the controller
     * @param simulator the simulator which has to be set
     */
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    /**
     * get the statistics of this controller
     * @param statistics the statistics
     */
    public void setStatistics(Statistics statistics) { this.statistics = statistics; }

    public void setWeather(Weather weather) { this.weather = weather; }

    public void setAudio(Audio audio) { this.audio = audio; }

    /**
     * get the JStyle of this controller
     * @return the jstyle
     */
    public JStyle getJStyle(){return jStyle;}

    /**
     * preform an action
     * @param doThis the action you want to be preformed
     */
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
     * @param step the step
     */
    public void showStatus(int step) {

        simulatorView.showStatus(step);

    }

    /**
     * Returns ButtonListener
     *
     * @return the listener of this controller
     */
    public Listener getListener() {
        return listener;
    }

    /**
     * Returns true or false depending on a running simulator
     * @return if the simulator is running
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

    /**
     * Calls method which disables buttons on ControlPanel
     */
    public void disableButtons() {
        simulatorView.getControlPanel().changeButtons();
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
        return field;
    }

    /**
     * These are all methods related to statistics.java
     */
    public void updateData(){
        statistics.updateData();
    }

    /**
     *
     * @param type
     * @return
     */
    public HashMap<Class, ArrayList<Double>> getHistory(String type){ return statistics.getHistory(type);}

    /**
     * TODO not
     * @param list
     * @return
     */
    public double[] convertToGraphData(List<Double> list) {
        return statistics.convertToGraphData(list);
    }

    /**
     * TODO not
     * @return
     */
    public double getMaxTurns(){return Statistics.HISTORY_TURNS;}

    /**
     * TODO not
     * @return
     */
    public int getCurrentSteps(){ return statistics.getCurrentStep();}

    /**
     * reset the data of the statistics
     */
    public void resetData(){ statistics.resetData();}

    /**
     * TODO not
     * @param historyTurns
     */
    public void setStatisticHistoryTurns(int historyTurns){
        Statistics.setHistoryTurns(historyTurns);
    }

    // These are all methods related to weather
    public void changeWeatherText(String weatherType){ simulatorView.getControlPanel().changeWeatherText(weatherType); }

    public void randomWeather(){ weather.randomWeather(); }

    public int getWeatherStep(){ return weather.getChangeWeatherStep(); }

    // These are all methods related to Audio

    public void playSound(String soundPath){ audio.playSound(soundPath); }

}
