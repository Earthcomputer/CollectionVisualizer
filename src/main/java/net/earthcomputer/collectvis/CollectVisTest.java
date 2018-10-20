package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;
import net.earthcomputer.collectvis.visualizers.VerticalVisualizer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;

public class CollectVisTest {

    public static void main(String[] args) {
        CollectionVisualizer.visualize(Arrays.asList("Hello", "World"), new VerticalVisualizer<>(() -> {
            ToStringVisualizer<String> vis = new ToStringVisualizer<>();
            Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(2, 2, 2, 2));
            vis.setBorder(border);
            return vis;
        }));
    }

}
