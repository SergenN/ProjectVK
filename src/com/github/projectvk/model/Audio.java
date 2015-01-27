package com.github.projectvk.model;

import com.github.projectvk.controller.Controller;

import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Created by Thijs on 27-1-2015.
 */
public class Audio {

    private Controller controller;
    AudioInputStream audio;
    Clip clip;

    //private static String getPlayingAudio = "default";

    public Audio(Controller controller){
        this.controller = controller;
    }

    public void playSound(String path)
    {
                //stopSound();
                try{
                    File soundFile =new File(path);
                    AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
                    // load the sound into memory (a Clip)
                    clip = AudioSystem.getClip();
                    clip.open(sound);
                    clip.start();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
//        }
//        getPlayingAudio = path;
//
//        if(getPlayingAudio != "default") {
//            System.out.println(path + "|" + getPlayingAudio + " | Same: " + path.equals(getPlayingAudio));
//            if(!(path.equals(getPlayingAudio))){
//                System.out.println("STOP DE SOUND");
//                stopSound();
//            } else {
//                try{
//                    File soundFile =new File(path);
//                    AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
//                    // load the sound into memory (a Clip)
//                    clip = AudioSystem.getClip();
//                    clip.open(sound);
//                    clip.start();
//                }
//                catch(Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//        getPlayingAudio = path;
//    }

    // play, stop, loop the sound clip
//    public void play(){
//        clip.setFramePosition(0);  // Must always rewind!
//        clip.start();
//    }
//    public void loop(){
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
    // }

    public void stopSound(){
        if(clip.isRunning()) {
            clip.close();
        }
    }
}
