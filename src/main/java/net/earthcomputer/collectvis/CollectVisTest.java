package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.HorizontalVisualizer;
import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;

import java.util.Arrays;

public class CollectVisTest {

    public static void main(String[] args) {
        CollectionVisualizer.visualize(Arrays.asList("Hello", "World"), new HorizontalVisualizer<>(ToStringVisualizer::new));
    }

}
