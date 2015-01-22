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

        if (e.getActionCommand() == "plusEen") controller.ControllerDo("plusEen");
        if (e.getActionCommand() == "plusHonderd") controller.ControllerDo("plusHonderd");
        if (e.getActionCommand() == "stop") controller.ControllerDo("stop");
        if (e.getActionCommand() == "start") controller.ControllerDo("start");
        if (e.getActionCommand() == "longSim") controller.ControllerDo("longSim");
        if (e.getActionCommand() == "birthsStat") controller.ControllerDo("birthsStat") ;
        if (e.getActionCommand() == "deathsStat") controller.ControllerDo("deathsStat");
        if (e.getActionCommand() == "stepsStat") controller.ControllerDo("stepsStat");

    }


}
