package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;

public class TreeSetVisualizer<E> extends Visualizer<TreeSet<E>> {

    private static final Field BACKING_MAP_FIELD;
    static {
        try {
            BACKING_MAP_FIELD = TreeSet.class.getDeclaredField("m");
            BACKING_MAP_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <E> TreeMap<E, Object> getBackingMap(TreeSet<E> set) {
        try {
            return (TreeMap<E, Object>) BACKING_MAP_FIELD.get(set);
        } catch (ClassCastException e) {
            throw new IllegalStateException("Unsupported backing map type");
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private TreeMapVisualizer<E, Object> mapVisualizer;

    public TreeSetVisualizer(Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        mapVisualizer = new TreeMapVisualizer<>(() -> {
            Visualizer<? super E> vis = visualizerCreator.get();
            return new Visualizer<Map.Entry<E, Object>>() {
                @Override
                public void layout(Map.Entry<E, Object> entry, Graphics2D g) {
                    vis.layout(entry.getKey(), g);
                }

                @Override
                public Dimension getContentSize() {
                    return vis.getTotalSize();
                }

                @Override
                public void drawContent(Graphics2D g, int x, int y) {
                    vis.draw(g, x, y);
                }
            };
        });
    }

    @Override
    public void layout(TreeSet<E> set, Graphics2D g) {
        mapVisualizer.layout(getBackingMap(set), g);
    }

    @Override
    public Dimension getContentSize() {
        return mapVisualizer.getContentSize();
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        mapVisualizer.drawContent(g, x, y);
    }
}
