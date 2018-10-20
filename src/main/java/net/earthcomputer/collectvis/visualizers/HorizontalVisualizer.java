package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HorizontalVisualizer<T> extends Visualizer<Iterable<T>> {

    public static final float ALIGN_TOP = 0f;
    public static final float ALIGN_MIDDLE = 0.5f;
    public static final float ALIGN_BOTTOM = 1f;

    private Supplier<? extends Visualizer<? super T>> visualizerCreator;
    private float alignment;
    protected List<Visualizer<? super T>> visualizers = new ArrayList<>();
    private Dimension size;

    public HorizontalVisualizer(Supplier<? extends Visualizer<? super T>> visualizerCreator) {
        this(ALIGN_MIDDLE, visualizerCreator);
    }

    public HorizontalVisualizer(float alignment, Supplier<? extends Visualizer<? super T>> visualizerCreator) {
        this.alignment = alignment;
        this.visualizerCreator = visualizerCreator;
    }

    @Override
    public void layout(Iterable<T> collection, Graphics2D g) {
        int index = 0;
        for (T elem : collection) {
            Visualizer<? super T> vis;
            if (index >= visualizers.size()) {
                vis = visualizerCreator.get();
                visualizers.add(vis);
            } else {
                vis = visualizers.get(index);
            }
            vis.layout(elem, g);
            index++;
        }
        visualizers.subList(index, visualizers.size()).clear();

        size = new Dimension(visualizers.stream().mapToInt(vis -> vis.getTotalSize().width).sum(),
                visualizers.stream().mapToInt(vis -> vis.getTotalSize().height).max().orElse(0));
    }

    @Override
    public Dimension getContentSize() {
        return size;
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        for (Visualizer<? super T> visualizer : visualizers) {
            visualizer.draw(g, x, y + (int) ((size.height - visualizer.getTotalSize().height) * alignment));
            x += visualizer.getTotalSize().width;
        }
    }

}
