package com.github.projectvk.model;

import java.util.*;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 */
public class Field
{
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    // The height and width of the field.
    private int height, width;
    // Storage for the animals.
    private Object[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param height The height of the field.
     * @param width The width of the field.
     */
    public Field(int height, int width) {
        this.height = height;
        this.width = width;
        field = new Object[height][width];
    }

    /**
     * Empty the field.
     */
    public void clear() {
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }

    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location) {
        field[location.getRow()][location.getCol()] = null;
    }

    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object animal, int row, int col) {
        place(animal, new Location(row, col));
    }

    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Object animal, Location location) {
        field[location.getRow()][location.getCol()] = animal;
    }

    /**
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location) {
        return getObjectAt(location.getRow(), location.getCol());
    }

    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col) {
        return field[row][col];
    }

    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location) {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location) {
        return getFreeAdjacentLocations(location, false);
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @param ignoreGrass ignore the grass and see it as free field.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location, boolean ignoreGrass) {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null || (ignoreGrass && (getObjectAt(next) instanceof Grass))) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location) {
        return freeAdjacentLocation(location, false);
    }

    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location, boolean ignoreGrass) {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location, ignoreGrass);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Get all locations adjacent to the given location which are empty
     * @param location the location where to check around
     * @param ignoreGrass ignore grass // grass will be added to the return
     * @return the List of empty locations around the given location
     */
    public List<Location> getOccupiedAdjacentLocations(Location location, boolean ignoreGrass){
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) != null || (ignoreGrass && !(getObjectAt(next) instanceof Grass))) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location) {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < height) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return the height of the field.
     * @return The height of the field.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth() {
        return width;
    }
}
