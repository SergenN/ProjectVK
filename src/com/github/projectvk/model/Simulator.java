package com.github.projectvk.model;

import com.github.projectvk.view.SimulatorView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a dodo will spawn in any given grid position
    private static final double DODO_CREATION_PROBABILITY = 0.03;
    // The probability that a hunter will spawn in any given grid position
    private static final double HUNTER_CREATION_PROBABILITY = 0.05;

    // List of animals in the field.
    private List<Animal> animals;
    //List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    //Init the thread runner needed for ControlPanel class
    //private ThreadRunner runner;

    //Statistics animals
    private List<Double> deaths = new ArrayList<Double>();
    public static List<Double> births = new ArrayList<Double>();
    private List<Double> eating = new ArrayList<Double>();

    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        animals = new ArrayList<Animal>();
        actors = new ArrayList<Actor>();
        field = new Field(depth, width);

        //runner = new ThreadRunner(this);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, new Color(0, 150, 136));
        view.setColor(Fox.class, new Color(81, 45, 168));
        view.setColor(Dodo.class, Color.green);
        view.setColor(Hunter.class, Color.gray);
        // Setup a valid starting point.
        reset();

    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */



    public void simulateOneStep()
    {
        int totalbirths = 0;

        step++;

        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<Actor>();
        // Let all rabbits/foxes act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();

            actor.act(newActors);
            if(actor instanceof Animal){
                Animal animal = (Animal) actor;
                totalbirths += animal.getBirths();

                if(! animal.isAlive()){
                    it.remove();
                }
            }

            if(actor instanceof Hunter){
                Hunter hunter = (Hunter)actor;
                if(hunter.isHome()) {
                    it.remove();
                }
            }
            actors.addAll(newActors);
        }

        // Setup birth data for graph
        if(births.size() >= 100) {
            births.remove(0);
            births.add((double)totalbirths);
        } else {
            births.add((double)totalbirths);
        }

        System.out.println(births);

        //TODO check if area is full of rabbits and make the hunters return.

        // Add the newly born foxes and rabbits to the main lists.
        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    animals.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    animals.add(rabbit);
                }

                else if(rand.nextDouble() <= DODO_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Dodo dodo = new Dodo(true, field, location);
                    animals.add(dodo);
                }
                else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(true, field, location);
                    actors.add(hunter);
                }
                // else leave the location empty.
            }
        }
    }
}
