package com.github.projectvk.model;

import com.github.projectvk.controller.Location;
import com.github.projectvk.controller.Randomizer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Hunter implements Actor {

    // number of steps a hunter can go before his mood dropped so low he wants to go home.
    private static final int HUNTER_MOOD = 10;
    //Random nummer generator voor random Mood level
    private static final Random rand = Randomizer.getRandom();
    private int moodLevel;
    private Field field;
    private Location location;
    private boolean isHome;

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
        isHome = false;
        if(randomMood) {
            moodLevel = rand.nextInt(HUNTER_MOOD);
        }
        else {
            moodLevel = HUNTER_MOOD;
        }
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

    @Override
    public void act(List<Actor> newActors) {
        decrementMood();
        if(!isHome) {
            // Move towards a source of prey if found.
            Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                goHome();
            }
        }
    }

    /**
     * Look for rabbits or foxes adjacent to the current location.
     * Only the first live rabbit or fox is shot.
     * @return Where prey was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) {
                    rabbit.setDead();
                    moodLevel = HUNTER_MOOD;
                    return where;
                }
            }
            if(animal instanceof Fox){
                Fox fox = (Fox) animal;
                if(fox.isAlive()){
                    fox.setDead();
                    moodLevel = HUNTER_MOOD;
                    return where;
                }
            }
            if(animal instanceof Dodo){
                Dodo dodo = (Dodo) animal;
                if(dodo.isAlive()){
                    dodo.setDead();
                    moodLevel = HUNTER_MOOD;
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Make this hunter lose mood and eventually he goes home.
     */
    private void decrementMood()
    {
        moodLevel--;
        if(moodLevel <= 0) {
            goHome();
        }
    }

    /**
     * Hunter goes home.
     */
    private void goHome(){
        isHome = true;
        getField().clear(location);
        location = null;
        setField(null);
    }

    public boolean isHome(){
        return isHome;
    }

}
