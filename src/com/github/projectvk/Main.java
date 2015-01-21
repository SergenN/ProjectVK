package com.github.projectvk;

import com.github.projectvk.model.Simulator;

public class Main {

    private Simulator simulator;

    public static void main(String[] args){
        Main main = new Main();
        main.simulator = new Simulator();
    }

    public Simulator getSimulator(){
        return simulator;
    }

}
