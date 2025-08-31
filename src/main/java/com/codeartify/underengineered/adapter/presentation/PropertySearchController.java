package com.codeartify.underengineered.adapter.presentation;


import com.codeartify.underengineered.application.PropertySearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertySearchController {

     private final PropertySearchService propertySearchService;

    public PropertySearchController(PropertySearchService propertySearchService) {
         this.propertySearchService = propertySearchService;
    }

    @GetMapping("/api/realestate")
    public Response searchProperties(
            @RequestParam(name = "x", required = false) Double x,
            @RequestParam(name = "y", required = false) Double y,
            @RequestParam(name = "r", required = false) Double radius)
            throws Exception {

        var results = propertySearchService.execute(x, y, radius);

        return new Response(results);
    }


}
