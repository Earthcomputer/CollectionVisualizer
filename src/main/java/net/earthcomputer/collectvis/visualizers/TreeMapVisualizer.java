package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class TreeMapVisualizer<K, V> extends Visualizer<TreeMap<K, V>> {

    private static final Field ROOT_FIELD;
    private static final Field LEFT_FIELD;
    private static final Field RIGHT_FIELD;
    static {
        try {
            ROOT_FIELD = TreeMap.class.getDeclaredField("root");
            ROOT_FIELD.setAccessible(true);
            Class<?> treeMapEntry = Class.forName("java.util.TreeMap$Entry");
            LEFT_FIELD = treeMapEntry.getDeclaredField("left");
            LEFT_FIELD.setAccessible(true);
            RIGHT_FIELD = treeMapEntry.getDeclaredField("right");
            RIGHT_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V> getRoot(TreeMap<K, V> map) {
        try {
            return (Map.Entry<K, V>) ROOT_FIELD.get(map);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V> getLeft(Map.Entry<K, V> node) {
        try {
            return (Map.Entry<K, V>) LEFT_FIELD.get(node);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V> getRight(Map.Entry<K, V> node) {
        try {
            return (Map.Entry<K, V>) RIGHT_FIELD.get(node);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private BinaryTreeNodeVisualizer<Map.Entry<K, V>, Map.Entry<K, V>> rootVisualizer;
    private boolean empty;

    public TreeMapVisualizer(Supplier<? extends Visualizer<? super Map.Entry<K, V>>> visualizerCreator) {
        this.rootVisualizer = new BinaryTreeNodeVisualizer<>(TreeMapVisualizer::getLeft, TreeMapVisualizer::getRight, Function.identity(), visualizerCreator);
    }

    @Override
    public void layout(TreeMap<K, V> map, Graphics2D g) {
        empty = map.isEmpty();
        if (!empty)
            rootVisualizer.layout(getRoot(map), g);
    }

    @Override
    public Dimension getContentSize() {
        return empty ? new Dimension(0, 0) : rootVisualizer.getContentSize();
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        if (!empty)
            rootVisualizer.draw(g, x, y);
    }
}
