package com.github.projectvk.runner;

import com.github.projectvk.model.Simulator;

/**
 * Created by Sergen on 21-1-2015.
 */
public class ThreadRunner implements Runnable{

    private int steps;
    private boolean inf;
    private Simulator sim;
    private final long delay = 500;

    public ThreadRunner(Simulator sim){
        steps = 0;
        inf = false;
        this.sim = sim;
    }

    public void runSimulate(int steps){
        this.steps += steps;
        if(this.steps > 0) {
            if (!Thread.currentThread().isAlive()) {
                new Thread(this).run();
            }
        }
    }

    public void runSimulate(boolean infinite){
        this.inf = infinite;
        if(infinite){
            if(!Thread.currentThread().isAlive()){
                new Thread(this).run();
            }
        }
    }

    public void stopSimulate(){
        inf = false;
        steps = 0;
    }

    private void decrementSteps(){
        if(steps > 0){
            steps--;
        }
    }


    @Override
    public void run() {
        if(steps > 0 || inf){
            sim.simulateOneStep();
            decrementSteps();
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
