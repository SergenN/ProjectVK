package com.github.projectvk.view;

import com.github.projectvk.controller.Listener;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.util.HashMap;

import static com.github.projectvk.Main.propertiesFile;

/**
 * Created by Sergen on 22-1-2015.
 * Class SettingsPanel
 */
public class SettingPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private Listener listener;

    public SettingPanel(Listener listener){
        this.listener = listener;
        buildPane();
    }

    /**
     * Build a new panel from the properties which are available
     */
    public void buildPane(){
        setLayout(new GridBagLayout());

        tabbedPane = new JTabbedPane();
        tabbedPane.setEnabled(true);
        tabbedPane.setMinimumSize(new Dimension(500, 500));
        add(tabbedPane, createGridBagConstraint(0,0));//voeg tabbedPane toe aan jPanel

        HashMap<String, HashMap<String, Integer>> categorizedMap = propertiesFile.getCategories();
        generateTab("general", categorizedMap.get("general"));
        categorizedMap.remove("general");

        for (String categorie : categorizedMap.keySet()){
            generateTab(categorie, categorizedMap.get(categorie));
        }
    }

    /**
     * This will generate a tab from the given Hashmap with keys(labels) and values (slides)
     * Some of the content had to be checked by hand.
     * @param categorie the title of the tab
     * @param dataMap the settings hashmap in key -> value order
     */
    private void generateTab(String categorie, HashMap<String, Integer> dataMap){
        JPanel panel = createTab(categorie.toLowerCase());
        int i = 1;
        for (String key : dataMap.keySet()){
            if(key.equalsIgnoreCase("MAX_LITTER_SIZE")) {
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i));
                panel.add(createSlider(0,10,dataMap.get(key),1,0, categorie+"-"+key, true, false), createGridBagConstraint(2,i));
            } else if (key.equalsIgnoreCase("SEED")){
                panel.add(new JPanel(), createGridBagConstraint(1, i));
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i+1));
                panel.add(createSpinner(0, 100000000, dataMap.get(key), 1, categorie+"-"+key), createGridBagConstraint(2, i+1));
                panel.add(new JPanel(), createGridBagConstraint(1, i+2));
                i = i+2;
            } else if (key.equalsIgnoreCase("STEP_SPEED")){
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i));
                panel.add(createSlider(0, 1000, dataMap.get(key), 1000, 100, categorie+"-"+key), createGridBagConstraint(2, i));
            }
            else if (key.equalsIgnoreCase("MAX_AGE")){
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i));
                panel.add(createSlider(0, 1000, dataMap.get(key), 1000, 100, categorie+"-"+key), createGridBagConstraint(2, i));
            }
            else if(key.contains("PROBABILITY")){
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i));
                panel.add(createSlider(0, 100, dataMap.get(key), 10, 5, categorie+"-"+key), createGridBagConstraint(2, i));
            }
            else if(key.contains("MULTIPLIER")){
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i));
                panel.add(createSlider(-10, 10, dataMap.get(key), 5, 1, categorie+"-"+key), createGridBagConstraint(2, i));
            }
            else {
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i));
                panel.add(createSlider(0, 30, dataMap.get(key), 10, 5, categorie+"-"+key), createGridBagConstraint(2, i));
            }
            i++;
        }
        panel.add(new JPanel(), createGridBagConstraint(0, 1));//lege tab links aan jpanel om mooi te maken
        panel.add(new JPanel(), createGridBagConstraint(2, i));//lege tab onder aan jpanel om mooi te maken
    }

    /**
     * This will create a jSpinner with the given parameters
     * @param min - minimal spin
     * @param max - maximal spin
     * @param init - initialize spin
     * @param step - steps to spin each time
     * @param name - name of the spin
     * @return Jspinner
     */
    private JSpinner createSpinner(int min, int max, int init, int step, String name){
        SpinnerModel model = new SpinnerNumberModel(init, min, max, step);
        JSpinner spinner = new JSpinner(model);
        JComponent comp = spinner.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        spinner.setName(name);
        spinner.addChangeListener(listener);
        return spinner;
    }

    /**
     * create a new slider but this one is with extra properties
     *
     * @param min minimal of the slider
     * @param max maximal of the slider
     * @param init initialized setting of the slider
     * @param tickSpacingMajor major ticks of the slider
     * @param tickSpacingMinor minor ticks of the slider
     * @param name name of the slider
     * @param lock lock to the ticks when changing the slider
     * @param invert invert the slider
     * @return JSlide the adjusted slider
     */
    private JSlider createSlider(int min, int max, int init, int tickSpacingMajor, int tickSpacingMinor, String name, boolean lock, boolean invert){
        JSlider slider = createSlider(min, max, init, tickSpacingMajor, tickSpacingMinor, name);
        slider.setInverted(invert);
        slider.setSnapToTicks(lock);
        slider.addChangeListener(listener);
        return slider;
    }

    /**
     * create a new slider with adjusted properties
     *
     * @param min minimum of the slider
     * @param max maximum of the slider
     * @param init the setting the slider should be initialized on
     * @param tickSpacingMajor major tick space
     * @param tickSpacingMinor minor tick space
     * @param name name of the slider
     * @return new configured jSlider
     */
    private JSlider createSlider(int min, int max, int init, int tickSpacingMajor, int tickSpacingMinor, String name){
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
        slider.setName(name);
        slider.setMajorTickSpacing(tickSpacingMajor);
        slider.setMinorTickSpacing(tickSpacingMinor);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addChangeListener(listener);
        return slider;
    }

    /**
     * create a new GBC
     * @param column columnn the GBC has to be created on
     * @param row row the GBC has to be created on
     * @return a new Gridbagconstraint
     */
    private GridBagConstraints createGridBagConstraint(int column, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    /**
     *create a gridbag constraint but with width and filling
     *
     * @param column the column the gbc has to be on
     * @param row the row the gbc has to be on
     * @param width the width of the gbc
     * @param fill the fill of the gbc
     * @return new Gridbagconstraint
     */
    private GridBagConstraints createGridBagConstraint(int column, int row, int width, int fill) {
        GridBagConstraints gbc = createGridBagConstraint(column, row);
        gbc.gridwidth = width;
        gbc.fill = fill;
        return gbc;
    }

    /**
     * create a new tab with a title above all settings
     * @param name the name of the tba
     * @return the created tab
     */
    private JPanel createTab(String name){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        tabbedPane.addTab(name, panel);

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new GridBagLayout());
        titleContainer.add(new JLabel(name + "Instellingen"), createGridBagConstraint(0, 0));
        panel.add(titleContainer, createGridBagConstraint(0,0,3,GridBagConstraints.BOTH));

        return panel;
    }
}