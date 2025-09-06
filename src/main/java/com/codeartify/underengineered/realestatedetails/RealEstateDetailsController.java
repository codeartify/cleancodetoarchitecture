package com.codeartify.underengineered.realestatedetails;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@RestController
public class RealEstateDetailsController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/api/realestatedetails/{id}")
    public ResponseEntity<RealEstateDetails> getRealEstateDetails(@PathVariable("id") long id) {
        try {
            var query = "SELECT seller, contact_phone, contact_email, earliest_move_in_date FROM real_estate WHERE id = ?";
            var details = jdbcTemplate.queryForObject(
                    query,
                    (rs, rowNum) -> toRealEstateDetails(rs),
                    id);

            return ResponseEntity.ok(details);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new RealEstateDetails("Error fetching real estate details. Please try again later.")
            );
        }
    }

    private static RealEstateDetails toRealEstateDetails(ResultSet rs) throws SQLException {
        var seller = rs.getString("seller");
        var contactPhone = rs.getString("contact_phone");
        var contactEmail = rs.getString("contact_email");
        var earliestMoveInDate = rs.getDate("earliest_move_in_date");

        return RealEstateDetails.from(seller, contactPhone, contactEmail, earliestMoveInDate);
    }

}
