package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;

import java.util.*;

public class CollectVisTest {

    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 100; i++)
            set.add(i);
        for (int i = 0; i < 10; i++)
            set.add(1024 * i);
        set.add(1025);
        set.remove(2);
        CollectionVisualizer.visualizeHashSet(set, ToStringVisualizer::new);
    }

}
