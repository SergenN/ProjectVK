package com.github.projectvk.model;


import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 *
 */
public abstract class NaturalEntity implements Actor
{
    // Whether the entity is alive or not.
    private boolean alive;
    // The entity's field.
    private Field field;
    // The entity's position in the field.
    private Location location;
    // The entity's age
    private int age;
    //every entity needs some food?
    private int foodLevel;
    // Random nummer generator
    private static final Random rand = Randomizer.getRandom();

    private Statistics statistics;

    /**
     * Create a new animal at location in field.
     * f
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public NaturalEntity(Field field, Location location, int age)
    {
        alive = true;
        setField(field);
        setAge(age);
        setLocation(location);
    }

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
     * verkrijg alle prooi klassen
     * @return Class[] of prey entities
     */
    protected abstract Class[] getPrey();

    /**
     *
     * @return
     */
    protected abstract int getFoodDecayLevel();

    /**
     * Check if the Entity can breed/walk on grassland
     * @return canOverrideGras
     */
    protected abstract boolean canOverrideGras();

    /**
     * Get the minimal food needed for breed
     * @return int minimal needed food lvl
     */
    protected abstract int getMinimalBreedFood();

    /**
     * verkrijg de klasse van het dier dat op dit moment gebruik maakt van deze super klasse
     * Todo betere oplossing voor dit
     * @return klasse van het dier
     */
    protected abstract Class getEntityClass();

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation() {
        return  location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(Location newLocation)
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
    public Field getField(){
        return field;
    }

    public void setField(Field field){
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
        if (getClass() != Grass.class) statistics.addData(statistics.deaths, getEntityClass(), 1);
    }

    /**
     * Increase the age. This could result in the entity's death.
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
        return (getAge() >= getBreedingAge()) && getFoodLevel() >= getMinimalBreedFood();
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of entities (may be zero).
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
     * Make this entity more hungry. This could result in the entity's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    protected void setFoodLevel(int foodLevel){
        this.foodLevel = foodLevel;
    }

    protected int getFoodLevel(){
        return foodLevel;
    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findPrey()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object object = field.getObjectAt(where);
            if (Arrays.asList(getPrey()).isEmpty()){
                if(object == null) {
                    return where;
                }
                return null;
            }
            if(object != null){
                if (Arrays.asList(getPrey()).contains(object.getClass())){//replacing instanceof
                    NaturalEntity prey = (NaturalEntity)object;
                    if(prey.isAlive()){
                        prey.setDead();
                        setFoodLevel(getFoodDecayLevel());
                        return where;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this entity is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param actors A list to return newly born foxes.
     */
    public void giveBirth(List<Actor> actors)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();

        List<Location> free = field.getFreeAdjacentLocations(getLocation(), canOverrideGras());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);

            if(field.getObjectAt(loc) instanceof Grass) ((Grass) field.getObjectAt(loc)).setDead();
            try {
                actors.add((Actor)getEntityClass().getDeclaredConstructor(Boolean.class, Field.class, Location.class).newInstance(false, field, loc));
            } catch (NoSuchMethodException e){
                e.printStackTrace();
            } catch (InstantiationException e){
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (getEntityClass() != Grass.class) statistics.addData(statistics.births, getEntityClass(), 1);
    }

    /**
     * This is what the entity does most of the time
     * In the process, it might breed, die of hunger,
     * or die of old age.
     * @param actors A list to return newly born actors.
     */
    public void act(List<Actor> actors)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(actors);
            // Move towards a source of food if found.
            Location newLocation = findPrey();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation(), canOverrideGras());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
                if (getEntityClass() != Grass.class) statistics.addData(statistics.steps, getEntityClass(), 1);;
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
}
