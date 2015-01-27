package com.github.projectvk.model;

import com.github.projectvk.controller.Controller;

import java.util.Random;

/**
 * Created by Thijs on 26-1-2015.
 */
public class Weather {
    private String weatherType;
    private Controller controller;
    private int changeWeatherStep;
    private Random rand = new Random();

    private static int MIN_WEATHER_STEP = 100;
    private static int MAX_WEATHER_STEP = 200;

    public Weather(Controller controller){
        this.controller = controller;
        randomWeather();
    }

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

    // This method changes the current weather. It will call the changeIcon method and the changeStats method
    public void changeWeather(String weatherType){
        setWeatherType(weatherType);
        controller.changeWeatherText(weatherType);

        // Decide when the next weather change occurs. This needs a minimum and maximum step (see statics)
        changeWeatherStep = controller.getCurrentSteps() + rand.nextInt(MAX_WEATHER_STEP) + MIN_WEATHER_STEP;

        System.out.println(changeWeatherStep);

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

    // Getter and setter for the weather type
    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public int getChangeWeatherStep(){
        return changeWeatherStep;
    }
}
