package com.github.projectvk.model.file;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergen on 27-1-2015.
 */
public class PropertiesHandler extends PropertiesFile{

    public PropertiesHandler(){
        super("Properties.txt");
        initializeProperties();
    }

    private void initializeProperties(){
        getInt("fox-BREEDING_AGE",15);
        getInt("fox-MAX_AGE",150);
        getInt("fox-BREEDING_PROBABILITY",12);
        getInt("fox-MAX_LITTER_SIZE",4);
        getInt("fox-FOOD_LEVEL",15);
        getInt("fox-BREED_FOODLEVEL",2);
        getInt("fox-SICKNESS_CATCH_PROBABILITY",50);

        getInt("rabbit-BREEDING_AGE",5);
        getInt("rabbit-MAX_AGE", 40);
        getInt("rabbit-BREEDING_PROBABILITY",12);
        getInt("rabbit-MAX_LITTER_SIZE",4);
        getInt("rabbit-FOOD_LEVEL",8);
        getInt("rabbit-BREED_FOODLEVEL",2);
        getInt("rabbit-SICKNESS_CATCH_PROBABILITY",90);

        getInt("dodo-BREEDING_AGE", 7);
        getInt("dodo-MAX_AGE",60);
        getInt("dodo-BREEDING_PROBABILITY",10);
        getInt("dodo-MAX_LITTER_SIZE",5);
        getInt("dodo-FOOD_LEVEL",12);
        getInt("dodo-BREED_FOODLEVEL",7);
        getInt("dodo-SICKNESS_CATCH_PROBABILITY",0);

        getInt("grass-SPREAD_AGE",3);
        getInt("grass-SPREAD_PROBABILITY",30);
        getInt("grass-SEEDLING_PROBABILITY",5);
        getInt("grass-BREED_FOODLEVEL",0);

        getInt("general-FOX_CREATION_PROBABILITY",2);
        getInt("general-RABBIT_CREATION_PROBABILITY",8);
        getInt("general-DODO_CREATION_PROBABILITY",15);
        getInt("general-HUNTER_CREATION_PROBABILITY",5);
        getInt("general-GRASS_CREATION_PROBABILITY",50);
        getInt("general-SEED", 111);
        getInt("general-STEP_SPEED",100);
    }

    /**
     * This will take all properties from the properties file
     * split the keys up in category, key and value
     * @return a hashmap of the categories linked to a hashmap with keys and values
     */
    public HashMap<String, HashMap<String, Integer>> getCategories(){
        HashMap<String, HashMap<String, Integer>> categorizedMap = new HashMap<>();
        Map<String, String> map = returnMap();
        for(String key : map.keySet()){
            int value = saveParseInt(map.get(key));
            String[] keys = key.split("-");
            if(!categorizedMap.containsKey(keys[0])){
                categorizedMap.put(keys[0], new HashMap<>());
            }
            categorizedMap.get(keys[0]).put(keys[1],value);
        }
        return categorizedMap;
    }

    /**
     * This function will try to parse an int and catch eventual mistakes and return 0 in that case
     * @param i the string to be parsed
     * @return an int or 0 if failed
     */
    public int saveParseInt(String i){
        int toReturn = 0;
        try {
            toReturn = Integer.parseInt(i);
        }catch (NumberFormatException ignored){}
        return toReturn;
    }

    /**
     * Verkrijg de property en verander het naar een double voor de
     * veiligheid return 0.01 als de property 0 is want delen door 0 kan niet.
     * @return 0.01 als de property 0 is anders property / 100
     */
    public double getIntTransformed(String property){
        return getInt(property) == 0 ? 0 : (getInt(property) / 100.0);
    }
}
