package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thijs on 24/01/2015.
 */
public class JStyle {
    // Change the appearance of a given button.
    public void buttonStyle(JButton button, String command, Controller controller, JPanel panel, int xposition, int yposition, int width, int height){
        button.setBackground(new Color(114, 114, 114));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Helvetica", Font.PLAIN, 12));
        button.setActionCommand(command);
        button.setBounds(xposition, yposition, width, height);

        button.addActionListener(controller.getListener());
        panel.add(button);
    }

    // Change the appearance of a given label.
    public void headerStyle(JComponent label, JPanel panel, int xposition, int yposition, int width, int height, Color color, int fontSize){
        label.setForeground(color);
        label.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        panel.add(label);
        label.setBounds(xposition, yposition, width, height);
    }

}
