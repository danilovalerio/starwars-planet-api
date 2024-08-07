package com.danilo.learn.starwarsplanetapi;

import com.danilo.learn.starwarsplanetapi.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Testes Integrados end2end e de componentes (subcutaneos)
 * SpringBootTest - inicializa o contexto da aplicação para teste
 */
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/import_planets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/remove_planets.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PlanetIT {
    @Autowired
    private TestRestTemplate restTemplate;

    //Testa o contexto da aplicação e valida as configurações dos beans
    @Test
    public void contextLoads() {
    }

    @Test
    public void createPlanet_ReturnsCreated() {
        //ForEntity - traduz o JSON para uma Entidade
        ResponseEntity<Planet> sut = restTemplate.postForEntity("/planets", PLANET, Planet.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().getId()).isNotNull();
        assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
        assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
    }

    @Test
    public void getPlanet_ReturnsPlanet() {

        ResponseEntity<Planet> sut = restTemplate.getForEntity("/planets/1", Planet.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(TATOOINE);

    }

    @Test
    public void getPlanetByName_ReturnsPlanet() {

        ResponseEntity<Planet> sut = restTemplate.getForEntity("/planets/name/Tatooine", Planet.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(TATOOINE);

    }

    @Test
    public void listPlanets_ReturnsAllPlanet() {

        ResponseEntity<Planet[]> sut = restTemplate.getForEntity("/planets", Planet[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(3);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
    }

    @Test
    public void listPlanets_ByClimate_ReturnsAllPlanet() {

        ResponseEntity<Planet[]> sut = restTemplate.getForEntity("/planets?climate=temperate", Planet[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(1);
        assertThat(sut.getBody()[0]).isEqualTo(Alderaan);
    }

    @Test
    public void listPlanets_ByTerrain_ReturnsAllPlanet() {

        ResponseEntity<Planet[]> sut = restTemplate.getForEntity("/planets?terrain=desert", Planet[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(1);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
    }

    @Test
    public void removePlanet_ReturnsNoContent() {

        ResponseEntity<Void> sut = restTemplate.exchange("/planets/1", HttpMethod.DELETE, null, Void.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
