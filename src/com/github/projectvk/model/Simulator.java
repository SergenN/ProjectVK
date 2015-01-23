package com.github.projectvk.model;

import com.github.projectvk.Main;
import com.github.projectvk.controller.ButtonHandler;
import com.github.projectvk.controller.Controller;
import com.github.projectvk.view.SimulatorView;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 */
public class Simulator implements Runnable
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
    // the probability grass will spawn in any given position
    private static final double GRASS_CREATION_PROBABILITY = 0.50;

    //List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation. ----> Simulatorview --> Init
    private SimulatorView view;
    //Init the thread runner needed for ControlPanel class
    //private ThreadRunner runner;

    //
    private boolean running = false;
    private boolean infinite = false;
    private int toStep = 0;

    // Buttonhandler to catch events buttons create
    //private ButtonHandler buttonHandler;

    private Controller controller;

    /**
     * Construct a simulation field with default size.
     */
    /**public Simulator()
    {
        //this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }**/

    /**
     * Creates a Simulator Object with a Controller Connection
     * @param controller
     */
    public Simulator(Controller controller)
    {

        this.controller = controller;
        //controller.setSimulator(this);

        actors = new ArrayList<Actor>();

        //this.field = field;

        // Creates new Field, Fetches new Field sizes from controller
        field = new Field(controller.getFieldHeight(), controller.getFieldWidth());
        System.out.println(controller.getFieldHeight()+" , "+ controller.getFieldWidth());
        //field = new Field(120, 80);
       // controller.disableButtons();

        //System.out.println(buttonHandler.toString());

        /**///view =
        /**///view.getControlPanel().disableButton();

        //runner = new ThreadRunner(this);

        // Create a view of the state of each location in the field.

        // Setup a valid starting point.
        reset();
    }


    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<Actor>();
        // Let all entities act.
            try {
                if(newActors != null) {
                    for (Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
                        Actor actor = it.next();

                        actor.act(newActors);

                        if (actor instanceof NaturalEntity) {
                            NaturalEntity naturalEntity = (NaturalEntity) actor;

                            if (!naturalEntity.isAlive()) {
                                it.remove();
                            }
                        }

                        if (actor instanceof Hunter) {
                            Hunter hunter = (Hunter) actor;
                            if (hunter.isHome()) {
                                it.remove();
                            }
                        }
                    }
                }
            } catch (ConcurrentModificationException e) {
                simulateOneStep();
        }
        actors.addAll(newActors);
        Statistics.updateData();

        //TODO check if area is full of rabbits and make the hunters return.
        // Add the newly born foxes and rabbits to the main lists.
        //
        controller.showStatus(step, field);

        //view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        controller.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getHeight(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(true, field, location);
                    actors.add(grass);
                }
                else if(rand.nextDouble() <= DODO_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Dodo dodo = new Dodo(true, field, location);
                    actors.add(dodo);
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

    /**
     * Start a new thread that runs the run method
     */
    public void start(){
        this.toStep = -1;
        running = true;
        new Thread(this).start();
        controller.disableButtons();
    }

    /**
     * Start a new thread that runs the run method
     */
    public void start(int toStep){
        this.toStep = toStep;
        running = true;
        new Thread(this).start();
        controller.disableButtons();
    }


    public void stop(){
        running = false;
        controller.disableButtons();
    }

    public void decrementStep(){
        if (toStep > 0){
            toStep--;
        }
    }

    public boolean isRunning(){
        return running;
    }

    @Override
    public void run() {
        while (running) {
            if(toStep < 0 || toStep != -1) {
                Main.getSimulator().simulateOneStep();
                //this.simulateOneStep();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                decrementStep();
            }
            if(toStep == 0){
                stop();
            }
        }
    }
}
