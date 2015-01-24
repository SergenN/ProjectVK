package com.github.projectvk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Thijs on 21-1-2015.
 */
public class Statistics {

    // Bepaal voor hoe lang de geschiedenis van de data moet worden (standaard laatste 100 turns)
    private static final double HISTORY_TURNS = 70;

    public static HashMap<Class, ArrayList<Double>> deaths = new HashMap<Class, ArrayList<Double>>();
    public static HashMap<Class, ArrayList<Double>> births = new HashMap<Class, ArrayList<Double>>();
    public static HashMap<Class, ArrayList<Double>> steps = new HashMap<Class, ArrayList<Double>>();

    public static HashMap<Class, ArrayList<Double>> deathsHistory = new HashMap<Class, ArrayList<Double>>();
    public static HashMap<Class, ArrayList<Double>> birthsHistory = new HashMap<Class, ArrayList<Double>>();
    public static HashMap<Class, ArrayList<Double>> stepsHistory = new HashMap<Class, ArrayList<Double>>();


    // Methods to fill the data arrays
    public static void addData(HashMap<Class, ArrayList<Double>> list, Class animal, double amount){
        if(list.get(animal) == null || list.get(animal).isEmpty()){
            list.put(animal, new ArrayList<Double>());
            list.get(animal).add(0.0);
        }
        list.get(animal).set(0, list.get(animal).get(0) + amount);
    }

    public static void addDataToHistory(HashMap<Class, ArrayList<Double>> list, Class animal, HashMap<Class, ArrayList<Double>> source){
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
        if(list.size() >= HISTORY_TURNS) {
            list.get(animal).add(source.get(animal).get(0));
            list.get(animal).remove(0);
        } else {
            list.get(animal).add(source.get(animal).get(0));
        }
        source.get(animal).clear();
    }

    public static void updateData(){
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

    public static double[] convertToGraphData(List<Double> list){
        if(list == null || list.isEmpty()){
            return new double[]{0,0,0};
        }

        double[] returnDouble = new double[list.size()];

        for (int i = 0; i < returnDouble.length; i++){
            returnDouble[i] = list.get(i);
        }

        return returnDouble;
    }
}