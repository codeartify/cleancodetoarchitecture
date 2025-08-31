package com.codeartify.underengineered;

import com.codeartify.underengineered.adapter.data_access.PropertyRepository;
import com.codeartify.underengineered.adapter.presentation.PropertySearchController;
import com.codeartify.underengineered.adapter.presentation.Response;
import com.codeartify.underengineered.application.PropertySearchService;
import com.codeartify.underengineered.application.port.outbound.FindProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    private JdbcTemplate jdbcTemplate;
    private PropertySearchController controller;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        final FindProperties findProperties = new PropertyRepository(jdbcTemplate);
        controller = new PropertySearchController(new PropertySearchService(findProperties));
    }

    // --- Missing parameter guards ---

    @Test
    void should_fail_when_x_is_missing() {
        assertThatThrownBy(() -> controller.checkContainment(null, 0.0, 1.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("x is missing");
    }

    @Test
    void should_fail_when_y_is_missing() {
        assertThatThrownBy(() -> controller.checkContainment(0.0, null, 1.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("y is missing");
    }

    @Test
    void should_fail_when_r_is_missing() {
        assertThatThrownBy(() -> controller.checkContainment(0.0, 0.0, null))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("radius is missing");
    }

    // --- Radius validation ---

    @Test
    void should_reject_radius_zero() {
        assertThatThrownBy(() -> controller.checkContainment(0.0, 0.0, 0.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("radius must be greater than 0");
    }

    @Test
    void should_reject_radius_negative() {
        assertThatThrownBy(() -> controller.checkContainment(0.0, 0.0, -1.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("radius must be greater than 0");
    }

    // --- DB interaction branches ---

    @Test
    void should_handle_empty_x_coordinates_from_db() {
        // Simulate empty result set: handler is never called -> xCoords stays empty
        doAnswer(invocation -> null)
                .when(jdbcTemplate)
                .query(eq("SELECT id, x, y FROM properties"), any(RowCallbackHandler.class));

        assertThatThrownBy(() -> controller.checkContainment(0.0, 0.0, 5.0))
                .isInstanceOf(Exception.class)
                .hasMessage("x coordinates are empty");
    }

    @Test
    void should_propagate_sql_exception() {
        doThrow(new RuntimeException("DB down"))
                .when(jdbcTemplate)
                .query(eq("SELECT id, x, y FROM properties"), any(RowCallbackHandler.class));

        assertThatThrownBy(() -> controller.checkContainment(0.0, 0.0, 5.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("DB down");
    }

    // --- Attempt to cover 'y coordinates are empty' branch (documented unreachable) ---

    @Test
    void should_document_y_coordinates_empty_is_unreachable_and_exceptions_propagate() {
        // We simulate a failure while fetching 'y' so yCoords isn't appended on that row.
        // Because the exception is thrown inside the callback and not caught, it propagates,
        // so the controller never reaches the 'y coordinates are empty' branch.
        doAnswer(invocation -> {
            RowCallbackHandler handler = invocation.getArgument(1);

            ResultSet rs = mock(ResultSet.class);
            org.mockito.Mockito.when(rs.getLong("id")).thenReturn(1L);
            org.mockito.Mockito.when(rs.getDouble("x")).thenReturn(1.0);
            org.mockito.Mockito.when(rs.getDouble("y")).thenThrow(new RuntimeException("boom on y"));

            // This will throw before yCoords.add(...)
            handler.processRow(rs);
            return null;
        }).when(jdbcTemplate).query(eq("SELECT id, x, y FROM properties"), any(RowCallbackHandler.class));

        assertThatThrownBy(() -> controller.checkContainment(0.0, 0.0, 5.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("boom on y");
    }

    // --- Attempt to cover 'mismatched lengths' branch (documented unreachable) ---

    @Test
    void should_document_mismatched_lengths_is_unreachable_and_exceptions_propagate() {
        // We simulate two rows, second row throws after appending id and x,
        // but again the exception aborts execution before size checks.
        doAnswer(invocation -> {
            RowCallbackHandler handler = invocation.getArgument(1);

            // Row 1 ok
            ResultSet r1 = mock(ResultSet.class);
            org.mockito.Mockito.when(r1.getLong("id")).thenReturn(1L);
            org.mockito.Mockito.when(r1.getDouble("x")).thenReturn(1.0);
            org.mockito.Mockito.when(r1.getDouble("y")).thenReturn(1.0);
            handler.processRow(r1);

            // Row 2 throws on y
            ResultSet r2 = mock(ResultSet.class);
            org.mockito.Mockito.when(r2.getLong("id")).thenReturn(2L);
            org.mockito.Mockito.when(r2.getDouble("x")).thenReturn(2.0);
            org.mockito.Mockito.when(r2.getDouble("y")).thenThrow(new RuntimeException("row2 y failed"));
            handler.processRow(r2);

            return null;
        }).when(jdbcTemplate).query(eq("SELECT id, x, y FROM properties"), any(RowCallbackHandler.class));

        assertThatThrownBy(() -> controller.checkContainment(0.0, 0.0, 5.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("row2 y failed");
    }

    // --- Happy paths and edge cases ---

    @Test
    void should_include_points_on_circle_boundary() throws Exception {
        // One point exactly at distance r from (0,0): (3,4) with r=5
        Object[][] rows = new Object[][]{
                {1L, 3.0, 4.0},     // exactly on circle
                {2L, 6.0, 0.0}      // outside
        };
        stubRows(rows);

        Response response = controller.checkContainment(0.0, 0.0, 5.0);
        assertThat(response.result()).containsExactly(1L);
    }

    @Test
    void should_return_empty_result_when_no_points_inside() throws Exception {
        Object[][] rows = new Object[][]{
                {1L, 100.0, 100.0},
                {2L, -50.0, 60.0}
        };
        stubRows(rows);

        Response response = controller.checkContainment(0.0, 0.0, 5.0);
        assertThat(response.result()).isEmpty();
    }

    @Test
    void should_count_contained_points_origin_radius_5() throws Exception {
        // 1:(1,1)   2:(2.5,3.5)  3:(-4,0.5)  4:(10,10)  5:(-2,-3)
        Object[][] rows = new Object[][]{
                {1L, 1.0, 1.0},
                {2L, 2.5, 3.5},
                {3L, -4.0, 0.5},
                {4L, 10.0, 10.0},
                {5L, -2.0, -3.0}
        };
        stubRows(rows);

        Response response = controller.checkContainment(0.0, 0.0, 5.0);

        List<Long> ids = response.result();
        assertThat(ids).containsExactlyInAnyOrder(1L, 2L, 3L, 5L);
        assertThat(ids).hasSize(4);
    }

    @Test
    void should_count_contained_points_when_moved_to_new_location_and_cover_id_signs() throws Exception {
        // Use ids -5 (negative), 0 (zero), 7 (positive) to exercise the convoluted boolean condition:
        // (ids.get(i) != null && ids.get(i) >= 0 || ids.get(i) < 0 || ids.get(i) == 0)
        Object[][] rows = new Object[][]{
                {5L, 10.0, 10.0},  // outside for first case (far) but later will be inside
                {0L,  0.0,  0.0},   // center point -> always inside for small radius near origin
                {7L,  1.0,  1.0}    // near origin
        };
        stubRows(rows);

        // Near origin
        Response nearOrigin = controller.checkContainment(0.0, 0.0, 2.0);
        assertThat(nearOrigin.result()).containsExactlyInAnyOrder(0L, 7L);

        // Near (10,10)
        Response nearTenTen = controller.checkContainment(8.0, 8.0, 5.0);
        assertThat(nearTenTen.result()).containsExactly(5L);
    }

    // Helper to simulate DB rows via JdbcTemplate.query(..., RowCallbackHandler)
    private void stubRows(Object[][] rows) {
        doAnswer(invocation -> {
            RowCallbackHandler handler = invocation.getArgument(1);
            for (Object[] row : rows) {
                ResultSet rs = mock(ResultSet.class);
                Long id = (Long) row[0];
                Double x = (Double) row[1];
                Double y = (Double) row[2];

                org.mockito.Mockito.when(rs.getLong("id")).thenReturn(id);
                org.mockito.Mockito.when(rs.getDouble("x")).thenReturn(x);
                org.mockito.Mockito.when(rs.getDouble("y")).thenReturn(y);

                handler.processRow(rs);
            }
            return null;
        }).when(jdbcTemplate).query(eq("SELECT id, x, y FROM properties"), any(RowCallbackHandler.class));
    }
}
