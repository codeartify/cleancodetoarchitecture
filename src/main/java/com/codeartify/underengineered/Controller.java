package com.codeartify.underengineered;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final RealEstateRepository realEstateRepository;

    public Controller(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    @GetMapping("/api/realestate")
    public Response searchRealEstate(
            @RequestParam(name = "x", required = false) Double x,
            @RequestParam(name = "y", required = false) Double y,
            @RequestParam(name = "r", required = false) Double r)
            throws Exception {
        var search = RealEstateSearch.from(x, y, r);

        var realEstate = realEstateRepository.findAll();

        var results = search.findContained(realEstate);

        return new Response(results);
    }


}
