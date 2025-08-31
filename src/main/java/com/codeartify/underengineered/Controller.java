package com.codeartify.underengineered;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final PropertyRepository propertyRepository;

    public Controller(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/api/realestate")
    public Response searchProperties(@RequestParam(required = false) Double x,
                                     @RequestParam(required = false) Double y,
                                     @RequestParam(required = false) Double r) throws Exception {
        var search = PropertySearch.from(x, y, r);

        var properties = propertyRepository.findAll();

        var results = search.findContained(properties);

        return new Response(results);
    }


}
