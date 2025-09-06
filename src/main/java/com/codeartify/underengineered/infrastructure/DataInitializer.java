package com.codeartify.underengineered.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS real_estate (
                id BIGINT PRIMARY KEY,
                x DOUBLE PRECISION NOT NULL,
                y DOUBLE PRECISION NOT NULL,
                address VARCHAR(255),
                seller VARCHAR(255),
                price DECIMAL(15,2),
                earliest_move_in_date DATE,
                contact_phone VARCHAR(64),
                contact_email VARCHAR(255)
            )
            """);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM real_estate", Integer.class);
        if (count == null || count == 0) {
            List<Object[]> rows = List.of(
                new Object[]{1L, 1.0, 1.0, "1 Main St", "Alice Realty", new BigDecimal("350000.00"),
                        LocalDate.now().plusDays(30), "+1-555-0100", "alice@example.com"},
                new Object[]{2L, 2.5, 3.5, "25 Oak Ave", "Bravo Brokers", new BigDecimal("525000.00"),
                        LocalDate.now().plusDays(45), "+1-555-0200", "bravo@example.com"},
                new Object[]{3L, -4.0, 0.5, "7 Pine Ct", "Cedar Homes", new BigDecimal("275000.00"),
                        LocalDate.now().plusDays(15), "+1-555-0300", "cedar@example.com"},
                new Object[]{4L, 10.0, 10.0, "99 Sunset Blvd", "Delta Realty", new BigDecimal("825000.00"),
                        LocalDate.now().plusDays(60), "+1-555-0400", "delta@example.com"},
                new Object[]{5L, -2.0, -3.0, "42 River Rd", "Evergreen Estates", new BigDecimal("410000.00"),
                        LocalDate.now().plusDays(20), "+1-555-0500", "evergreen@example.com"}
            );

            jdbcTemplate.batchUpdate("""
                INSERT INTO real_estate (id, x, y, address, seller, price, earliest_move_in_date, contact_phone, contact_email)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, rows);
        }
    }
}
