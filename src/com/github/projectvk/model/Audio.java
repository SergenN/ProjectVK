package com.github.projectvk.model;

import com.github.projectvk.controller.Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created by Thijs on 27-1-2015.
 * Class: Audio
 */
public class Audio {

    protected Controller controller;
    private Clip clip;

    public Audio(Controller controller){
        this.controller = controller;
    }

    /**
     * Start playing a sound
     * @param path the path of the sound file
     */
    public void playSound(String path) {
        try{
            File soundFile =new File(path);
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop playing the current playing sound
     */
    public void stopSound(){
        if(clip.isRunning()) {
            clip.close();
        }
    }
}
