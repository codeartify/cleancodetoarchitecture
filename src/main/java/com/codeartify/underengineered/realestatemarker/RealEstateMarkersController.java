package com.codeartify.underengineered.realestatemarker;

import com.codeartify.underengineered.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@RestController
public class RealEstateMarkersController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/api/realestatemarkers")
    public ResponseEntity<List<RealEstateMarker>> getRealEstateMarkers() {
        var markers = fetchRealEstateMarkers();

        return ResponseEntity.ok(markers);
    }

    private List<RealEstateMarker> fetchRealEstateMarkers() {
        var query = "SELECT id, x, y, address, seller, price FROM real_estate";
        return jdbcTemplate.query(query, (rs, rowNum) -> toRealEstateMarker(rs));
    }

    private static RealEstateMarker toRealEstateMarker(ResultSet rs) throws SQLException {
        var id = rs.getLong("id");
        var x = rs.getDouble("x");
        var y = rs.getDouble("y");
        var seller = rs.getString("seller");
        var address = rs.getString("address");
        var price = rs.getDouble("price");

        return toRealEstateMarker(id, x, y, seller, address, price);
    }

    private static RealEstateMarker toRealEstateMarker(long id, double x, double y, String seller, String address, double price) {
        return new RealEstateMarker(
                id,
                new Location(x, y),
                MarkerText.from(seller, address, price)
        );
    }
}


