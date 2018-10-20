package net.earthcomputer.collectvis.visualizers;

import java.awt.*;

public interface IVisualizer<T> {

    void layout(T object, Graphics2D g);

    Dimension getSize();

    void draw(Graphics2D g, int x, int y);

}
