package com.danilo.learn.starwarsplanetapi.domain;

import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<Planet, Long> {

}
