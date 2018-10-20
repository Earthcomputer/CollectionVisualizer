package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.Collection;

public interface ICollectionVisualizer<T extends Collection<?>> {

    void layout(T collection, Graphics2D g);

    Dimension getSize();

    void draw(Graphics2D g, int x, int y);

}
