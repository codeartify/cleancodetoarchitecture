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
    public Response checkContainment(@RequestParam(required = false) Double x,
                                     @RequestParam(required = false) Double y,
                                     @RequestParam(required = false) Double r) throws Exception {

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

        var search = new Search(new Location(x, y), new SearchRadius(r));

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
            var id = ids.get(i);

            var property = new Property(id, new Location(x1, y1));

            if (search.contains(property)) {
                results.add(property.id());
            }
        }

        return new Response(results);
    }

}
