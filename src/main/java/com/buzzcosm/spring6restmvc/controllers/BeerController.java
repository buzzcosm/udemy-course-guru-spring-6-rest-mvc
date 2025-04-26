package com.buzzcosm.spring6restmvc.controllers;

import com.buzzcosm.spring6restmvc.model.Beer;
import com.buzzcosm.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(
            @PathVariable("beerId") UUID beerId,
            @RequestBody Beer beer
    ) {

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveNewBeer(@RequestBody Beer beer) {

        log.debug("Save new beer - in controller: {}", beer);

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID id) {

        log.debug("Get Beer by Id in controller was called");

        return beerService.getBeerById(id);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id) {

        beerService.deleteBeerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
