package com.github.projectvk.model;

import java.util.List;

public interface Actor {

    /**
     * verkrijg de locatie van deze actor
     * @return Locatie
     */
    public Location getLocation();

    /**
     * zet de locatie van deze actor
     * @param loc Locatie
     */
    public void setLocation(Location loc);

    /**
     * verkrijg het field van deze actor
     * @return Field
     */
    public Field getField();

    /**
     * zet het field van deze actor
     * @param field
     */
    public void setField(Field field);

    /**
     * acteer
     * @param newActors
     */
    public void act(List<Actor> newActors);


}
