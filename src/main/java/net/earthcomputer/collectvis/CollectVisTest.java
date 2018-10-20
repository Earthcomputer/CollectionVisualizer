package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;
import net.earthcomputer.collectvis.visualizers.VerticalVisualizer;

import java.util.Arrays;

public class CollectVisTest {

    public static void main(String[] args) {
        CollectionVisualizer.visualize(Arrays.asList("Hello", "World"), new VerticalVisualizer<>(ToStringVisualizer::new));
    }

}
