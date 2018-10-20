package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;

import java.util.*;

public class CollectVisTest {

    public static void main(String[] args) {
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            set.add(i);
        CollectionVisualizer.visualizeList(set, ToStringVisualizer::new);
    }

}
