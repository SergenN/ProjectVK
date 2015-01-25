package com.github.projectvk.model;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 */
public class Fox extends NaturalEntity {
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int FOOD_LEVEL = 9;
    // The fox it's natural prey
    private static final Class[] PREY = {Dodo.class, Rabbit.class};
    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = true;
    // the minimum foodlevel an entity needs to breed
    private static final int BREED_FOODLEVEL = 5;


    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Fox(Boolean randomAge, Field field, Location location) {
        super(field, location, 0);
        setFoodLevel(FOOD_LEVEL);
        if (randomAge) {
            setAge(getRandom().nextInt(MAX_AGE));
            setFoodLevel(getRandom().nextInt(FOOD_LEVEL));
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
     * verkrijg de minimum leeftijd om te broeden
     *
     * @return breeding age
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

    /**
     * verkrijg de foodDecayLevel
     *
     * @return
     */
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
        return this.getClass();
    }
}
