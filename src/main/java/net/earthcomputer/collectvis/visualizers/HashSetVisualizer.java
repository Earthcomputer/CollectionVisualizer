package net.earthcomputer.collectvis.visualizers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

public class HashSetVisualizer<E> extends TransformingVisualizer<HashSet<E>, HashMap<E, Object>> {

    private static final Field BACKING_MAP;
    static {
        try {
            BACKING_MAP = HashSet.class.getDeclaredField("map");
            BACKING_MAP.setAccessible(true);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <E> HashMap<E, Object> getBackingMap(HashSet<E> set) {
        try {
            return (HashMap<E, Object>) BACKING_MAP.get(set);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public HashSetVisualizer(Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        super(HashSetVisualizer::getBackingMap, new HashMapVisualizer<>(() -> new TransformingVisualizer<>(Map.Entry::getKey, visualizerCreator.get())));
    }
}
