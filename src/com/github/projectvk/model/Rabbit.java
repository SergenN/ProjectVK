package com.github.projectvk.model;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 */
public class Rabbit extends NaturalEntity implements Sickness {
    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // the maximum number a rabbit can move without food before dying
    private static final int FOOD_LEVEL = 8;
    // The rabbits it's natural prey
    private static final Class[] PREY = {Grass.class};
    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = true;
    // the minimum foodlevel an entity needs to breed
    private static final int BREED_FOODLEVEL = 2;
    // chance other rabbits can get infected
    private static final double SICKNESS_CATCH_PROBABILITY = 0.90;

    private boolean isSick;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(Boolean randomAge, Field field, Location location)
    {
        super(field, location, 0);
        setFoodLevel(FOOD_LEVEL);
        isSick = false;
        if(randomAge) {
            if(getRandom().nextDouble() <= SICKNESS_CATCH_PROBABILITY)setSick();
            setAge(getRandom().nextInt(getMaxAge()));
            setFoodLevel(getRandom().nextInt(FOOD_LEVEL));
        }
    }

    /**
     * verkrijg de maximum leeftijd van een dier
     * @return int maximum age
     */
    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     * @return breeding age
     */
    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Verkrijg de maximale litter grootte
     * @return max litter grootte
     */
    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
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
     * verkrijg de klasse van het dier dat op dit moment gebruik maakt van deze super klasse
     * Todo betere oplossing voor dit
     *
     * @return klasse van het dier
     */
    @Override
    protected Class getEntityClass() {
        return getClass();
    }

    /**
     * Check if the entity is sick
     *
     * @return true if the entity is sick
     */
    @Override
    public boolean isSick() {
        return isSick;
    }

    /**
     * Set the entity to sick
     * There is no cure for this sickness.
     */
    @Override
    public void setSick() {
        if(getRandom().nextDouble() <= SICKNESS_CATCH_PROBABILITY)isSick = true;
        if(isSick() && getAge() > getMaxAge()-5){
            setAge(getMaxAge()-5);
        }
    }

}
