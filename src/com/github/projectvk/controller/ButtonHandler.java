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

        controller.controllerDo(e.getActionCommand());
    }

}
