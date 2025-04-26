package com.buzzcosm.spring6restmvc.controllers;

import com.buzzcosm.spring6restmvc.model.Beer;
import com.buzzcosm.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    private static final String BEER_PATH = "/api/v1/beers";
    private static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    /**
     * Create a new beer
     */
    @PostMapping(BEER_PATH)
    public ResponseEntity saveNewBeer(@RequestBody Beer beer) {
        log.debug("Save new beer - in controller: {}", beer);
        Beer savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Read: Get all beers
     */
    @GetMapping(BEER_PATH)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    /**
     * Read: Get a specific beer by Id
     */
    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Get Beer by Id in controller was called");
        return beerService.getBeerById(id);
    }

    /**
     * Update specific properties of a beer by Id
     */
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID id,
                                              @RequestBody Beer beer) {
        log.debug("Patch Beer by Id - in controller was called");
        beerService.patchBeerById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Update a specific beer by Id
     */
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId,
                                         @RequestBody Beer beer) {
        beerService.updateBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete a specific beer by Id
     */
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id) {
        beerService.deleteBeerById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
