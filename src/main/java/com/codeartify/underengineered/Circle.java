package com.codeartify.underengineered;

import java.util.List;

public record Circle(Point center, int radius) {

    static Circle toCircle(int x, int y, int radius) {
        return new Circle(new Point(x, y), radius);
    }

    boolean contains(Point point) {
        var deltaX = point.x() - this.center().x();
        var deltaY = point.y() - this.center().y();

        return square(deltaX) + square(deltaY) <= square(this.radius());
    }

    private static int square(int value) {
        return value * value;
    }

    int countContained(List<Point> points) {
        return (int) points.stream().filter(this::contains).count();
    }
}
