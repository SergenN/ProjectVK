package com.github.projectvk.model;

import com.github.projectvk.controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Thijs on 21-1-2015.
 * Class: Statistics
 */
public class Statistics {
    private Controller controller;
    // Bepaal voor hoe lang de geschiedenis van de data moet worden (standaard laatste 100 turns)
    public static double HISTORY_TURNS = 1;

    public static HashMap<Class, ArrayList<Double>> deaths = new HashMap<>();
    public static HashMap<Class, ArrayList<Double>> births = new HashMap<>();
    public static HashMap<Class, ArrayList<Double>> steps = new HashMap<>();
    public static HashMap<Class, ArrayList<Double>> alive = new HashMap<>();

    private HashMap<Class, ArrayList<Double>> deathsHistory = new HashMap<>();
    private HashMap<Class, ArrayList<Double>> birthsHistory = new HashMap<>();
    private HashMap<Class, ArrayList<Double>> stepsHistory = new HashMap<>();
    private HashMap<Class, ArrayList<Double>> aliveHistory = new HashMap<>();

    private int currentStep = 0;

    /**
     * Constructor
     * @param controller - the linked controller
     */
    public Statistics(Controller controller) {
        this.controller = controller;
    }

    /**
     * Retrieve history hashmap by name
     * @param type - What kind of history do you want to be returned?
     * @return HashMap - Hashmap which contains data of the last HISTORY_TURNS
     */
    public HashMap<Class, ArrayList<Double>> getHistory(String type){

        if(type.equals("deathsHistory")) {
            return deathsHistory;
        }
        if(type.equals("birthsHistory")) {
            return birthsHistory;
        }
        if(type.equals("stepsHistory")) {
            return stepsHistory;
        }
        if(type.equals("aliveHistory")) {
            return aliveHistory;
        }
        if(type.equals("deaths")) {
            return deaths;
        }
        if(type.equals("births")) {
            return births;
        }
        if(type.equals("steps")) {
            return steps;
        }
        if(type.equals("alive")) {
            return alive;
        }
        return null;
    }

    /**
     * Sets Historyturns , which stands for the max x value on the graph
     * @param historyTurns the history turn
     */
    public static void setHistoryTurns(int historyTurns){
        HISTORY_TURNS = historyTurns;
    }

    /**
     * Methods to fill the data arrays
     * @param list - Hashmap which contains data of this step
     * @param animal - The kind of animal you want to retrieve
     * @param amount - How much should be added to the stats?
     */
    public static void addData(HashMap<Class, ArrayList<Double>> list, Class animal, double amount){
        if(list.get(animal) == null || list.get(animal).isEmpty()){
            list.put(animal, new ArrayList<>());
            list.get(animal).add(0.0);
        }
        list.get(animal).set(0, list.get(animal).get(0) + amount);
    }

    /**
     * When a step is finished, we need to empty all statistics hashmaps (those hashmap will only contain data for one step)
     */
    public void resetData(){
        currentStep++;
        births.clear();
        steps.clear();
        deaths.clear();
        alive.clear();
    }

    /**
     * This method will clear all variabels related to the statistics. This method is being called when the user presses the reset button.
     */
    public void resetStats(){

        currentStep = 0;
        births.clear();
        steps.clear();
        deaths.clear();
        alive.clear();
        deathsHistory.clear();
        birthsHistory.clear();
        stepsHistory.clear();
        aliveHistory.clear();
    }

    /**
     * Get the current step
     * @return currentStep - The current step
     */
    public int getCurrentStep(){
        return currentStep;
    }

    /**
     * This method is being called when a step is finished. It will add all newly gained data to the history list. This history lists contains all data of the previous HISTORY_TURNS steps.
     * @param list - Hashmap with the history steps
     * @param animal - The kind of animal you want to modify
     * @param source - Hashmap with data of a step
     */
    public void addDataToHistory(HashMap<Class, ArrayList<Double>> list, Class animal, HashMap<Class, ArrayList<Double>> source){

        // Catch error codes
        if(list.get(animal) == null){
            list.put(animal, new ArrayList<>());
            list.get(animal).add(0.0);
        }

        if(source.get(animal) == null) {
            source.put(animal, new ArrayList<>());
            source.get(animal).add(0.0);
        }

        if (source.get(animal).isEmpty()){
            source.get(animal).add(0.0);
        }

        //Setup birth data for graph
//        if(list.get(animal).size() >= HISTORY_TURNS) {
//            list.get(animal).remove(0);
//        }
        list.get(animal).add(source.get(animal).get(0));
        source.get(animal).clear();
    }

    /**
     * This will run at the end of each step. It is responsible for adding the gained data to the history arraylist
     */
    public void updateData(){
        addDataToHistory(birthsHistory, Fox.class, births);
        addDataToHistory(birthsHistory, Dodo.class, births);
        addDataToHistory(birthsHistory, Rabbit.class, births);

        addDataToHistory(deathsHistory, Fox.class, deaths);
        addDataToHistory(deathsHistory, Dodo.class, deaths);
        addDataToHistory(deathsHistory, Rabbit.class, deaths);

        addDataToHistory(stepsHistory, Fox.class, steps);
        addDataToHistory(stepsHistory, Dodo.class, steps);
        addDataToHistory(stepsHistory, Rabbit.class, steps);
        addDataToHistory(stepsHistory, Hunter.class, steps);

        addDataToHistory(aliveHistory, Fox.class, alive);
        addDataToHistory(aliveHistory, Dodo.class, alive);
        addDataToHistory(aliveHistory, Rabbit.class, alive);
        addDataToHistory(aliveHistory, Hunter.class, alive);
    }

    /**
     * Convert all data to data that can be used for XCharts
     * @param list - History hashmap
     * @return
     */
    public double[] convertToGraphData(List<Double> list){
        // Limitedlist contains the last HISTORY_TURNS (100 standard) values of the history list.
        List<Double> limitedList = new ArrayList<>();
        // If the history list is empty, then fill it with data to prevent errors.
        if(list == null || list.isEmpty()){
            return new double[]{0};
        }
        // If the history list contains more than 100 values, then add the last 100 values to the limitedlist
        if(list.size() > HISTORY_TURNS) {
            for (int i = (int)HISTORY_TURNS; i > 0; i--) {
                limitedList.add(list.get(list.size() - i));
            }
        } else {
            limitedList = list;
        }
        // Convert limitedlist into a double array. This is needed to create an graph
        double[] returnDouble = new double[limitedList.size()];

        for (int i = 0; i < returnDouble.length; i++){
            returnDouble[i] = limitedList.get(i);
        }
        return returnDouble;
    }
}