package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.function.Function;

public class TransformingVisualizer<A, B> extends Visualizer<A> {

    private Function<? super A, ? extends B> transformer;
    private Visualizer<? super B> delegate;

    public TransformingVisualizer(Function<? super A, ? extends B> transformer, Visualizer<? super B> delegate) {
        this.transformer = transformer;
        this.delegate = delegate;
    }

    @Override
    public void layout(A object, Graphics2D g) {
        delegate.layout(transformer.apply(object), g);
    }

    @Override
    public Dimension getContentSize() {
        return delegate.getTotalSize();
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        delegate.draw(g, x, y);
    }
}
