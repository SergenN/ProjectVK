package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thijs on 24/01/2015.
 * Class: JStyle
 */
public class JStyle {
    /**
     * Change the appearance of a given button.
     * @param button The button to edit
     * @param command The command of the button
     * @param controller The controller of the button (to get the listener from)
     * @param panel the panel of the button
     * @param xposition the x position of the button
     * @param yposition the y position of the button
     * @param width the witdh of the button
     * @param height the height of the button
     */
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

    /**
     * Change the appearance of a given label.
     * @param label the label to edit
     * @param panel the panel of the label
     * @param xposition the xposition of the label
     * @param yposition the y position of the label
     * @param width the width of the label
     * @param height the height of the label
     * @param color the color of the label
     * @param fontSize the fontSize of the label
     */
    public void headerStyle(JComponent label, JPanel panel, int xposition, int yposition, int width, int height, Color color, int fontSize){
        System.out.println("jStyle");
        label.setForeground(color);
        label.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        label.setBounds(xposition, yposition, width, height);
        panel.add(label);
    }
}
