package com.github.projectvk.model;

import static com.github.projectvk.Main.propertiesFile;

/**
 * Created by Sergen on 19-1-2015.
 * Class: Dodo
 */
public class Dodo extends NaturalEntity {
    // The rabbits it's natural prey
    private static final Class[] PREY = {Grass.class};
    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = false;

    /**
     * Create a new Dodo
     * @param randomAge if the age should be random or max
     * @param field field handler
     * @param location location where to spawn
     */
    public Dodo(Boolean randomAge, Field field, Location location) {
        super(field, location, 0);
        setFoodLevel(getFoodDecayLevel());
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
        return propertiesFile.getInt("dodo-MAX_AGE");
    }

    /**
     * get the age at which the animal is able to breed
     *
     * @return breeding age
     */
    @Override
    protected int getBreedingAge() {
        return propertiesFile.getInt("dodo-BREEDING_AGE");
    }

    /**
     * Verkrijg de maximale litter grootte
     *
     * @return max litter grootte
     */
    @Override
    protected int getMaxLitterSize() {
        return propertiesFile.getInt("dodo-MAX_LITTER_SIZE");
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     *
     * @return breeding age
     */
    @Override
    protected double getBreedingProbability() {
        return (propertiesFile.getIntTransformed("dodo-BREEDING_PROBABILITY"));
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
     * get the foodlevel with which the animal can survive before having to eat again
     * @return foodlevel
     */
    @Override
    protected int getFoodDecayLevel() {
        return propertiesFile.getInt("dodo-FOOD_LEVEL");
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
        return propertiesFile.getInt("dodo-BREED_FOODLEVEL");
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
}
