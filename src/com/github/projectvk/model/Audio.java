package com.github.projectvk.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created by Thijs on 27-1-2015.
 * Class: Audio
 */
public class Audio {
    public static Clip clip;

    private static String getPlayingAudio = "default";

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
