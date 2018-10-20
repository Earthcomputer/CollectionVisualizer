package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;
import net.earthcomputer.collectvis.visualizers.TreeMapVisualizer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class CollectVisTest {

    public static void main(String[] args) {
        TreeMap<Double, Double> map = new TreeMap<>();
        for (int i = 0; i < 20; i++)
            map.put(Math.random(), Math.random());
        CollectionVisualizer.visualize(map, new TreeMapVisualizer<>(() -> {
            ToStringVisualizer<Map.Entry<Double, Double>> vis = new ToStringVisualizer<>();
            Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(2, 2, 2, 2));
            vis.setBorder(border);
            return vis;
        }));
    }

}
