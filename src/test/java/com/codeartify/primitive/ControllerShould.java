package com.codeartify.primitive;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerShould {


    @Test
    void should_always_have_a_valid_response_on_invalid_input() {
        assertThrows(RuntimeException.class, () -> new Controller().checkContainment(null));
    }

    @Test
    void should_always_have_a_radius_larger_0() {
        assertThrows(RuntimeException.class, () -> new Controller().checkContainment(new Input(0, 0, 0, List.of(), List.of())));
        assertThrows(RuntimeException.class, () -> new Controller().checkContainment(new Input(0, 0, -1, List.of(), List.of())));
    }

    @Test
    void should_handle_coordinates_of_unequal_length() {
        Controller circle = new Controller();

        var input = new Input(0, 0, 2, List.of(2, 3, 4, -12, -20), List.of(8, 20, 15, -4));

        assertThrows(RuntimeException.class, () -> circle.checkContainment(input));
    }

    @Test
    void should_handle_empty_x_coordinates() {
        Controller controller = new Controller();
        assertThrows(RuntimeException.class, () -> controller.checkContainment(new Input(0, 0, 2, null, List.of(8, 20, 15, -4))));
        assertThrows(RuntimeException.class, () -> controller.checkContainment(new Input(0, 0, 2, List.of(), List.of(8, 20, 15, -4))));
    }

    @Test
    void should_handle_empty_y_coordinates() {
        Controller controller = new Controller();

        assertThrows(RuntimeException.class, () -> controller.checkContainment(new Input(0, 0, 2, List.of(8, 20, 15, -4), null)));
        assertThrows(RuntimeException.class, () -> controller.checkContainment(new Input(0, 0, 2, List.of(8, 20, 15, -4), List.of())));
    }

    @Test
    void should_count_contained_points() {
        Controller controller = new Controller();
        var input = new Input(5, -5, 10, List.of(2, 1, 3, 8, 4, -12, -20, -4), List.of(8, 1, 20, -4, 15, -4, -20, -4));

        var response = controller.checkContainment(input);

        assertEquals(3, response.numberOfContainedPoints());
    }

    @Test
    void should_count_contained_points_when_moved_to_new_location() {

        Controller controller = new Controller();

        var response = controller.checkContainment(new Input(0, 0, 20, List.of(2, 3, 4, -12, -20), List.of(8, 20, 15, -4, -20)));

        assertEquals(3, response.numberOfContainedPoints());

        var afterMovement = controller.checkContainment(new Input(-30, -30, 20, List.of(2, 3, 4, -12, -20), List.of(8, 20, 15, -4, -20)));

        assertEquals(1, afterMovement.numberOfContainedPoints());
    }

}
