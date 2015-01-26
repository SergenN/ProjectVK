package com.github.projectvk.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergen on 26-1-2015.
 */
public class SettingsFrame extends JFrame{

    private SettingsView view;

    public SettingsFrame(){
        view = new SettingsView(new GridLayout(7,7));
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
