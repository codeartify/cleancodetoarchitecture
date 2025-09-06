package com.codeartify.underengineered.adapter.presentation;


import com.codeartify.underengineered.application.port.inbound.SearchRealEstate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealEstateSearchController {

     private final SearchRealEstate searchRealEstate;

    public RealEstateSearchController(SearchRealEstate searchRealEstate) {
         this.searchRealEstate = searchRealEstate;
    }

    @GetMapping("/api/realestate")
    public Response searchRealEstate(
            @RequestParam(name = "x", required = false) Double x,
            @RequestParam(name = "y", required = false) Double y,
            @RequestParam(name = "r", required = false) Double radius)
            throws Exception {
        var results = searchRealEstate.execute(x, y, radius);

        return new Response(results);
    }


}
