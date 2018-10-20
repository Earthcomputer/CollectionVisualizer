package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.function.Function;

public class ToStringVisualizer<T> extends Visualizer<T> {

    private Function<? super T, String> toStringFunction;
    private String text;
    private Dimension size;

    public ToStringVisualizer() {
        this(String::valueOf);
    }

    public ToStringVisualizer(Function<? super T, String> toStringFunction) {
        this.toStringFunction = toStringFunction;
    }

    @Override
    public void layout(T element, Graphics2D g) {
        text = toStringFunction.apply(element);
        size = new Dimension(g.getFontMetrics().stringWidth(text),
                g.getFontMetrics().getAscent() + g.getFontMetrics().getDescent());
    }

    @Override
    public Dimension getContentSize() {
        return size;
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        g.drawString(text, x, y + g.getFontMetrics().getAscent());
    }
}
