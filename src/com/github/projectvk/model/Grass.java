package com.github.projectvk.model;

import java.util.List;

/**
 * Created by Sergen on 22-1-2015.
 */
public class Grass extends NaturalEntity {

    // after 5 turns grass is ready to spread
    private static final int SPREAD_AGE = 3;
    // probability of spreading at each turn
    private static final double SPREAD_PROBABILITY = 0.3;
    //amount of seedlings grass can seed if hitting spread age and spread probability
    private static final int SEEDLING_PROBABILITY = 5;
    // The entity it's natural prey
    private static final Class[] PREY = {};

    /**
     * verkrijg de klasse van het dier dat op dit moment gebruik maakt van deze super klasse
     * Todo betere oplossing voor dit
     *
     * @return klasse van het dier
     */
    @Override
    protected Class getEntityClass() {
        return getClass();
    }

    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = false;
    // the minimum foodlevel an entity needs to breed
    private static final int BREED_FOODLEVEL = 0;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(Boolean randomAge, Field field, Location location)
    {
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
        return SPREAD_AGE;
    }

    /**
     * @return
     */
    @Override
    protected int getBreedingAge() {
        return SPREAD_AGE;
    }

    /**
     * Verkrijg de maximale litter grootte
     *
     * @return max litter grootte
     */
    @Override
    protected int getMaxLitterSize() {
        return SEEDLING_PROBABILITY;
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     *
     * @return breeding age
     */
    @Override
    protected double getBreedingProbability() {
        return SPREAD_PROBABILITY;
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
        return 0;
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
     * Check if the Entity can breed/walk on grassland
     *
     * @return canOverrideGras
     */
    @Override
    protected boolean canOverrideGras() {
        return IGNORE_GRASS;
    }

    @Override
    public int incrementAge(){
        if(!(getAge() >= SPREAD_AGE)){
            setAge(getAge() + 1);
        }
        return getAge();
    }

    /**
     * acteer
     *
     * @param actors
     */
    @Override
    public void act(List<Actor> actors) {
        incrementAge();
        if(isAlive()) {
            super.giveBirth(actors);
            // Move towards a source of food if found.
            Location newLocation = findPrey();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
        }
    }
}
