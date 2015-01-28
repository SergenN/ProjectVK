package com.github.projectvk.model;

import com.github.projectvk.controller.Controller;

import java.util.Random;

/**
 * Created by Thijs on 26-1-2015.
 */
public class Weather {
    public static String weatherType;
    private Controller controller;
    private int changeWeatherStep;
    private Random rand = new Random();

    private static int MIN_WEATHER_STEP = 20;
    private static int MAX_WEATHER_STEP = 50;

    /**
     * Constructor to make a Weather model
     *
     * @param controller - the linked controller
     */
    public Weather(Controller controller){
        this.controller = controller;
        randomWeather();
    }

    /**
     * Change weather randomly by chance
     */
    public void randomWeather(){
        // Chances of a particular type of weather
        double randomDouble = Math.random();

        if(randomDouble < 0.5){
            changeWeather("Sunny");
        } else if (randomDouble < 0.75){
            changeWeather("Snow");
        } else {
            changeWeather("Rain");
        }
    }

    /**
     * This method changes the current weather. It will call the changeIcon method and the changeStats method. It will also decide when the next change in weather will occur.
     *
     * @param weatherType - the linked controller
     */
    public void changeWeather(String weatherType){
        setWeatherType(weatherType);

        // Change text in controlPanel and play a song in Audio class
        controller.changeWeatherText(weatherType);
        controller.playSound("audio/" + weatherType + ".wav");

        // Decide when the next weather change occurs. This needs a minimum and maximum step (see statics)
        changeWeatherStep = controller.getCurrentSteps() + rand.nextInt(MAX_WEATHER_STEP) + MIN_WEATHER_STEP;

        // DEBUG
        System.out.println("Next weather change at step " + changeWeatherStep);

        switch (weatherType) {
            case "sunny":
                System.out.println("sunny");
                break;
            case "snowstorm":

                break;
            case "rain":

                break;
        }
    }

    /**
     * Returns WeatherType
     * @return weatherType - Type of weather. By example "Sunny"
     */
    public String getWeatherType() {
        return weatherType;
    }

    /**
     * Set the weatherType String. By example: Sunny
     */
    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    /**
     * Returns changeWeatherStep
     * @return changeWeatherStep - This contains the value of the step in which the weather changes
     */
    public int getChangeWeatherStep(){
        return changeWeatherStep;
    }
}
