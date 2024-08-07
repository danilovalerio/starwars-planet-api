package com.danilo.learn.starwarsplanetapi;

import com.danilo.learn.starwarsplanetapi.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Testes Integrados end2end e de componentes (subcutaneos)
 * SpringBootTest - inicializa o contexto da aplicação para teste
 */
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
}
