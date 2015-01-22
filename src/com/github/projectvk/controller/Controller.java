package com.github.projectvk.controller;

import com.github.projectvk.model.Simulator;
import com.github.projectvk.view.BirthsGraphView;

import com.github.projectvk.view.DeathsGraphView;
import com.github.projectvk.view.StepsGraphView;



/**
 * Created by kevin on 22-1-15.
 */
public class Controller {

    private Simulator simulator;


    public Controller(Simulator simulator){

        this.simulator = simulator;


    }

    protected void ControllerDo(String doThis) {

        System.out.println(doThis);
        if (doThis == "plusEen") simulator.start(1);
        if (doThis == "plusHonderd") simulator.start(100);
        if (doThis == "stop") simulator.stop();
        if (doThis == "start") simulator.start();
        if (doThis == "longSim") simulator.start(1000);
        if (doThis == "birthsStat") new BirthsGraphView();
        if (doThis == "deathsStat") new DeathsGraphView();
        if (doThis == "stepsStat") new StepsGraphView();


    }

}
