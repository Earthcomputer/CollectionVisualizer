package net.earthcomputer.collectvis.visualizers;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class VerticalVisualizer<T> implements IVisualizer<Iterable<T>> {

    private Supplier<? extends IVisualizer<? super T>> visualizerCreator;
    private List<IVisualizer<? super T>> visualizers = new ArrayList<>();
    private Dimension size;

    public VerticalVisualizer(Supplier<? extends IVisualizer<? super T>> visualizerCreator) {
        this.visualizerCreator = visualizerCreator;
    }

    @Override
    public void layout(Iterable<T> collection, Graphics2D g) {
        int index = 0;
        for (T elem : collection) {
            IVisualizer<? super T> vis;
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

        size = new Dimension(visualizers.stream().mapToInt(vis -> vis.getSize().width).max().orElse(0),
                visualizers.stream().mapToInt(vis -> vis.getSize().height).sum());
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        for (IVisualizer<? super T> visualizer : visualizers) {
            visualizer.draw(g, x + size.width / 2 - visualizer.getSize().width / 2, y);
            y += visualizer.getSize().height;
        }
    }
}
