package net.earthcomputer.collectvis.visualizers;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;

public class TreeSetVisualizer<E> extends TransformingVisualizer<TreeSet<E>, TreeMap<E, Object>> {

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

    public TreeSetVisualizer(Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        super(TreeSetVisualizer::getBackingMap, new TreeMapVisualizer<>(() -> new TransformingVisualizer<>(Map.Entry::getKey, visualizerCreator.get())));
    }
}
