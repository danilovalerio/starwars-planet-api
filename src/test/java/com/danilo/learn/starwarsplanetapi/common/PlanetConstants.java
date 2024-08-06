package com.danilo.learn.starwarsplanetapi.common;

import com.danilo.learn.starwarsplanetapi.domain.Planet;

import java.util.Arrays;
import java.util.List;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");

    public static final Planet TATOOINE = new Planet(1L, "Tatooine", "arid", "desert");
    public static final Planet Alderaan = new Planet(2L,"Alderaan", "temperate", "grasslands, mountains");
    public static final Planet YAVINIV = new Planet(3L, "Yavin IV", "temperate, tropical", "jungle, rainfores");

    public static final List<Planet> PLANETS = Arrays.asList(TATOOINE, Alderaan, YAVINIV);

}
