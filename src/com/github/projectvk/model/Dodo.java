package com.github.projectvk.model;

import java.util.List;

/**
 * Created by Sergen on 19-1-2015.
 */
public class Dodo extends NaturalEntity{

    // The age at which a rabbit can start to breed.     xxxx
    private static final int BREEDING_AGE = 7;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 60;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // the maximum number a rabbit can move without food before dying
    private static final int FOOD_LEVEL = 10;
    // The rabbits it's natural prey
    private static final Class[] PREY = {Grass.class};
    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = true;
    // the minimum foodlevel an entity needs to breed
    private static final int BREED_FOODLEVEL = 5;


    public Dodo(Boolean randomAge, Field field, Location location){
        super(field, location, 0);
        setFoodLevel(FOOD_LEVEL);
        if(randomAge) {
            setAge(getRandom().nextInt(getMaxAge()));
            setFoodLevel(getRandom().nextInt(getFoodDecayLevel()));
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
     * verkrijg alle prooi klassen
     *
     * @return Class[] of prey entities
     */
    @Override
    protected Class[] getPrey() {
        return PREY;
    }

    @Override
    protected int getFoodDecayLevel() {
        return FOOD_LEVEL;
    }

    /**
     * Check if the Entity can breed/walk on grassland
     *
     * @return canOverrideGras
     */
    @Override
    protected boolean canOverrideGras() {
        return IGNORE_GRASS;
    }

    /**
     * Get the minimal food needed for breed
     *
     * @return int minimal needed food lvl
     */
    @Override
    protected int getMinimalBreedFood() {
        return BREED_FOODLEVEL;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     *
     * @param newActors A list to receive newly born animals.
     */
    @Override
    public void act(List<Actor> newActors) {
        super.act(newActors, this.getClass());
    }

    public void setDead(){
        super.setDead();
        Statistics.addData(Statistics.dodo_death, 1);
    }

}
