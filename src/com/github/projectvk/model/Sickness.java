package com.github.projectvk.model;

/**
 * Created by Sergen on 26-1-2015.
 */
public interface Sickness {
    /**
     * Check if the entity is sick
     * @return true if the entity is sick
     */
    public boolean isSick();

    /**
     * Set the entity to sick
     * There is no cure for this sickness.
     */
    public void setSick();
}
