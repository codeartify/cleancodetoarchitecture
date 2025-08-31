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

        if (x != null) {
            if (y != null) {
                if (r != null) {
                    if (!(r <= 0)) {
                        jdbcTemplate.query("SELECT id, x, y FROM properties", rs -> {
                            var id = rs.getLong("id");
                            var xx = rs.getDouble("x");
                            var yy = rs.getDouble("y");
                            ids.add(id);
                            xCoords.add(xx);
                            yCoords.add(yy);
                        });

                        if (!xCoords.isEmpty()) {
                            if (!yCoords.isEmpty()) {
                                if (xCoords.size() == yCoords.size() && ids.size() == xCoords.size()) {
                                    for (int i = 0; i < xCoords.size(); ++i) {
                                        var result = ((xCoords.get(i) - x) * (xCoords.get(i) - x) + (yCoords.get(i) - y) * (yCoords.get(i) - y) <= r * r);
                                        if (result) {
                                            results.add(ids.get(i));
                                        }
                                    }
                                } else {
                                    throw new Exception("Not every provided x coordinate has a matching y coordinate");
                                }
                            } else {
                                throw new Exception("y coordinates are empty");
                            }
                        } else {
                            throw new Exception("x coordinates are empty");
                        }
                    } else {
                        throw new RuntimeException("radius must be greater than 0");
                    }
                } else {
                    throw new RuntimeException("radius is missing");
                }
            } else {
                throw new RuntimeException("y is missing");
            }
        } else {
            throw new RuntimeException("x is missing");
        }

        return new Response(results);
    }

}
