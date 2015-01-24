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

        if (e.getActionCommand() == "plusEen") controller.controllerDo("plusEen");
        if (e.getActionCommand() == "plusHonderd") controller.controllerDo("plusHonderd");
        if (e.getActionCommand() == "stop") controller.controllerDo("stop");
        if (e.getActionCommand() == "start") controller.controllerDo("start");
        if (e.getActionCommand() == "longSim") controller.controllerDo("longSim");
        if (e.getActionCommand() == "birthsStat") controller.controllerDo("birthsStat") ;
        if (e.getActionCommand() == "deathsStat") controller.controllerDo("deathsStat");
        if (e.getActionCommand() == "stepsStat") controller.controllerDo("stepsStat");

    }

}
