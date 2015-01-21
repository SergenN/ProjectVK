package com.github.projectvk.model;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 *
 */
public abstract class Animal  
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age
    private int age;
    // Random nummer generator
    private static final Random rand = Randomizer.getRandom();


    // Stats
    private int deaths;
    private int births;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location, int age)
    {
        alive = true;
        setField(field);
        setAge(age);
        setLocation(location);
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract protected void act(List<Animal> newAnimals);

    /**
     * verkrijg de maximum leeftijd van een dier
     * @return int maximum age
     */
    abstract protected int getMaxAge();

    /**
     *
     * @return
     */
    abstract protected int getBreedingAge();

    /**
     * Verkrijg de maximale litter grootte
     * @return max litter grootte
     */
    protected abstract int getMaxLitterSize();

    /**
     * verkrijg de minimum leeftijd om te broeden
     * @return breeding age
     */
    protected abstract double getBreedingProbability();

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation() {
        return  location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive(){
        return alive;
    }

    /**
     * verkrijg de age van het dier.
     * @return int age
     */
    protected int getAge(){
        return age;
    }

    /**
     * zet de leeftijd van dit dier.
     * @param age de leeftijd dat gezet moet worden
     */
    protected void setAge(int age){
        this.age = age;
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField(){
        return field;
    }

    protected void setField(Field field){
        this.field = field;
    }

    /**
     * Krijg de random nummer generator
     * @return Random rand
     */
    protected Random getRandom(){
        return rand;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Increase the age. This could result in the animals's death.
     */
    protected int incrementAge()
    {
        setAge(getAge() + 1);
        if(getAge() > getMaxAge()) {
            setDead();
        }
        return age;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && getRandom().nextDouble() <= getBreedingProbability()) {
            births = getRandom().nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Add a birth of an animal
    */

    protected void addBirth()
    {

        births ++;
    }

    /**
     * Return the animal's births.
     * @return The animal's births.
     */
    protected int getBirths()
    {
        return births;
    }


}
