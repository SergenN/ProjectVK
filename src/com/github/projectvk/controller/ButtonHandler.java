package com.github.projectvk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kevin on 22-1-15.
 */
public class ButtonHandler implements ActionListener{
    private Controller controller;

    public ButtonHandler(Controller controller){
        this.controller = controller;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("plusEen")) controller.controllerDo("plusEen");
        if (e.getActionCommand().equals("plusHonderd")) controller.controllerDo("plusHonderd");
        if (e.getActionCommand().equals("stop")) controller.controllerDo("stop");
        if (e.getActionCommand().equals("start")) controller.controllerDo("start");
        if (e.getActionCommand().equals("longSim")) controller.controllerDo("longSim");
        if (e.getActionCommand().equals("birthsStat")) controller.controllerDo("birthsStat") ;
        if (e.getActionCommand().equals("deathsStat")) controller.controllerDo("deathsStat");
        if (e.getActionCommand().equals("stepsStat")) controller.controllerDo("stepsStat");

    }

}
