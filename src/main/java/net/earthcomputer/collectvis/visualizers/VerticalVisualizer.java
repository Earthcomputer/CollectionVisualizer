package net.earthcomputer.collectvis.visualizers;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class VerticalVisualizer<T> extends Visualizer<Iterable<T>> {

    public static final float ALIGN_LEFT = 0f;
    public static final float ALIGN_CENTER = 0.5f;
    public static final float ALIGN_RIGHT = 1f;

    private Supplier<? extends Visualizer<? super T>> visualizerCreator;
    private float alignment;
    private List<Visualizer<? super T>> visualizers = new ArrayList<>();
    private Dimension size;

    public VerticalVisualizer(Supplier<? extends Visualizer<? super T>> visualizerCreator) {
        this(ALIGN_CENTER, visualizerCreator);
    }

    public VerticalVisualizer(float alignment, Supplier<? extends Visualizer<? super T>> visualizerCreator) {
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

        size = new Dimension(visualizers.stream().mapToInt(vis -> vis.getTotalSize().width).max().orElse(0),
                visualizers.stream().mapToInt(vis -> vis.getTotalSize().height).sum());
    }

    @Override
    public Dimension getContentSize() {
        return size;
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        for (Visualizer<? super T> visualizer : visualizers) {
            visualizer.draw(g, x + (int) ((size.width - visualizer.getTotalSize().width) * alignment), y);
            y += visualizer.getTotalSize().height;
        }
    }
}
