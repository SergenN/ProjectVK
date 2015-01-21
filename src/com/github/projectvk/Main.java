package com.github.projectvk;

import com.github.projectvk.model.Simulator;

public class Main {

    private static Simulator simulator;

    public static void main(String[] args){
        //Main main = new Main();
        simulator = new Simulator();
    }

    public static Simulator getSimulator(){
        return simulator;
    }

}
