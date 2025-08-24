package com.codeartify.primitive;

import java.util.List;

public record Input(
        int x,
        int y,
        int r,
        List<Integer> xCoords,
        List<Integer> yCoords
) {
}
