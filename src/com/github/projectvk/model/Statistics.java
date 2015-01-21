package com.github.projectvk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thijs on 21-1-2015.
 */
public class Statistics {
    // Bepaal voor hoe lang de geschiedenis van de data moet worden (standaard laatste 100 turns)
    private static final double HISTORY_TURNS = 100;

    // Variables containing the count of data of one turn
    public static List<Double> fox_birth = new ArrayList<Double>();
    public static List<Double> dodo_birth = new ArrayList<Double>();
    public static List<Double> rabbit_birth = new ArrayList<Double>();

    public static List<Double> fox_steps = new ArrayList<Double>();
    public static List<Double> dodo_steps = new ArrayList<Double>();
    public static List<Double> rabbit_steps = new ArrayList<Double>();

    public static List<Double> fox_death = new ArrayList<Double>();
    public static List<Double> dodo_death = new ArrayList<Double>();
    public static List<Double> rabbit_death = new ArrayList<Double>();

    // Contains the data history. (data of last HISTORY_TURNS turns)
    public static List<Double> fox_birth_history = new ArrayList<Double>();
    public static List<Double> dodo_birth_history = new ArrayList<Double>();
    public static List<Double> rabbit_birth_history = new ArrayList<Double>();

    public static List<Double> fox_steps_history = new ArrayList<Double>();
    public static List<Double> dodo_steps_history = new ArrayList<Double>();
    public static List<Double> rabbit_steps_history = new ArrayList<Double>();

    public static List<Double> fox_death_history = new ArrayList<Double>();
    public static List<Double> dodo_death_history = new ArrayList<Double>();
    public static List<Double> rabbit_death_history = new ArrayList<Double>();


    // Methods to fill the data arrays
    public static void addData(List<Double> list, double amount){
        if(list.isEmpty()){
            list.add(0.0);
        }
        list.set(0, list.get(0) + amount);
    }

    public static void addDataToHistory(List<Double> list, List<Double> source){
//      Setup birth data for graph
        if(list.size() >= HISTORY_TURNS) {
            list.remove(0);
            list.add(source.get(0));
        } else {
            list.add(source.get(0));
        }
        source.clear();
    }

    public static void updateData(){
        addDataToHistory(fox_birth_history, fox_birth);
        addDataToHistory(dodo_birth_history, dodo_birth);
        addDataToHistory(rabbit_birth_history, rabbit_birth);

        addDataToHistory(fox_death_history, fox_death);
        addDataToHistory(dodo_death_history, dodo_death);
        addDataToHistory(rabbit_death_history, rabbit_death);

        addDataToHistory(fox_steps_history, fox_steps);
        addDataToHistory(dodo_steps_history, dodo_steps);
        addDataToHistory(rabbit_steps_history, rabbit_steps);
    }

    public static double[] convertToGraphData(List<Double> list){
        double[] returnDouble = new double[list.size()];

        for (int i = 0; i < returnDouble.length; i++){
            returnDouble[i] = list.get(i);
        }

        return returnDouble;
    }
}