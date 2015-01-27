package com.github.projectvk.model;

import com.github.projectvk.controller.Controller;

import java.io.*;
import javax.sound.sampled.*;


/**
 * Created by Thijs on 27-1-2015.
 */
public class Audio {
    private Controller controller;

    public Audio(Controller controller){
        this.controller = controller;

        try {

        } catch (Exception e) {
            System.out.println("Something went wrong with the audio");
        }
    }
}
