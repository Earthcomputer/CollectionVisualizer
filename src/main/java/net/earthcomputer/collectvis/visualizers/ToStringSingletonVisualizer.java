package net.earthcomputer.collectvis.visualizers;

import java.awt.*;

public class ToStringSingletonVisualizer<T> implements IVisualizer<T> {

    private String text;
    private Dimension size;

    @Override
    public void layout(T element, Graphics2D g) {
        text = String.valueOf(element);
        size = new Dimension(g.getFontMetrics().stringWidth(text),
                g.getFontMetrics().getAscent() + g.getFontMetrics().getDescent());
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.drawString(text, x, y + g.getFontMetrics().getAscent());
    }
}
