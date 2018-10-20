package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringSingletonVisualizer;

import java.util.Collections;

public class CollectVisTest {

    public static void main(String[] args) {
        CollectionVisualizer.visualize(Collections.singletonList("Hello World!"), new ToStringSingletonVisualizer<>());
    }

}
