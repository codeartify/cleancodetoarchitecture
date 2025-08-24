package com.codeartify.primitive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @PostMapping("/api/primitive/checkContainment")
    public Response checkContainment(@RequestBody Input input) {
        var numberOfContainedPoints = 0;
        if (input.xCoords() != null) {
            if (!input.xCoords().isEmpty()) {
                if (input.yCoords() != null) {
                    if (!input.yCoords().isEmpty()) {
                        if (input.xCoords().size() == input.yCoords().size()) {
                            for (int i = 0; i < input.xCoords().size(); ++i) {
                                var result = (input.xCoords().get(i) - input.x()) * (input.xCoords().get(i) - input.x()) + (input.yCoords().get(i) - input.y()) * (input.yCoords().get(i) - input.y()) <= input.r() * input.r();
                                if (result) {
                                    numberOfContainedPoints++;
                                }
                            }
                        } else {
                            throw new RuntimeException("Not every provided x coordinate has a matching y coordinate");
                        }
                    } else {
                        throw new RuntimeException("y coordinates are empty");
                    }
                } else {
                    throw new RuntimeException("y coordinates are empty");
                }
            } else {
                throw new RuntimeException("x coordinates are empty");
            }
        } else {
            throw new RuntimeException("x coordinates are empty");
        }
        return new Response(numberOfContainedPoints);
    }


}

