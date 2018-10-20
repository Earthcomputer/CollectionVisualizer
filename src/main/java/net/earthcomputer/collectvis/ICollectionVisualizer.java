package net.earthcomputer.collectvis;

import java.awt.*;
import java.util.Collection;

public interface ICollectionVisualizer<T extends Collection<?>> {

    void layout(T collection);

    Dimension getSize();

    void draw(Graphics2D g);

}
