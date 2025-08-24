package com.codeartify.underengineered;

import java.util.ArrayList;
import java.util.List;

public class PointsFactory {
    static List<Point> createPointsFromCoordinates(List<Integer> xCoords, List<Integer> yCoords) {
        if (xCoords == null) {
            throw new RuntimeException("x coordinates are empty");
        }
        if (xCoords.isEmpty()) {
            throw new RuntimeException("x coordinates are empty");
        }
        if (yCoords == null) {
            throw new RuntimeException("y coordinates are empty");
        }
        if (yCoords.isEmpty()) {
            throw new RuntimeException("y coordinates are empty");
        }
        if (xCoords.size() != yCoords.size()) {
            throw new RuntimeException("Not every provided x coordinate has a matching y coordinate");
        }

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < xCoords.size(); ++i) {
            points.add(new Point(xCoords.get(i), yCoords.get(i)));

        }
        return points;
    }
}
