package net.earthcomputer.collectvis.visualizers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HashMapVisualizer<K, V> extends TransformingVisualizer<HashMap<K, V>, List<Map.Entry<K, V>>> {

    private static final Field TABLE;
    static {
        try {
            TABLE = HashMap.class.getDeclaredField("table");
            TABLE.setAccessible(true);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V>[] getTable(HashMap<K, V> map) {
        try {
            return (Map.Entry<K, V>[]) TABLE.get(map);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public HashMapVisualizer(Supplier<? extends Visualizer<? super Map.Entry<K, V>>> visualizerCreator) {
        super(map -> Arrays.asList(getTable(map)),
                new NullSafeVisualizer<>(true,
                        new HorizontalListVisualizer<>(() -> new NullSafeVisualizer<>(false, new HashMapBinVisualizer<>(visualizerCreator)))));
    }
}
