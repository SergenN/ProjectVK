package com.github.projectvk.view;

import com.github.projectvk.controller.Listener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergen on 26-1-2015.
 * Class: SettingsFrame
 */
public class SettingsFrame extends JFrame{
    /**
     * Create a new SettingsFrame
     */
    public SettingsFrame(){
        SettingPanel view = new SettingPanel(new Listener());
        ImageIcon img = new ImageIcon("img\\fox.png");
        setIconImage(img.getImage());
        setTitle("Vossen en konijnen simulatie settings");
        Container contents = getContentPane();
        contents.add(view);
        pack();
        //locatie in het midden v scherm
        setLocationRelativeTo(null);
        //zichtbaar maken
        setVisible(true);
        //resize blokkeren
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
