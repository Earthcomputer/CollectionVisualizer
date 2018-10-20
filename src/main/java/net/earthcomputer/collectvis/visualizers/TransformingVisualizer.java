package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.function.Function;

public class TransformingVisualizer<A, B> extends Visualizer<A> {

    private Function<A, B> transformer;
    private Visualizer<B> delegate;

    public TransformingVisualizer(Function<A, B> transformer, Visualizer<B> delegate) {
        this.transformer = transformer;
        this.delegate = delegate;
    }

    @Override
    public void layout(A object, Graphics2D g) {
        delegate.layout(transformer.apply(object), g);
    }

    @Override
    public Dimension getContentSize() {
        return delegate.getContentSize();
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        delegate.drawContent(g, x, y);
    }
}
