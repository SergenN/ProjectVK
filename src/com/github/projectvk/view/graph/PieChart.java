package com.github.projectvk.view.graph;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergen on 1-2-2015.
 *
 */
public class PieChart extends JComponent{
    Slice[] slices = { new Slice(5, Color.black),  new Slice(33, Color.green), new Slice(20, Color.yellow), new Slice(15, Color.red) };
    
    public PieChart() {}
    
    public void paint(Graphics g) {
        drawPie((Graphics2D) g, getBounds(), slices);
    }
    
    private void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
        double total = 0.0D;
        double curValue = 0.0D;
        int startAngle;
        
        for (int i = 0; i < slices.length; i++) {
            total += slices[i].value;
        }
        
        for (int i = 0; i < slices.length; i++) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slices[i].value * 360 / total);
            g.setColor(slices[i].color);
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
            curValue += slices[i].value;
        }
    }
}

class Slice {
    double value;
    Color color;
    public Slice(double value, Color color) {
        this.value = value;
        this.color = color;
    }
}
