package net.earthcomputer.collectvis.visualizers;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class FieldSingletonVisualizer<T> extends SingletonVisualizer<T> {

    private Field[] visualizedFields;
    private String[] displayedLines;
    private Dimension size;

    public FieldSingletonVisualizer(Class<T> clazz) {
        visualizedFields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())).toArray(Field[]::new);
        for (Field field : visualizedFields)
            field.setAccessible(true);
        displayedLines = new String[visualizedFields.length];
    }

    public FieldSingletonVisualizer(Class<T> clazz, String... visualizedFields) {
        this.visualizedFields = new Field[visualizedFields.length];
        for (int i = 0; i < visualizedFields.length; i++) {
            try {
                this.visualizedFields[i] = clazz.getDeclaredField(visualizedFields[i]);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("Field not found: \"" + visualizedFields[i] + "\"");
            }
            this.visualizedFields[i].setAccessible(true);
        }
        displayedLines = new String[visualizedFields.length];
    }

    @Override
    public void layout(T element, Graphics2D g) {
        for (int i = 0; i < visualizedFields.length; i++) {
            try {
                displayedLines[i] = visualizedFields[i].getName() + ": " + visualizedFields[i].get(element);
            } catch (Exception e) {
                throw new IllegalStateException("Couldn't get field \"" + visualizedFields[i].getName() + "\" of object " + element);
            }
        }

        FontMetrics metrics = g.getFontMetrics();
        size = new Dimension(Arrays.stream(displayedLines).mapToInt(metrics::stringWidth).max().orElse(0),
                (metrics.getAscent() + metrics.getDescent()) * displayedLines.length);
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        int lineHeight = g.getFontMetrics().getAscent() + g.getFontMetrics().getDescent();
        y += g.getFontMetrics().getAscent();

        for (String line : displayedLines) {
            g.drawString(line, x, y);
            y += lineHeight;
        }
    }
}
