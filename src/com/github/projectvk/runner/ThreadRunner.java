package com.github.projectvk.runner;

/**
 * Created by Sergen on 21-1-2015.
 */
public class ThreadRunner implements Runnable{

    private int steps;
    private boolean inf;

    public ThreadRunner(){
        steps = 0;
        inf = false;
    }

    public void runSimulate(int steps){
        this.steps += steps;
    }

    public void runSimulate(boolean infinite){
        if(infinite){

        }
    }

    public void stopSimulate(){
        inf = false;
        steps = 0;
    }


    @Override
    public void run() {

    }
}
