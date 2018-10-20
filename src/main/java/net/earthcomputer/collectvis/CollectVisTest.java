package net.earthcomputer.collectvis;

import java.awt.*;
import java.util.Collection;

public class CollectVisTest {

    public static void main(String[] args) {
        CollectionVisualizer.visualize(null, new ICollectionVisualizer<Collection<?>>() {
            @Override
            public void layout(Collection<?> collection) {

            }

            @Override
            public Dimension getSize() {
                return new Dimension(320, 240);
            }

            @Override
            public void draw(Graphics2D g) {
                g.drawString("Hello World!", 5, 30);
            }
        });
    }

}
