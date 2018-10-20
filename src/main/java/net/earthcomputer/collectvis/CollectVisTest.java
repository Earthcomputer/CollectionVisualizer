package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.ToStringVisualizer;
import net.earthcomputer.collectvis.visualizers.TreeSetVisualizer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.TreeSet;

public class CollectVisTest {

    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < 100; i++)
            set.add(i);
        CollectionVisualizer.visualize(set, new TreeSetVisualizer<>(() -> {
            ToStringVisualizer<Integer> vis = new ToStringVisualizer<>();
            Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(2, 2, 2, 2));
            vis.setBorder(border);
            return vis;
        }));
    }

}
