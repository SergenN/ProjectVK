package com.github.projectvk.view;

import javax.swing.*;
import java.awt.*;

/**
 * Provide a graphical view of a rectangular field. This is
 * a nested class (a class defined inside a class) which
 * defines a custom component for the user interface. This
 * component displays the field.
 * This is rather advanced GUI stuff - you can ignore this
 * for your project if you like.
 */
public class FieldView extends JPanel {
    private final int GRID_VIEW_SCALING_FACTOR = 6;
    private int gridWidth, gridHeight;
    private int xScale, yScale;
    Dimension size;
    private Graphics g;
    private Image fieldImage;

    /**
     * Create a new FieldView component.
     * @param height height of the FieldView
     * @param width width of the FieldView
     */
    public FieldView(int height, int width) {
        gridHeight = height;
        gridWidth = width;
        size = new Dimension(0, 0);
    }

    /**
     * Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR, gridHeight * GRID_VIEW_SCALING_FACTOR);
    }

    /**
     * Prepare for a new round of painting. Since the component
     * may be resized, compute the scaling factor again.
     */
    public void preparePaint() {
        if(! size.equals(getSize())) {  // if the size has changed...
            size = getSize();
            fieldImage = createImage(size.width, size.height);
            g = fieldImage.getGraphics();

            xScale = size.width / gridWidth;
            if(xScale < 1) {
                xScale = GRID_VIEW_SCALING_FACTOR;
            }
            yScale = size.height / gridHeight;
            if(yScale < 1) {
                yScale = GRID_VIEW_SCALING_FACTOR;
            }
        }
    }

    /**
     * Paint on grid location on this field in a given color.
     * @param x the x location on the grid
     * @param y the y location on the grid
     * @param color the color to be drawn
     */
    public void drawMark(int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
    }

    /**
     * The field view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if(fieldImage != null) {
            Dimension currentSize = getSize();
            if(size.equals(currentSize)) {
                g.drawImage(fieldImage, 0, 0, null);
            } else {
                g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    }
}