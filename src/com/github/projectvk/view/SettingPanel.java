package com.github.projectvk.view;

import com.github.projectvk.controller.Listener;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.util.HashMap;

import static com.github.projectvk.Main.propertiesFile;

/**
 * Created by Sergen on 22-1-2015.
 */
public class SettingPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private Listener listener;

    public SettingPanel(Listener listener){
        this.listener = listener;
        buildPane();
    }

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
            } else {
                panel.add(new JLabel(key.toLowerCase()), createGridBagConstraint(1, i));
                panel.add(createSlider(0, 100, dataMap.get(key), 10, 5, categorie+"-"+key), createGridBagConstraint(2, i));
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
     *
     * @param min
     * @param max
     * @param init
     * @param tickSpacingMajor
     * @param tickSpacingMinor
     * @param name
     * @param lock
     * @param invert
     * @return
     */
    private JSlider createSlider(int min, int max, int init, int tickSpacingMajor, int tickSpacingMinor, String name, boolean lock, boolean invert){
        JSlider slider = createSlider(min, max, init, tickSpacingMajor, tickSpacingMinor, name);
        slider.setInverted(invert);
        slider.setSnapToTicks(lock);
        slider.addChangeListener(listener);
        return slider;
    }

    /**
     *
     * @param min
     * @param max
     * @param init
     * @param tickSpacingMajor
     * @param tickSpacingMinor
     * @param name
     * @return
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
     *
     * @param column
     * @param row
     * @return
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
     *
     * @param column
     * @param row
     * @param width
     * @param fill
     * @return
     */
    private GridBagConstraints createGridBagConstraint(int column, int row, int width, int fill) {
        GridBagConstraints gbc = createGridBagConstraint(column, row);
        gbc.gridwidth = width;
        gbc.fill = fill;
        return gbc;
    }

    /**
     *
     * @param name
     * @return
     */
    private JPanel createTab(String name){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        tabbedPane.addTab(name, panel);

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new GridBagLayout());
        titleContainer.add(new JLabel(name),createGridBagConstraint(0,0));
        panel.add(titleContainer, createGridBagConstraint(0,0,3,GridBagConstraints.BOTH));

        return panel;
    }
}

//TODO BUGS
//ALS PROBABILITIES OP 0 WORDEN GEZET = CRASH
//JE KAN MEERDERE SETTINGS SCHERMEN HEBBEN
//SETTINGS VERKRIJGEN HUN LISTENER OP EEN ILLEGALE MANIER?
