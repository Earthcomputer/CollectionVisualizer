package net.earthcomputer.collectvis;

import net.earthcomputer.collectvis.visualizers.IVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CollectVisViewport<T> extends JComponent implements Scrollable, MouseMotionListener {

    private static final int MAX_UNIT_INCREMENT = 1;

    private T collection;
    private IVisualizer<? super T> visualizer;

    public CollectVisViewport(T collection, IVisualizer<? super T> visualizer) {
        this.collection = collection;
        this.visualizer = visualizer;

        setAutoscrolls(true);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        visualizer.layout(collection, g2);

        visualizer.draw(g2, 0, 0);

        Dimension size = visualizer.getSize();
        if (!size.equals(getPreferredSize())) {
            setPreferredSize(size);
            revalidate();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
        scrollRectToVisible(r);
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        int currentPos;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPos = visibleRect.x;
        } else {
            currentPos = visibleRect.y;
        }

        if (direction < 0) {
            int newPos = currentPos - (currentPos / MAX_UNIT_INCREMENT) * MAX_UNIT_INCREMENT;
            return newPos == 0 ? MAX_UNIT_INCREMENT : newPos;
        } else {
            return ((currentPos / MAX_UNIT_INCREMENT) + 1) * MAX_UNIT_INCREMENT - currentPos;
        }
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - MAX_UNIT_INCREMENT;
        } else {
            return visibleRect.height - MAX_UNIT_INCREMENT;
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
