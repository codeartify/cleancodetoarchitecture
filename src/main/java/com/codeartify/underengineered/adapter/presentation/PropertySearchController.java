package com.codeartify.underengineered.adapter.presentation;


import com.codeartify.underengineered.application.port.inbound.SearchProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertySearchController {

     private final SearchProperties searchProperties;

    public PropertySearchController(SearchProperties searchProperties) {
         this.searchProperties = searchProperties;
    }

    @GetMapping("/api/realestate")
    public Response checkContainment(@RequestParam(required = false) Double x,
                                     @RequestParam(required = false) Double y,
                                     @RequestParam(required = false) Double radius) throws Exception {

        var results = searchProperties.execute(x, y, radius);

        return new Response(results);
    }


}
