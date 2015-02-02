package com.github.projectvk.view.graph;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by Sergen on 1-2-2015.
 * Class: CustomPieChart
 */
public class CustomPieChart extends Component {
    LinkedList<Slice> slices = new LinkedList<>();

    private static final int HEIGHT = 350;
    private static final int WIDTH = 350;

    private static final int XPOS = 130;
    private static final int YPOS = 40;
    
    private String title;

    /**
     * Init de custom pie chart 
     * @param data Naam = Kleur = Data
     */
    public CustomPieChart(HashMap<String ,HashMap<Color, Integer>> data, String title) {
        this.title = title;
        for (String key : data.keySet()){
            slices.addAll(data.get(key).keySet().stream().map(color -> new Slice(data.get(key).get(color), color, key)).collect(Collectors.toList()));
        }
    }

    /**
     * Draw the piechart 
     * @param g graphic
     */
    public void paint(Graphics g) {
        drawPie((Graphics2D) g, slices);
        drawLegend((Graphics2D) g, slices);//drawpie has to do the percentage magic first!
    }

    /**
     * draw the piechart
     * @param g graphic
     * @param slices slices of the piechart
     */
    private void drawPie(Graphics2D g, LinkedList<Slice> slices) {
        double total = 0.0D;
        double curValue = 0.0D;
        int startAngle;

        for (Slice slice : slices) {
            total += slice.value;
        }

        for (int i = 0; i < slices.size(); i++) {
            startAngle = (int)Math.ceil(curValue * 360 / total);
            int arcAngle = (int)Math.ceil(slices.get(i).value * 360 / total);
            this.slices.get(i).percentage = (int)Math.round((slices.get(i).value / total)*100);
            g.setColor(slices.get(i).color);
            g.fillArc(XPOS, YPOS, WIDTH, HEIGHT, startAngle, arcAngle);
            curValue += slices.get(i).value;
        }
    }

    /**
     * Draw the legend
     * @param g graphics instance
     * @param slices list of all slices in the chart
     */
    private void drawLegend(Graphics2D g, LinkedList<Slice> slices){
        Font titleFont = new Font("Helvetica", Font.BOLD, 15);
        g.setFont(titleFont);
        FontMetrics metrics = g.getFontMetrics(titleFont);
        g.setPaint(Color.black);
        g.drawString(title, (Math.round(getWidth() / 2)-(metrics.stringWidth(title)/2)), 25);//magic of positioning the string in the middle
        
        g.setFont(new Font("Helvetica", Font.PLAIN, 13));
        Rectangle2D.Double rect = new Rectangle2D.Double(10, 10, 150, slices.size()*20);
        g.setPaint(Color.white);
        g.fill(rect);//draw the container
        g.setPaint(Color.black);
        g.drawRect (9, 9, 151, slices.size()*20+1);//border around it
        int top = 15;
        for (Slice slice : slices){
            g.setPaint(slice.color);
            g.fill(new Rectangle2D.Double((int) rect.getX()+5, rect.getY()+top-9, 10, 10));
            g.setPaint(Color.black);
            g.drawString(slice.name + " : "+ slice.percentage + " %", (int)rect.getX()+20, (int)rect.getY()+top);
            top += 20;//spacing between legend elements
        }
    }
}

class Slice {
    double value;
    Color color;
    String name;
    int percentage = 0;

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
