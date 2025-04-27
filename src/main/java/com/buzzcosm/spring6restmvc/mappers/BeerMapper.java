package com.buzzcosm.spring6restmvc.mappers;

import com.buzzcosm.spring6restmvc.entities.Beer;
import com.buzzcosm.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDto);

    BeerDTO beerToBeerDto(Beer beer);
}
