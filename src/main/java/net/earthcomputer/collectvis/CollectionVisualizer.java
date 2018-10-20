package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.Visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class CollectionVisualizer {

    public static <T> void visualize(T collection, Visualizer<? super T> visualizer) {
        JDialog dialog = new JDialog((Frame) null, "Collection Visualizer", true);

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
