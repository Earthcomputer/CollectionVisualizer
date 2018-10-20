package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.List;

public abstract class SingletonVisualizer<T> implements ICollectionVisualizer<List<T>> {

    public abstract void layout(T element, Graphics2D g);

    @Override
    public void layout(List<T> collection, Graphics2D g) {
        layout(collection.get(0), g);
    }

}
