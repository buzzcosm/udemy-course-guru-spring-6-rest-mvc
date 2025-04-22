package com.buzzcosm.spring6restmvc.services;

import com.buzzcosm.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}
