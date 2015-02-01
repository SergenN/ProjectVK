package com.github.projectvk.view.graph;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by Sergen on 1-2-2015.
 * Class: CustomPieChart
 */
public class CustomPieChart extends Component{
    LinkedList<Slice> slices = new LinkedList<>();

    /**
     * Init de custom pie chart 
     * @param data Naam = Kleur = Data
     */
    public CustomPieChart(HashMap<String ,HashMap<Color, Integer>> data) {
        setName("Piechart");
        for (String key : data.keySet()){
            slices.addAll(data.get(key).keySet().stream().map(color -> new Slice(data.get(key).get(color), color, key)).collect(Collectors.toList()));
        }
    }

    /**
     * Draw the piechart 
     * @param g graphic
     */
    public void paint(Graphics g) {
        drawPie((Graphics2D) g, getBounds(), slices);
    }

    /**
     * draw the piechart
     * @param g graphic
     * @param area area
     * @param slices slices of the piechart
     */
    private void drawPie(Graphics2D g, Rectangle area, LinkedList<Slice> slices) {
        double total = 0.0D;
        double curValue = 0.0D;
        int startAngle;

        for (Slice slice : slices) {
            total += slice.value;
        }

        for (Slice slice : slices) {
            JLabel label = new JLabel();
            label.setText(slice.name + ": "+ slice.value + " %");
            label.setLocation(area.x, area.y);//Not shown :(
            
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slice.value * 360 / total);
            g.setColor(slice.color);
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
            curValue += slice.value;
        }
    }
    
}

class Slice {
    double value;
    Color color;
    String name;

    /**
     * Create a new Slice
     * @param value value of the slice
     * @param color color of the slice
     * @param name name of the slice
     */
    public Slice(double value, Color color, String name) {
        this.value = value;
        this.color = color;
        this.name = name;
    }
}
