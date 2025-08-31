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
    public Response checkContainment(@RequestParam(required = false) Double x,
                                     @RequestParam(required = false) Double y,
                                     @RequestParam(required = false) Double r) throws Exception {

        var results = propertySearchService.execute(x, y, r);

        return new Response(results);
    }


}
