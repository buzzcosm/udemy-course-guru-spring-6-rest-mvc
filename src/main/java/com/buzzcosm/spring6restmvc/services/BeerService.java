package com.buzzcosm.spring6restmvc.services;

import com.buzzcosm.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Optional<BeerDTO> patchBeerById(UUID id, BeerDTO beer);

    Boolean deleteBeerById(UUID id);
}
