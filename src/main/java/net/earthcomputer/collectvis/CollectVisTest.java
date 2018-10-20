package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;

import java.util.*;

public class CollectVisTest {

    public static void main(String[] args) {
        HashMap<Integer, Integer> set = new HashMap<>();
        for (int i = 0; i < 100; i++)
            set.put(i, null);
        for (int i = 0; i < 10; i++)
            set.put(1024 * i, null);
        set.put(1025, null);
        set.remove(2);
        CollectionVisualizer.visualizeHashMap(set, ToStringVisualizer<Map.Entry<Integer, Integer>>::new);
    }

}
