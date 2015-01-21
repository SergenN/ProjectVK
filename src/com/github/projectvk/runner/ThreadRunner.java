package com.github.projectvk.runner;

import com.github.projectvk.Main;
import com.github.projectvk.model.Simulator;

/**
 * Created by Sergen on 21-1-2015.
 */
public class ThreadRunner implements Runnable{

    private int steps;
    private boolean inf;
    private Simulator sim;
    private final long delay = 500;
    private boolean running;

    public ThreadRunner(){
        steps = 0;
        inf = false;
        running = false;
        sim = Main.getSimulator();
    }

    public void runSimulate(int steps){
        System.out.println(steps + "," + this.steps + "," + Thread.currentThread().isAlive() + "," + running);
        this.steps += steps;
        if(this.steps > 0) {
            if (!running && Thread.currentThread().isAlive()) {
                this.run();
                //new Thread(this).run();
            }
        }
    }

    public void runSimulate(boolean infinite){
        this.inf = infinite;
        if(infinite){
            if(!running && Thread.currentThread().isAlive()){
                this.run();
                //new Thread(this).run();
            }
        }
    }

    public void stopSimulate(){
        inf = false;
        steps = 0;
        running = false;
    }

    private void decrementSteps(){
        if(steps > 0){
            steps--;
        }
    }


    @Override
    public void run() {
        running = true;
        while (running && (steps > 0 || inf)) {
            sim.simulateOneStep();
            decrementSteps();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        running = false;
    }
}
