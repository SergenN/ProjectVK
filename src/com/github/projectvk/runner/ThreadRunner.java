package com.github.projectvk.runner;

import com.github.projectvk.Main;

/**
 * Created by Sergen on 21-1-2015.
 */
public class ThreadRunner implements Runnable{

    private int steps;
    private boolean inf;
    private final long delay = 0;
    private boolean running;

    public ThreadRunner(){
        steps = 0;
        inf = false;
        running = false;
    }

    public void runSimulate(int steps){
        System.out.println(steps + "," + this.steps + "," + Thread.currentThread().isAlive() + "," + running);
        this.steps += steps;
        if(this.steps > 0) {
            if (!running && Thread.currentThread().isAlive()) {
                new Thread(this).run();
            }
        }
    }

    public void runSimulate(boolean infinite){
        this.inf = infinite;
        if(infinite){
            if(!running && Thread.currentThread().isAlive()){
                new Thread(this).run();
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
            Main.getSimulator().simulateOneStep();
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
