package net.earthcomputer.collectvis.visualizers;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public abstract class Visualizer<T> {

    private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder();

    private Border border = EMPTY_BORDER;

    public void setBorder(Border border) {
        this.border = border;
    }

    public int getTopAnchorX() {
        return getTotalSize().width / 2;
    }

    public int getBottomAnchorX() {
        return getTotalSize().width / 2;
    }

    public int getLeftAnchorY() {
        return getTotalSize().height / 2;
    }

    public int getRightAnchorY() {
        return getTotalSize().height / 2;
    }

    public abstract void layout(T object, Graphics2D g);

    public Dimension getTotalSize() {
        Insets insets;
        try {
            insets = border.getBorderInsets(null);
        } catch (NullPointerException e) {
            insets = EMPTY_BORDER.getBorderInsets(null);
        }
        Dimension contentSize = getContentSize();
        return new Dimension(insets.left + contentSize.width + insets.right, insets.top + contentSize.height + insets.bottom);
    }

    public abstract Dimension getContentSize();

    public void draw(Graphics2D g, int x, int y) {
        Dimension totalSize = getTotalSize();
        Insets insets;
        try {
            insets = border.getBorderInsets(null);
            border.paintBorder(null, g, x, y, totalSize.width, totalSize.height);
        } catch (NullPointerException e) {
            insets = EMPTY_BORDER.getBorderInsets(null);
        }
        drawContent(g, x + insets.left, y + insets.top);
    }

    public abstract void drawContent(Graphics2D g, int x, int y);

}
