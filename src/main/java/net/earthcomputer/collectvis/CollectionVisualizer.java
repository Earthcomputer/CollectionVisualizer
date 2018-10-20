package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.TreeMapVisualizer;
import net.earthcomputer.collectvis.visualizers.TreeSetVisualizer;
import net.earthcomputer.collectvis.visualizers.VerticalListVisualizer;
import net.earthcomputer.collectvis.visualizers.Visualizer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;

public class CollectionVisualizer {

    private static <E> Supplier<? extends Visualizer<? super E>> addStandardBorder(Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        return () -> {
            Visualizer<? super E> vis = visualizerCreator.get();
            Border b = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(2, 2, 2, 2));
            vis.setBorder(b);
            return vis;
        };
    }

    public static <E> void visualizeList(List<E> list, Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        visualize(list, new VerticalListVisualizer<>(addStandardBorder(visualizerCreator)));
    }

    public static <E> void visualizeTreeSet(TreeSet<E> set, Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        visualize(set, new TreeSetVisualizer<>(addStandardBorder(visualizerCreator)));
    }

    public static <K, V> void visualizeTreeMap(TreeMap<K, V> map, Supplier<? extends Visualizer<? super Map.Entry<K, V>>> visualizerCreator) {
        visualize(map, new TreeMapVisualizer<>(addStandardBorder(visualizerCreator)));
    }

    public static <T> void visualize(T collection, Visualizer<? super T> visualizer) {
        // If the first parameter is a Dialog, it shows it in the taskbar, but if it's a Frame it doesn't
        JDialog dialog = new JDialog((Dialog) null, "Collection Visualizer", true);

        {
            JMenuBar menuBar = new JMenuBar();

            JMenu viewMenu = new JMenu("View");
            menuBar.add(viewMenu);
            viewMenu.setMnemonic(KeyEvent.VK_V);

            JMenuItem refreshItem = new JMenuItem("Refresh");
            viewMenu.add(refreshItem);
            refreshItem.setMnemonic(KeyEvent.VK_R);
            refreshItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
            refreshItem.getAccessibleContext().setAccessibleDescription("Refreshing the collection view");
            refreshItem.addActionListener(event -> dialog.repaint());

            dialog.setJMenuBar(menuBar);
        }

        {
            CollectVisViewport<T> viewport = new CollectVisViewport<>(collection, visualizer);
            JScrollPane scrollPane = new JScrollPane(viewport);
            scrollPane.setPreferredSize(new Dimension(720, 480));
            dialog.add(scrollPane);
        }

        dialog.pack();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.requestFocus();
        dialog.setVisible(true);
    }

}
