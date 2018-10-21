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

        FontMetrics fontMetrics = g.getFontMetrics();
        int width = 0;
        for (int i = 0; i < visualizers.size(); i++) {
            width += Math.max(Math.max(visualizers.get(i).getTotalSize().width, fontMetrics.stringWidth(i + ":") + 5), 32);
        }
        this.size = new Dimension(width, super.getContentSize().height + fontMetrics.getAscent() + fontMetrics.getDescent() + 1);
    }

    @Override
    public Dimension getContentSize() {
        return size;
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int lineHeight = fontMetrics.getAscent() + fontMetrics.getDescent();

        int index = 0;
        for (Visualizer<? super E> vis : visualizers) {
            int width = Math.max(Math.max(vis.getTotalSize().width, fontMetrics.stringWidth(index + ":") + 5), 32);
            g.drawString(index + ":", x + width / 2 - fontMetrics.stringWidth(index + ":") / 2, y + fontMetrics.getAscent());
            vis.draw(g, x + width / 2 - vis.getTotalSize().width / 2, y + lineHeight + 1);
            x += width;
            index++;
        }
    }
}
