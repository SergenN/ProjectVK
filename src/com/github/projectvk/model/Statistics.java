package com.github.projectvk.model;

import com.github.projectvk.controller.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Thijs on 21-1-2015.
 */
public class Statistics {
    private Controller controller;

    // Bepaal voor hoe lang de geschiedenis van de data moet worden (standaard laatste 100 turns)
    private static final double HISTORY_TURNS = 70;

    public static HashMap<Class, ArrayList<Double>> deaths = new HashMap<Class, ArrayList<Double>>();
    public static HashMap<Class, ArrayList<Double>> births = new HashMap<Class, ArrayList<Double>>();
    public static HashMap<Class, ArrayList<Double>> steps = new HashMap<Class, ArrayList<Double>>();

    private HashMap<Class, ArrayList<Double>> deathsHistory = new HashMap<Class, ArrayList<Double>>();
    private HashMap<Class, ArrayList<Double>> birthsHistory = new HashMap<Class, ArrayList<Double>>();
    private HashMap<Class, ArrayList<Double>> stepsHistory = new HashMap<Class, ArrayList<Double>>();

    public Statistics(Controller controller) {
        this.controller = controller;
    }

    // Get methods
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
        if(type.equals("deaths")) {
            return deaths;
        }
        if(type.equals("births")) {
            System.out.println("Inhoud: " + births);
            return births;
        }
        if(type.equals("steps")) {
            System.out.println("Inhoud: " + steps);
            return steps;
        }
        return null;
    }

    // Methods to fill the data arrays
    public static void addData(HashMap<Class, ArrayList<Double>> list, Class animal, double amount){
        if(list.get(animal) == null || list.get(animal).isEmpty()){
            list.put(animal, new ArrayList<Double>());
            list.get(animal).add(0.0);
        }
        list.get(animal).set(0, list.get(animal).get(0) + amount);
    }

    public static void resetData(){
        births.clear();
        steps.clear();
        deaths.clear();
    }

    // Add newly gained data to the history Arraylist
    public void addDataToHistory(HashMap<Class, ArrayList<Double>> list, Class animal, HashMap<Class, ArrayList<Double>> source){

        // Catch error codes
        if(list.get(animal) == null){
            list.put(animal, new ArrayList<Double>());
            list.get(animal).add(0.0);
        }

        if(source.get(animal) == null) {
            source.put(animal, new ArrayList<Double>());
            source.get(animal).add(0.0);
        }

        if (source.get(animal).isEmpty()){
            source.get(animal).add(0.0);
        }

//      Setup birth data for graph
//        if(list.size() >= HISTORY_TURNS) {
//            list.get(animal).add(source.get(animal).get(0));
//            list.get(animal).remove(0);
//        } else {
//            list.get(animal).add(source.get(animal).get(0));
//        }

        // Add data to the arraylist and remove data from the source
        list.get(animal).add(source.get(animal).get(0));
//        source.get(animal).clear();
    }

    // This will run at the end of each step. It is responsible for adding the gained data to the history arraylist
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
    }

    // Convert all data to data that can be used for XCharts
    public double[] convertToGraphData(List<Double> list){
        // Limitedlist contains the last HISTORY_TURNS (100 standard) values of the history list.
        List<Double> limitedList = new ArrayList<Double>();;

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