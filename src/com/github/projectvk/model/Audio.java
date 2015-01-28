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

    private Controller controller;
    AudioInputStream audio;
    public static Clip clip;

    private static String getPlayingAudio = "default";

    /**
     * Constructor to make a Audio model
     *
     * @param controller - the linked controller
     */
    public Audio(Controller controller){
        this.controller = controller;
    }

    /**
     * Play a sound file
     *
     * @param path - this is the location of the sound file. By example "/audio/audiofile.wav"
     */
    public void playSound(String path) {
        try{
            if(!(getPlayingAudio.equals("default")) && !(getPlayingAudio.equals(path))){
                clip.close();
            }

            if(!(getPlayingAudio.equals(path))) {
                getPlayingAudio = path;

                File soundFile = new File(path);
                AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
                clip = AudioSystem.getClip();
                clip.open(sound);
                clip.start();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
