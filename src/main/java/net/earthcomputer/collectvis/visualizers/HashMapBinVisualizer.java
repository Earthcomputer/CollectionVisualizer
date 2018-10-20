package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class HashMapBinVisualizer<K, V> extends Visualizer<Map.Entry<K, V>> {

    private static final Field NODE_NEXT;
    private static final Class<?> TREE_NODE_CLASS;
    private static final Field TREE_NODE_LEFT;
    private static final Field TREE_NODE_RIGHT;
    static {
        try {
            Class<?> nodeClass = Class.forName("java.util.HashMap$Node");
            NODE_NEXT = nodeClass.getDeclaredField("next");
            NODE_NEXT.setAccessible(true);
            TREE_NODE_CLASS = Class.forName("java.util.HashMap$TreeNode");
            TREE_NODE_LEFT = TREE_NODE_CLASS.getDeclaredField("left");
            TREE_NODE_LEFT.setAccessible(true);
            TREE_NODE_RIGHT = TREE_NODE_CLASS.getDeclaredField("right");
            TREE_NODE_RIGHT.setAccessible(true);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V> getNext(Map.Entry<K, V> entry) {
        try {
            return (Map.Entry<K, V>) NODE_NEXT.get(entry);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    private static <K, V> boolean isTreeNode(Map.Entry<K, V> entry) {
        return TREE_NODE_CLASS.isInstance(entry);
    }
    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V> getLeft(Map.Entry<K, V> entry) {
        try {
            return (Map.Entry<K, V>) TREE_NODE_LEFT.get(entry);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V> getRight(Map.Entry<K, V> entry) {
        try {
            return (Map.Entry<K, V>) TREE_NODE_RIGHT.get(entry);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private VerticalVisualizer<Map.Entry<K, V>> listVisualizer;
    private BinaryTreeNodeVisualizer<Map.Entry<K, V>, Map.Entry<K, V>> treeVisualizer;
    private Visualizer<?> theVisualizer;

    public HashMapBinVisualizer(Supplier<? extends Visualizer<? super Map.Entry<K, V>>> visualizerCreator) {
        listVisualizer = new VerticalVisualizer<>(visualizerCreator);
        treeVisualizer = new BinaryTreeNodeVisualizer<>(HashMapBinVisualizer::getLeft, HashMapBinVisualizer::getRight, Function.identity(), visualizerCreator);
    }

    @Override
    public void layout(Map.Entry<K, V> node, Graphics2D g) {
        if (isTreeNode(node)) {
            treeVisualizer.layout(node, g);
            theVisualizer = treeVisualizer;
        } else {
            listVisualizer.layout(() -> new Iterator<Map.Entry<K, V>>() {
                Map.Entry<K, V> e = node;
                @Override
                public boolean hasNext() {
                    return e != null;
                }

                @Override
                public Map.Entry<K, V> next() {
                    Map.Entry<K, V> last = e;
                    e = getNext(e);
                    return last;
                }
            }, g);
            theVisualizer = listVisualizer;
        }
    }

    @Override
    public Dimension getContentSize() {
        return theVisualizer.getTotalSize();
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        theVisualizer.draw(g, x, y);
    }
}
