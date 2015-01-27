package com.github.projectvk.model;

import static com.github.projectvk.Main.propertiesFile;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 */
public class Rabbit extends NaturalEntity implements Sickness {
    // The rabbits it's natural prey
    private static final Class[] PREY = {Grass.class};
    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = false;
    // Check if the rabbit is sick; false on default
    private boolean isSick;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(Boolean randomAge, Field field, Location location) {
        super(field, location, 0);
        setFoodLevel(getFoodDecayLevel());
        isSick = false;
        if(randomAge) {
            if(getRandom().nextDouble() <= getSicknessCatchProbability())setSick();
            setAge(getRandom().nextInt(getMaxAge()));
            setFoodLevel(getRandom().nextInt(getFoodDecayLevel()));
        }
    }

    /**
     * verkrijg de maximum leeftijd van een dier
     * @return int maximum age
     */
    @Override
    protected int getMaxAge() {
        return propertiesFile.getInt("rabbit-MAX_AGE");
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     * @return breeding age
     */
    @Override
    protected int getBreedingAge() {
        return propertiesFile.getInt("rabbit-BREEDING_AGE");
    }

    /**
     * Verkrijg de maximale litter grootte
     * @return max litter grootte
     */
    @Override
    protected int getMaxLitterSize() {
        return propertiesFile.getInt("rabbit-MAX_LITTER_SIZE");
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     * @return breeding age
     */
    @Override
    protected double getBreedingProbability() {
        return (propertiesFile.getInt("rabbit-BREEDING_PROBABILITY") / 100.0);
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
        return propertiesFile.getInt("rabbit-FOOD_LEVEL");
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
        return propertiesFile.getInt("rabbit-BREED_FOODLEVEL");
    }

    /**
     * verkrijg de klasse van het dier dat op dit moment gebruik maakt van deze super klasse
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
        if(getRandom().nextDouble() <= getSicknessCatchProbability())isSick = true;
        if(isSick() && getAge() > getMaxAge()-5){
            setAge(getMaxAge()-5);
        }
    }

    /**
     * Get the chance the animal might become sick
     * @return double chance of sickness between 0 to 1
     */
    public double getSicknessCatchProbability() {
        return (propertiesFile.getInt("rabbit-SICKNESS_CATCH_PROBABILITY") / 100.0);
    }

}
