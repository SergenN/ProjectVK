package com.github.projectvk.view;

import com.github.projectvk.controller.Controller;
import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class ControlPanel extends JPanel{
    private static final int GRID_VIEW_SCALING_FACTOR = 6;
    private JLabel sim_kop, weather_kop, weatherIcon, weatherBG;
    private JButton plusEen, plusHonderd, longSim, start, stop, settings, reset;
    private int height;
    private Controller controller;
    private JStyle jStyle;

    // Weather icons
    private ImageIcon snow = new ImageIcon("img/snow.png");
    private ImageIcon rain = new ImageIcon("img/rain.png");
    private ImageIcon sunny = new ImageIcon("img/sun.png");
    private ImageIcon bgWeather = new ImageIcon("img/bg_weathericon.png");

    /**
     * Constructor voor het maken van de control panel
     *
     * @param height - Hoogte van de simulator
     */
    public ControlPanel(int height, Controller controller) {

        setBackground(new Color(33, 33, 33));
        this.setLayout(null);
        this.height = height;
        this.controller = controller;
        this.jStyle = controller.getJStyle();

        makeGUI();
    }

    /**
     * Add buttons to the GUI
     */
    public void makeGUI(){
        ////////////////////// IMAGES  ////////////////////////
        ImageIcon pauseIcon = new ImageIcon("img/pause.png");
        ImageIcon playIcon = new ImageIcon("img/play.png");
        ImageIcon icon = new ImageIcon("img/fox.png");
        JLabel fox = new JLabel();
        fox.setIcon(icon);
        add(fox);
        fox.setBounds(13, 205, 500, 500);

        //////////////////////// HEADERS  ////////////////////////
        sim_kop = new JLabel("Simulate");
        jStyle.headerStyle(sim_kop, this, 10, 20, 80, 30, new Color(132, 132, 132), 16);

        weather_kop = new JLabel("Weather", SwingConstants.CENTER);
        jStyle.headerStyle(weather_kop, this, -1, 305, 101, 30, new Color(132, 132, 132), 16);

        weatherIcon = new JLabel("", SwingConstants.CENTER);
        jStyle.headerStyle(weatherIcon, this, 0, 340, 101, 58, new Color(216, 214, 126), 16);

        weatherBG = new JLabel("", SwingConstants.CENTER);
        jStyle.headerStyle(weatherBG, this, 0, 337, 101, 68, new Color(216, 214, 126), 16);
        weatherBG.setIcon(bgWeather);

        //////////////////////// BUTTONS  ////////////////////////

        // + 1 button
        plusEen = new JButton("+1");
        jStyle.buttonStyle(plusEen, "plusEen", controller, this, 10, 60, 80, 30);

        // + 100 button
        plusHonderd = new JButton("+100");
        jStyle.buttonStyle(plusHonderd, "plusHonderd",controller, this, 10, 100, 80, 30);

        // Simuleer button
        longSim = new JButton("+1000");
        jStyle.buttonStyle(longSim, "longSim", controller, this, 10, 140, 80, 30);

        // Stop button
        stop = new JButton();
        jStyle.buttonStyle(stop, "stop", controller, this, 55, 180, 35, 30);
        stop.setIcon(pauseIcon);

        // Start button
        start = new JButton();
        jStyle.buttonStyle(start, "start", controller, this, 10, 180, 35, 30);
        start.setIcon(playIcon);

        // Reset button
        reset = new JButton("Reset");
        jStyle.buttonStyle(reset, "reset", controller, this, 10, 220, 80, 30);

        // Settings button
        settings = new JButton("Settings");
        jStyle.buttonStyle(settings, "settings", controller, this, 10, 260, 80, 30);
    }

    /**
     * verkrijg de geprefereerde groote voor deze jPane
     *
     * @return preferred size of this jPane
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(100, height * GRID_VIEW_SCALING_FACTOR);
    }

    public void changeWeatherText(String weatherType){
        weather_kop.setText(weatherType);

        System.out.println(weatherType);

        switch(weatherType){
            case "Sunny":
                weatherIcon.setIcon(sunny);
                weatherIcon.setBounds(weatherIcon.getX(), 341, weatherIcon.getWidth(), weatherIcon.getHeight());
                break;
            case "Rain":
                weatherIcon.setIcon(rain);
                weatherIcon.setBounds(-2, 345, weatherIcon.getWidth(), weatherIcon.getHeight());
                break;
            case "Snow":
                weatherIcon.setIcon(snow);
                weatherIcon.setBounds(weatherIcon.getX(), 344, weatherIcon.getWidth(), weatherIcon.getHeight());
                break;
        }
    }

    /**
     * Disables buttons while the simulation is running running
     * Enables the buttons when the simulation is finished.
     */
    public void changeButtons() {
        if(controller.isSimulatorRunning()) {
            plusEen.setEnabled(false);
            plusHonderd.setEnabled(false);
            start.setEnabled(false);
            stop.setEnabled(true);
            longSim.setEnabled(false);
        } else {
            plusEen.setEnabled(true);
            plusHonderd.setEnabled(true);
            start.setEnabled(true);
            stop.setEnabled(false);
            longSim.setEnabled(true);
        }
    }
}
