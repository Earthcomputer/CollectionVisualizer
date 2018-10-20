package net.earthcomputer.collectvis.visualizers;

import java.awt.*;

public class NullSafeVisualizer<T> extends Visualizer<T> {
    private Visualizer<T> delegate;
    private boolean isNull;
    private boolean nullsBlank;

    public NullSafeVisualizer(boolean nullsBlank, Visualizer<T> delegate) {
        this.nullsBlank = nullsBlank;
        this.delegate = delegate;
    }

    @Override
    public void layout(T object, Graphics2D g) {
        this.isNull = object == null;
        if (!isNull) {
            delegate.layout(object, g);
        }
    }

    @Override
    public Dimension getContentSize() {
        return isNull ? (nullsBlank ? new Dimension(0, 0) : new Dimension(32, 32)) : delegate.getTotalSize();
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        if (isNull) {
            if (!nullsBlank) {
                g.setColor(Color.BLACK);
                g.drawRect(x, y, 31, 31);
                g.drawLine(x, y, x + 31, y + 31);
                g.drawLine(x, y + 31, x + 31, y);
            }
        } else {
            delegate.draw(g, x, y);
        }
    }
}
