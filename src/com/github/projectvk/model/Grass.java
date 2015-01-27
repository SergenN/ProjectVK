package com.github.projectvk.model;

import java.util.List;
import static com.github.projectvk.Main.propertiesFile;

/**
 * Created by Sergen on 22-1-2015.
 * Class: NaturalEntity
 */
public class Grass extends NaturalEntity {
    // The entity it's natural prey
    private static final Class[] PREY = {};
    // Can the animal walk/breed on grass (will remove grass
    private static final boolean IGNORE_GRASS = false;

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
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(Boolean randomAge, Field field, Location location) {
        super(field, location, 0);
        if(randomAge) {
            int newAge = getMaxAge() <= 0 ? 0 : getRandom().nextInt(getMaxAge());
            setAge(newAge);
        }
    }

    /**
     * verkrijg de maximum leeftijd van een dier
     *
     * @return int maximum age
     */
    @Override
    protected int getMaxAge() {
        return getBreedingAge();
    }

    /**
     * Get the age of the entity when it's ready to breed
     * @return the age when the entity is able to breed.
     */
    @Override
    protected int getBreedingAge() {
        return propertiesFile.getInt("grass-SPREAD_AGE");
    }

    /**
     * Verkrijg de maximale litter grootte
     *
     * @return max litter grootte
     */
    @Override
    protected int getMaxLitterSize() {
        return propertiesFile.getInt("grass-SEEDLING_PROBABILITY");
    }

    /**
     * verkrijg de minimum leeftijd om te broeden
     *
     * @return breeding age
     */
    @Override
    protected double getBreedingProbability() {
        return (propertiesFile.getIntTransformed("grass-SPREAD_PROBABILITY"));
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
     * verkrijg de foodDecayLevel (aantal beurten dat het dier kan overleven zonder voedsel)
     *
     * @return de food decay level
     */
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
        return propertiesFile.getInt("grass-BREED_FOODLEVEL");
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
     * increase the age of this entity BUT stop increasing once it reaches breeding age
     * @return age of the entity
     */
    @Override
    public int incrementAge(){
        if(!(getAge() >= getBreedingAge())){
            setAge(getAge() + 1);
        }
        return getAge();
    }

    /**
     * acteer
     *
     * @param actors all actors
     */
    @Override
    public void act(List<Actor> actors) {
        incrementAge();
        if(isAlive()) {
            super.giveBirth(actors);
        }
    }
}
