package com.github.projectvk.view;

import com.github.projectvk.controller.ControlPanel;
import com.github.projectvk.model.Field;
import com.github.projectvk.model.FieldStats;
import com.github.projectvk.model.Simulator;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = new Color(229, 229, 229, 255);

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    private ControlPanel controlPanel;
    private SettingsView settingPanel;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width, Simulator simulator)
    {
        JTabbedPane tabbedPane = new JTabbedPane();
/*        tabbedPane.addTab("Tab1", getContentPane());
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        //JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("Tab1", null);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);*/
        settingPanel = new SettingsView(new GridLayout(5,2));
        controlPanel = new ControlPanel(height, simulator);
        stats = new FieldStats();
        colors = new LinkedHashMap<Class, Color>();

        setTitle("Vossen en konijnen simulatie");
        stepLabel = new JLabel();
        stepLabel.setForeground(Color.WHITE);
        stepLabel.setFont(new Font("Helvetica", Font.PLAIN, 10));

        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        population.setForeground(Color.WHITE);
        population.setFont(new Font("Helvetica", Font.PLAIN, 10));

        fieldView = new FieldView(height, width);
        tabbedPane.addTab("Veld", fieldView);
        tabbedPane.addTab("Settings", settingPanel);

        Container contents = getContentPane();
        //contents.setBackground(new Color(39, 39, 39));
        contents.setBackground(new Color(211, 47, 47));

        contents.add(tabbedPane, BorderLayout.NORTH);
        contents.add(stepLabel);
        stepLabel.setBounds(50,50,500,500);
        contents.add(fieldView, BorderLayout.EAST);
        contents.add(controlPanel, BorderLayout.WEST);
        contents.add(population, BorderLayout.SOUTH);
        pack();

        //locatie in het midden v scherm
        setLocationRelativeTo(null);
        //zichtbaar maken
        setVisible(true);
    }

    public ControlPanel getControlPanel(){
        return controlPanel;
    }
    
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }

        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();

        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
}
