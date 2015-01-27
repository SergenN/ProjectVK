package com.github.projectvk.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Hunter implements Actor {

    private Field field;
    private Location location;
    private static final Class[] prey = {Dodo.class, Rabbit.class, Fox.class};
    private static final boolean overrideGrass = true;
    private Statistics statistics;

    /**
     * Create a Hunter.
     *
     * @param randomMood If true, the fox will have random mood level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(boolean randomMood, Field field, Location location)
    {
        this.field = field;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location loc) {
        if(location != null) {
            getField().clear(location);
        }
        location = loc;
        getField().place(this, loc);
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * This is what the entity does most of the time
     * In the process, it might breed, die of hunger,
     * or die of old age.
     * @param actors A list to return newly born actors.
     */
    public void act(List<Actor> actors)
    {
        // Move towards a source of food if found.
        Location newLocation = findPrey();
        if(newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation(), canOverrideGras());
        }
        // See if it was possible to move.
        if(newLocation != null) {
            setLocation(newLocation);
        }
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
                        return where;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the prey this hunter is hunting for
     * @return Class[] of prey the hunter can hunt
     */
    private Class[] getPrey(){
        return prey;
    }

    /**
     * Check if the hunter can step on grass
     * @return if the hunter can step on grass
     */
    private boolean canOverrideGras(){
        return overrideGrass;
    }
}
