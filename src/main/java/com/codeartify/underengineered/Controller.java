package com.codeartify.underengineered;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @PostMapping("/api/primitive/checkContainment")
    public Response checkContainment(@RequestBody Input input) {
        var points = PointsFactory.createPointsFromCoordinates(input.xCoords(), input.yCoords());

        var circle = Circle.toCircle(input.x(), input.y(), input.r());

        var numberOfContainedPoints = circle.countContained(points);

        return new Response(numberOfContainedPoints);
    }


}

