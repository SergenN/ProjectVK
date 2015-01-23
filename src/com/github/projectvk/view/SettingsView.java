package com.github.projectvk.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergen on 22-1-2015.
 */
public class SettingsView extends JPanel {

    GridLayout layout;

    public SettingsView(GridLayout layout){
        this.layout = layout;
        GridTest(5, 2);
    }

    public void GridTest(int rows, int cols) {
        setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < 20; i++) {
            JButton button = new JButton(Integer.toString(i + 1));
            add(button);
        }
    }



}
