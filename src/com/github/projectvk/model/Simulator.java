package com.github.projectvk.model;

import com.github.projectvk.Main;
import com.github.projectvk.controller.Controller;

import java.util.*;

import static com.github.projectvk.Main.propertiesFile;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 */
public class Simulator implements Runnable {
    //List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    public int step = 0;
    // Check if the simulator is running
    private boolean running = false;
    //the step when the next weather event is taking place
    public int nextWeatherEvent;
    // The steps the simulator has to run to stop
    private int toStep = 0;
    // The controller
    private Controller controller;

    /**
     * Creates a Simulator Object with a Controller Connection
     * @param controller the control panel
     */
    public Simulator(Controller controller) {
        this.controller = controller;
        this.nextWeatherEvent = controller.getWeatherStep();
        actors = new ArrayList<>();
        System.out.println(controller.getFieldHeight()+" , "+ controller.getFieldWidth());
        field = controller.getField();
        reset();
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep() {

        if(controller.getCurrentSteps() >= nextWeatherEvent){
            controller.randomWeather();
            System.out.println("Changing Weather..");
            nextWeatherEvent = controller.getWeatherStep();
        }

        step++;

        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<>();
        // Let all entities act.
            try {
                for (Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
                    Actor actor = it.next();
                    actor.act(newActors);
                    if (actor instanceof NaturalEntity) {
                        NaturalEntity naturalEntity = (NaturalEntity) actor;

                        if (!naturalEntity.isAlive()) {
                            it.remove();
                        }
                    }
                }
            } catch (ConcurrentModificationException e) {
                simulateOneStep();
        }
        actors.addAll(newActors);
        controller.updateData();

        // Add the newly born foxes and rabbits to the main lists.
        //
        controller.showStatus(step);


        //view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        controller.showStatus(step);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getHeight(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= propertiesFile.getIntTransformed("general-FOX_CREATION_PROBABILITY")) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= propertiesFile.getIntTransformed("general-RABBIT_CREATION_PROBABILITY")) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= propertiesFile.getIntTransformed("general-GRASS_CREATION_PROBABILITY")) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(true, field, location);
                    actors.add(grass);
                }
                else if(rand.nextDouble() <= propertiesFile.getIntTransformed("general-DODO_CREATION_PROBABILITY")) {
                    Location location = new Location(row, col);
                    Dodo dodo = new Dodo(true, field, location);
                    actors.add(dodo);
                }
                else if(rand.nextDouble() <= propertiesFile.getIntTransformed("general-HUNTER_CREATION_PROBABILITY")) {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(field, location);
                    actors.add(hunter);
                }
            }
        }
    }

    /**
     * Start a new thread that runs the run method
     */
    public void start() {
        this.toStep = -1;
        running = true;
        new Thread(this).start();
        controller.disableButtons();
    }

    /**
     * Start a new thread that runs the run method
     * @param toStep the step till which the thread has to run
     */
    public void start(int toStep) {
        this.toStep = toStep;
        running = true;
        new Thread(this).start();
        controller.disableButtons();
    }

    /**
     * Stop the simulation
     */
    public void stop() {
        running = false;
        controller.disableButtons();
    }

    /**
     * decerement the simulation steps till 0
     */
    public void decrementStep() {
        if (toStep > 0){
            toStep--;
        }
    }

    /**
     * check if the simulation is running
     * @return true if the simulation is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Start the simulation by creating a new Thread.
     */
    @Override
    public void run() {
        while (running && controller.getFieldStats().isViable(field)) {
            if (toStep < 0 || toStep != -1) {
                Main.getSimulator().simulateOneStep();
                try {
                    Thread.sleep(propertiesFile.getInt("general-STEP_SPEED"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                decrementStep();
            }
            if (toStep == 0) {
                stop();
            }
        }
    }

    /**
     * Get the current field processor
     * @return field
     */
    public Field getField() {
        return field;
    }

}
