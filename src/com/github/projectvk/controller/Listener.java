package com.github.projectvk.controller;

import com.github.projectvk.model.Randomizer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.github.projectvk.Main.propertiesFile;
/**
 * Created by kevin on 22-1-15.
 * Class: Listener
 */
public class Listener implements ActionListener, ChangeListener{
    private Controller controller;

    /**
     * Constructor with the controller
     * @param controller controller for the actions preformed
     */
    public Listener(Controller controller){
        this.controller = controller;
    }

    /**
     * Empty constructor
     */
    public Listener(){}

    /**
     * called when an action is preformed (on button press)
     * @param e action that is preformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.controllerDo(e.getActionCommand());
    }

    /**
     * Invoked when the component's state changes.
     * @param e action that is preformed
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider){
            JSlider source = (JSlider)e.getSource();
            if(!source.getValueIsAdjusting()){
                int value = source.getValue();
                String property = source.getName();
                propertiesFile.setInt(property, value);
            }
        }
        if(e.getSource() instanceof JSpinner){
            JSpinner spinner = (JSpinner)e.getSource();
            int value = (Integer)spinner.getValue();
            String property = spinner.getName();
            propertiesFile.setInt(property, value);
            Randomizer.reset(); //Reset the seed :o
        }
    }
}
