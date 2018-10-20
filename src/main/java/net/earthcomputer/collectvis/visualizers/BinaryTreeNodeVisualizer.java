package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.util.function.Function;
import java.util.function.Supplier;

public final class BinaryTreeNodeVisualizer<T, E> extends Visualizer<T> {

    private static final float BRANCH_SLOPE = 0.375f;

    private Visualizer<? super E> contentVisualizer;
    private BinaryTreeNodeVisualizer<T, E> leftVisualizer;
    private BinaryTreeNodeVisualizer<T, E> rightVisualizer;

    private Function<T, T> leftExtractor;
    private Function<T, T> rightExtractor;
    private Function<T, E> contentExtractor;
    private Supplier<? extends Visualizer<? super E>> visualizerCreator;

    private Dimension size;

    public BinaryTreeNodeVisualizer(Function<T, T> leftExtractor, Function<T, T> rightExtractor, Function<T, E> contentExtractor, Supplier<? extends Visualizer<? super E>> visualizerCreator) {
        this.leftExtractor = leftExtractor;
        this.rightExtractor = rightExtractor;
        this.contentExtractor = contentExtractor;
        this.visualizerCreator = visualizerCreator;
    }

    @Override
    public void layout(T node, Graphics2D g) {
        E content = contentExtractor.apply(node);
        if (contentVisualizer == null)
            contentVisualizer = visualizerCreator.get();

        T left = leftExtractor.apply(node);
        if (left == null)
            leftVisualizer = null;
        else if (leftVisualizer == null)
            leftVisualizer = new BinaryTreeNodeVisualizer<>(leftExtractor, rightExtractor, contentExtractor, visualizerCreator);

        T right = rightExtractor.apply(node);
        if (right == null)
            rightVisualizer = null;
        else if (rightVisualizer == null)
            rightVisualizer = new BinaryTreeNodeVisualizer<>(leftExtractor, rightExtractor, contentExtractor, visualizerCreator);


        contentVisualizer.layout(content, g);
        if (leftVisualizer != null)
            leftVisualizer.layout(left, g);
        if (rightVisualizer != null)
            rightVisualizer.layout(right, g);

        Dimension contentSize = contentVisualizer.getTotalSize();
        Dimension leftSize = leftVisualizer == null ? null : leftVisualizer.getTotalSize();
        Dimension rightSize = rightVisualizer == null ? null : rightVisualizer.getTotalSize();

        int width = leftSize == null ? contentSize.width / 2 : Math.max(contentSize.width / 2, leftSize.width);
        width++;
        width += rightSize == null ? contentSize.width / 2 : Math.max(contentSize.width / 2, rightSize.width);

        int height = contentSize.height;
        height += Math.max(leftSize == null ? 0 : (int) ((leftSize.width - leftVisualizer.getTopAnchorX()) * BRANCH_SLOPE),
                rightSize == null ? 0 : (int) (rightVisualizer.getTopAnchorX() * BRANCH_SLOPE));
        height += Math.max(leftSize == null ? 0 : leftSize.height, rightSize == null ? 0 : rightSize.height);

        size = new Dimension(width, height);
    }

    @Override
    public Dimension getContentSize() {
        return size;
    }

    @Override
    public int getTopAnchorX() {
        return Math.max(contentVisualizer.getTotalSize().width / 2, leftVisualizer == null ? 0 : leftVisualizer.getTotalSize().width);
    }

    @Override
    public void drawContent(Graphics2D g, int x, int y) {
        Dimension contentSize = contentVisualizer.getTotalSize();
        Dimension leftSize = leftVisualizer == null ? null : leftVisualizer.getTotalSize();
        Dimension rightSize = rightVisualizer == null ? null : rightVisualizer.getTotalSize();

        int middleX = x + Math.max(contentSize.width / 2, leftSize == null ? 0 : leftSize.width);

        contentVisualizer.draw(g, middleX - contentSize.width / 2, y);

        int childrenY = y + contentSize.height;
        childrenY += Math.max(leftSize == null ? 0 : (int) ((leftSize.width - leftVisualizer.getTopAnchorX()) * BRANCH_SLOPE),
                rightSize == null ? 0 : (int) (rightVisualizer.getTopAnchorX() * BRANCH_SLOPE));

        if (leftSize != null) {
            g.setColor(Color.BLACK);
            g.drawLine(middleX, y + contentSize.height, middleX - leftSize.width + leftVisualizer.getTopAnchorX(), childrenY);
            leftVisualizer.draw(g, middleX - leftSize.width, childrenY);
        }

        if (rightSize != null) {
            g.setColor(Color.BLACK);
            g.drawLine(middleX, y + contentSize.height, middleX + rightVisualizer.getTopAnchorX(), childrenY);
            rightVisualizer.draw(g, middleX, childrenY);
        }
    }
}
