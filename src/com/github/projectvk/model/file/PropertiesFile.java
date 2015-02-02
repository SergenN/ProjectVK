package com.github.projectvk.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesFile {
    private String fileName;
    private Properties props = new Properties();

    /**
     * Create a new properties file with the given name
     * @param fileName name of the new propertiesFile
     */
    public PropertiesFile(String fileName) {
        File file = new File(fileName);

        this.fileName = file.getPath();

        try {
            if (file.exists()) {
                load();
            } else {
                save();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load a new propertiesfile into the system
     * @throws IOException throws an exception if something goes wrong with the stream closing or file input stream
     */
    public void load() throws IOException {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(fileName);
            props.load(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * save the propertiesfile from system to file
     */
    public void save() {
        FileOutputStream stream = null;

        try {
            stream = new FileOutputStream(fileName);
            props.store(stream, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Get all properties in the properties file in a map
     * @return Map of all keys in key = value order
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> returnMap() {
        return (Map<String, String>) props.clone();
    }

    /**
     * check if the propertiesFile contains the given key
     * @param var key to search for
     * @return true of it contains the key
     */
    public boolean containsKey(String var) {
        return props.containsKey(var);
    }

    /**
     * get the property from the given key
     * @param var key to search for
     * @return the value of the key found
     */
    public String getProperty(String var) {
        return props.getProperty(var);
    }

    /**
     * get an int by the given property from the propertiesfile
     * @param key property to search for
     * @return found property; 0 if property was not found
     */
    public int getInt(String key) {
        if (containsKey(key)) {
            return Integer.parseInt(getProperty(key));
        }
        return 0;
    }

    /**
     * Get a value from the propertiesFile
     * if this does not exist it will create one and return the default value
     * @param key the key you search for
     * @param value default value
     * @return the property requested
     */
    public int getInt(String key, int value) {
        if (containsKey(key)) {
            return Integer.parseInt(getProperty(key));
        }
        setInt(key, value);
        return value;

    }

    /**
     * Set an existing int in the file
     * @param key key of the property
     * @param value new value of the property
     */
    public void setInt(String key, int value) {
        props.put(key, String.valueOf(value));
        save();
    }

}