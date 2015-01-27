package com.github.projectvk.model;

import static com.github.projectvk.Main.propertiesFile;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 */
public class Fox extends NaturalEntity implements Sickness {
    // The fox it's natural prey
    private static Class[] PREY = {Dodo.class, Rabbit.class};
    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = true;
    // check if the animal is sick false by default
    private boolean isSick;

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
        setFoodLevel(getFoodDecayLevel());
        isSick = false;
        if (randomAge) {
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
        return propertiesFile.getInt("fox-MAX_AGE");
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     *
     * @return breeding age
     */
    @Override
    protected int getBreedingAge() {
        return propertiesFile.getInt("fox-BREEDING_AGE");
    }

    /**
     * Verkrijg de maximale litter grootte
     *
     * @return max litter grootte
     */
    @Override
    protected int getMaxLitterSize() {
        return propertiesFile.getInt("fox-MAX_LITTER_SIZE");
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     *
     * @return breeding age
     */
    @Override
    protected double getBreedingProbability() {
        return (propertiesFile.getIntTransformed("fox-BREEDING_PROBABILITY"));
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
        if(getRandom().nextDouble() <= getSicknessCatchProbability())isSick = true;
        if(isSick() && getAge() > getMaxAge()-5){
            setAge(getMaxAge()-5);
        }
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
     * aantal beurten dat het dier kan overleven zonder voedsel
     *
     * @return de food decay level
     */
    @Override
    protected int getFoodDecayLevel() {
        return propertiesFile.getInt("fox-FOOD_LEVEL");
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
        return propertiesFile.getInt("fox-BREED_FOODLEVEL");
    }

    /**
     * verkrijg de klasse van het dier dat op dit moment gebruik maakt van deze super klasse
     *
     * @return klasse van het dier
     */
    @Override
    protected Class getEntityClass() {
        return this.getClass();
    }

    /**
     * Get the chance the animal might become sick
     * @return double chance of sickness between 0 to 1
     */
    public double getSicknessCatchProbability(){
        return (propertiesFile.getIntTransformed("rabbit-SICKNESS_CATCH_PROBABILITY"));
    }
}
