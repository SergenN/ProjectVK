package com.github.projectvk.model;

import java.util.List;

/**
 * Created by Sergen on 19-1-2015.
 */
public class Dodo extends Animal implements Actor{

    // The age at which a rabbit can start to breed.     xxxx
    private static final int BREEDING_AGE = 7;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 170;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.10;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;


    public Dodo(boolean randomAge, Field field, Location location){
        super(field, location, 0);
        if(randomAge) {
            setAge(getRandom().nextInt(getMaxAge()));
        }
    }

    /**
     * verkrijg de maximum leeftijd van een dier
     *
     * @return int maximum age
     */
    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * @return
     */
    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Verkrijg de maximale litter grootte
     *
     * @return max litter grootte
     */
    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     *
     * @return breeding age
     */
    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     *
     * @param newDodos A list to receive newly born animals.
     */
    @Override
    public void act(List<Actor> newDodos) {
        incrementAge();
        if(isAlive()) {
            giveBirth(newDodos);
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
                Statistics.addData(Statistics.dodo_steps, 1);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Check whether or not this dodo is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newDodos A list to return newly born dodos.
     */
    private void giveBirth(List<Actor> newDodos)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Dodo young = new Dodo(false, field, loc);
            newDodos.add(young);
        }
        Statistics.addData(Statistics.dodo_birth, 1);
    }

    public void setDead(){
        super.setDead();
        Statistics.addData(Statistics.dodo_death, 1);
    }

}
