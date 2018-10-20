package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class HorizontalListVisualizer<E> extends HorizontalVisualizer<E> {

    public HorizontalListVisualizer(Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        super(ALIGN_TOP, visualizerCreator);
    }

    private Dimension size;
    private int middleY;

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
        this.size = new Dimension(super.getContentSize().width, super.getContentSize().height + fontMetrics.getAscent() + fontMetrics.getDescent() + 1);
    }

    @Override
    public Dimension getContentSize() {
        return size;
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int lineHeight = fontMetrics.getAscent() + fontMetrics.getDescent();

        super.drawContent(g, x, y + lineHeight + 1);

        int index = 0;
        for (Visualizer<? super E> vis : visualizers) {
            int width = vis.getTotalSize().width;
            g.drawString(index + ":", x + width / 2 - fontMetrics.stringWidth(index + ":") / 2, y + fontMetrics.getAscent());
            x += width;
            index++;
        }
    }
}
