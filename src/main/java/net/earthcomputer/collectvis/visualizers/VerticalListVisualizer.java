package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class VerticalListVisualizer<E> extends VerticalVisualizer<E> {

    public VerticalListVisualizer(Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        super(ALIGN_LEFT, visualizerCreator);
    }

    private Dimension size;
    private int middleX;

    @Override
    public void layout(Iterable<E> collection, Graphics2D g) {
        super.layout(collection, g);
        int size;
        if (collection instanceof Collection) {
            size = ((Collection<?>) collection).size();
        } else {
            size = 0;
            for (E e : collection)
                size++;
        }

        FontMetrics fontMetrics = g.getFontMetrics();
        middleX = IntStream.range(0, size).mapToObj(idx -> idx + ":").mapToInt(fontMetrics::stringWidth).max().orElse(0) + 1;
        this.size = new Dimension(super.getContentSize().width + middleX, super.getContentSize().height);
    }

    @Override
    public Dimension getContentSize() {
        return size;
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        super.drawContent(g, x + middleX, y);

        FontMetrics fontMetrics = g.getFontMetrics();
        int lineHeight = fontMetrics.getAscent() + fontMetrics.getDescent();

        int index = 0;
        for (Visualizer<? super E> vis : visualizers) {
            int height = vis.getTotalSize().height;
            g.drawString(index + ":", x + middleX - fontMetrics.stringWidth(index + ":") - 1, y + height / 2 - lineHeight / 2 + fontMetrics.getAscent());
            y += height;
            index++;
        }
    }
}
