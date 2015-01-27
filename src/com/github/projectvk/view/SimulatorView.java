package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 */
public class SimulatorView extends JFrame  {
    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_WIDTH = 100;
    private static final Color EMPTY_COLOR = new Color(229, 229, 229, 255);
    private static final Color UNKNOWN_COLOR = new Color(143, 218, 18);
    private static final String STEP_PREFIX = "Step: ";
    private static final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    private Controller controller;
    private Map<Class, Color> colors;
    private ControlPanel controlPanel;
    private GraphView graphView;

    /**
     * Contstructor to use default window settings
     * @param controller
     */
    @SuppressWarnings("UnusedDeclaration")
    public SimulatorView(Controller controller){
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH, controller);
        System.out.println("The dimensions must be greater than zero.");
        System.out.println("Using default values.");
    }

    /**
     * create the view of the simulator
     * @param height minimal height
     * @param width minimal width
     * @param controller the control panel
     */
    public SimulatorView(int height, int width, Controller controller) {
        //JTabbedPane tabbedPane = new JTabbedPane();
        this.controller = controller;
        // Making sure the simulator has a height and width set. If not, set it to default.
        if(width <= 0 || height <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
        }
        ImageIcon img = new ImageIcon("img\\fox.png");
        this.setIconImage(img.getImage());
        controlPanel = new ControlPanel(height, controller);
        colors = new LinkedHashMap<>();
        HashMap<String, Class> animals = controller.fetchClassDefinitions();

        // Set the colors for the classes
        this.setColor(animals.get("Rabbit"), new Color(76, 114, 255)); //Rabbit
        this.setColor(animals.get("Fox"), new Color(255, 196, 76)); //Fox
        this.setColor(animals.get("Dodo"), new Color(166, 76, 255)); //Dodo
        this.setColor(animals.get("Hunter"), new Color(219, 0, 37)); //Hunter


        // Screen setup
        graphView = new GraphView(height, controller, colors);
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
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(controlPanel, BorderLayout.WEST);
        contents.add(graphView, BorderLayout.EAST);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        animals = controller.fetchClassDefinitions();

        //noinspection MagicConstant
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * get the control panel of this frame
     * @return the control panel
     */
    public ControlPanel getControlPanel(){
        return controlPanel;
    }

    /**
     * get the graph view of this frame
     * @return the graph view
     */
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
     * get the color of the given animal
     * @param animalClass the animal class
     * @return The color to be used for a given class of animal.
     */
    protected Color getColor(Class animalClass) {
        Color col = colors.get(animalClass);
        if(col == null) {
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
    public void showStatus(int step) {
        graphView.drawChart(graphView.getDataChartType());
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
}