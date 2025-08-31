package com.codeartify.underengineered.adapter.data_access;

import com.codeartify.underengineered.application.port.outbound.FindProperties;
import com.codeartify.underengineered.domain.Location;
import com.codeartify.underengineered.domain.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Repository
public class PropertyRepository implements FindProperties {
    private final JdbcTemplate jdbcTemplate;

    public PropertyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Property> findAll() throws Exception {
        var ids = new ArrayList<Long>();
        var xCoords = new ArrayList<Double>();
        var yCoords = new ArrayList<Double>();

        this.jdbcTemplate.query("SELECT id, x, y FROM properties", rs -> {
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

        return IntStream.range(0, xCoords.size())
                .mapToObj(i -> new Property(ids.get(i), new Location(xCoords.get(i), yCoords.get(i))))
                .collect(toList());
    }
}
