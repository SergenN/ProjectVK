package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;

//TODO make sure this goes through the controller and not through the model directly
//import com.github.projectvk.model.*;

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
    // Default height and width
    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_WIDTH = 100;

    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = new Color(229, 229, 229, 255);

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    private Controller controller;
    
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    //private FieldStats stats;

    private ControlPanel controlPanel;
    private GraphView graphView;
    private SettingsView settingPanel;

    private int height, width;

    /**
     * Contstructor to use default window settings
     * @param controller
     */
    public SimulatorView(Controller controller){
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH, controller);
        System.out.println("The dimensions must be greater than zero.");
        System.out.println("Using default values.");
    }

    /**
     *
     * @param height
     * @param width
     * @param //simulator
     */
    public SimulatorView(int height, int width, Controller controller)
    {
        //JTabbedPane tabbedPane = new JTabbedPane();
        this.controller = controller;
        // Making sure the simulator has a height and width set. If not, set it to default.
        if(width <= 0 || height <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
        }

        ImageIcon img = new ImageIcon("img\\fox.png");
        this.setIconImage(img.getImage());

        this.height = height;
        this.width = width;

        //controller.setSimulatorView(this);
        //JComponent panel2 = makeTextPanel("Panel #2");

        settingPanel = new SettingsView(new GridLayout(5,2));
        controlPanel = new ControlPanel(height, controller);

        graphView = new GraphView(height, controller);
        //stats = controller.getFieldStats();


        colors = new LinkedHashMap<Class, Color>();

        setTitle("Vossen en konijnen simulatie");
        stepLabel = new JLabel();
        stepLabel.setForeground(Color.WHITE);
        stepLabel.setFont(new Font("Helvetica", Font.PLAIN, 10));

        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        population.setForeground(Color.WHITE);
        population.setFont(new Font("Helvetica", Font.PLAIN, 10));

        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        contents.setBackground(new Color(211, 47, 47));

        //contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(controlPanel, BorderLayout.WEST);
        contents.add(graphView, BorderLayout.EAST);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        
        //locatie in het midden v scherm
        setLocationRelativeTo(null);
        //zichtbaar maken
        setVisible(true);
        setResizable(false);


        Class[] animals = controller.fetchClassDefinitions();


        this.setColor(animals[0], new Color(76, 114, 255)); //Rabbit
        this.setColor(animals[1], new Color(255, 196, 76)); //Fox
        this.setColor(animals[2], new Color(166, 76, 255)); //Dodo
        this.setColor(animals[3], new Color(76, 219, 76)); //Hunter

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public ControlPanel getControlPanel(){
        return controlPanel;
    }

    public GraphView getGraphView(){
        return graphView;
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
     * @param //field The field whose status is to be displayed.
     */
    public void showStatus(int step)
    {
        graphView.drawChart(graphView.getCharType());


        if(!isVisible()) {
            setVisible(true);
        }


        stepLabel.setText(STEP_PREFIX + step);

        controller.getFieldStats().reset();

        fieldView.preparePaint();

        for (int row = 0; row < controller.getField().getHeight(); row++) {
            for (int col = 0; col < controller.getField().getWidth(); col++) {
                Object animal = controller.getField().getObjectAt(row, col);
                if(animal != null) {
                    controller.getFieldStats().incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        controller.getFieldStats().countFinished();

        population.setText(POPULATION_PREFIX + controller.getFieldStats().getPopulationDetails(controller.getField()));
        fieldView.repaint();
    }

    public int getFieldHeight(){
        return height;
    }

    public int getFieldWidth(){
        return width;
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     *//*
    public boolean isViable(Field field)
    {
        return controller.getFieldStats().isViable(field);
    }*/
}