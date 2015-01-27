package com.github.projectvk.model;

import java.util.Random;
import static com.github.projectvk.Main.propertiesFile;

/**
 * Provide control over the randomization of the simulation.
 */
public class Randomizer {
    // The default seed for control of randomization.
    private static int SEED = propertiesFile.getInt("general-SEED");
    // A shared Random object, if required.
    private static final Random rand = new Random(SEED);
    // Determine whether a shared random generator is to be provided.
    private static final boolean useShared = true;

    /**
     * Provide a random generator.
     * @return A random object.
     */
    public static Random getRandom() {
        if(useShared) {
            return rand;
        }
        else {
            return new Random();
        }
    }
    
    /**
     * Reset the randomization.
     * This will have no effect if randomization is not through
     * a shared Random generator.
     */
    public static void reset() {
        if(useShared) {
            rand.setSeed(SEED);
        }
    }
}
