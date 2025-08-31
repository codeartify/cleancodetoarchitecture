package com.codeartify.underengineered;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {

    private final JdbcTemplate jdbcTemplate;

    public Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/realestate")
    public Response checkContainment(
            @RequestParam(name = "x", required = false) Double x,
            @RequestParam(name = "y", required = false) Double y,
            @RequestParam(name = "r", required = false) Double r)
            throws Exception {

        var ids = new ArrayList<Long>();
        var results = new ArrayList<Long>();
        var xCoords = new ArrayList<Double>();
        var yCoords = new ArrayList<Double>();

        if (x == null) {
            throw new RuntimeException("x is missing");
        }
        if (y == null) {
            throw new RuntimeException("y is missing");
        }
        if (r == null) {
            throw new RuntimeException("radius is missing");
        }
        if (r <= 0) {
            throw new RuntimeException("radius must be greater than 0");
        }

        jdbcTemplate.query("SELECT id, x, y FROM properties", rs -> {
            var id = rs.getLong("id");
            var xx = rs.getDouble("x");
            var yy = rs.getDouble("y");
            ids.add(id);
            xCoords.add(xx);
            yCoords.add(yy);
        });

        if (xCoords.isEmpty()) {
            throw new Exception("x coordinates are empty");
        }
        if (yCoords.isEmpty()) {
            throw new Exception("y coordinates are empty");
        }
        if (xCoords.size() != yCoords.size() || ids.size() != xCoords.size()) {
            throw new Exception("Not every provided x coordinate has a matching y coordinate");
        }

        for (int i = 0; i < xCoords.size(); ++i) {
            var x1 = xCoords.get(i);
            var y1 = yCoords.get(i);
            if (contains(x, y, r, x1, y1)) {
                results.add(ids.get(i));
            }
        }

        return new Response(results);
    }

    private static boolean contains(Double x, Double y, Double radius, Double x1, Double y1) {
        var deltaX = x1 - x;
        var deltaY = y1 - y;
        return square(deltaX) + square(deltaY) <= square(radius);
    }

    private static double square(double value) {
        return value * value;
    }

}
