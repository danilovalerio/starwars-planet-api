package com.danilo.learn.starwarsplanetapi.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
/**
 * @DataJpaTest - cria o banco de dados H2 (banco de teste em memória) para testarmos as funcionalidades do repostory
 * @TestEntityManager - cria o entityManager para termos acesso ao repository sem usar a classe alvo de teste (PlanetRepository)
 *
 * @author Danilo Valerio da Silva
 */

@DataJpaTest
public class PlanetRepositoryTest {
    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        PLANET.setId(null);
    }

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        Planet planet = planetRepository.save(PLANET);

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        System.out.println(planet); // DEBUG visualiza os dados do objeto salvo

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(planet.getName());
        assertThat(sut.getClimate()).isEqualTo(planet.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(planet.getTerrain());
    }

    /**
     * Aqui através de constraints do banco não vamos permitir dados vazios ou um planeta vazio
     * Lançando exception
     */
    @ParameterizedTest
    @MethodSource("providesInvalidPlanets")
    public void createPlanet_WithInvalidData_ReturnsThrowsException(Planet planet) {
        assertThatThrownBy(() ->planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    private static Stream<Arguments>  providesInvalidPlanets() {
        return Stream.of(
                Arguments.of(new Planet("", "", "")),
                Arguments.of(new Planet(null, null, null)),
                Arguments.of(new Planet(null, "Tatooine", "arid")),
                Arguments.of(new Planet("Tatooine", null, "arid")),
                Arguments.of(new Planet("Tatooine", "arid", null)),
                Arguments.of(new Planet(null, null, "arid")),
                Arguments.of(new Planet(null, "Tatooine", null)),
                Arguments.of(new Planet("Tatooine", null, null)),
                Arguments.of(new Planet(null, null, null)),
                Arguments.of(new Planet("", "Tatooine", "arid")),
                Arguments.of(new Planet("Tatooine", "", "arid")),
                Arguments.of(new Planet("Tatooine", "arid", "")),
                Arguments.of(new Planet("", "", "arid")),
                Arguments.of(new Planet("", "Tatooine", "")),
                Arguments.of(new Planet("Tatooine", "", "")),
                Arguments.of(new Planet("", "", ""))
        );

    }

    @Test
    public void createPlanet_WithExistingName_ReturnsThrowsException() {
        //prepara as dados para teste, persistFlushFind salva o planeta no banco, busca e retorna ele
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet); //detach desconecta o objeto do entityManager, assim não é mais gerenciado,
        planet.setId(null); //limpa o id para poder inserir um novo planeta de mesmo nome

        assertThatThrownBy(() -> planetRepository.save(planet));
    }

    @Test
    public void getPlanet_byExistingId_ReturnsPlanet() throws Exception {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        //testEntityManager.detach(planet);
        //planet.setId(1L);

        Optional<Planet> planetLocalized = planetRepository.findById(planet.getId());

        assertThat(planetLocalized).isNotNull();
        assertThat(planetLocalized.get()).isEqualTo(planet);

    }

    @Test
    public void getPlanet_byUnexistingId_ReturnsEmpty() throws Exception {
        Optional<Planet> planetLocalized = planetRepository.findById(1L);

        assertThat(planetLocalized).isEmpty();

    }

    @Test
    public void getPlanet_byExistingName_ReturnsPlanet() throws Exception {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> planetLocalized = planetRepository.findByName(planet.getName());

        assertThat(planetLocalized).isNotNull();
        assertThat(planetLocalized.get()).isEqualTo(planet);

    }

    @Test
    public void getPlanet_byUnexistingName_ReturnsEmpty() throws Exception {
        Optional<Planet> planetLocalized = planetRepository.findByName("Unexisting Name");

        assertThat(planetLocalized).isEmpty();

    }

    @Sql(scripts = "/import_planets.sql")
    @Test
    public void listPlanets_ReturnsFilteredPlanets() throws Exception {
        Example<Planet> queryWithoutFilters = QueryBuilder.makeQuery(new Planet());
        Example<Planet> queryWithFilters = QueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()));

        List<Planet> responseWithoutFilters = planetRepository.findAll(queryWithoutFilters);
        List<Planet> responseWithFilters = planetRepository.findAll(queryWithFilters);

        assertThat(responseWithoutFilters).isNotEmpty();
        assertThat(responseWithoutFilters).hasSize(3);

        assertThat(responseWithFilters).isNotEmpty();
        assertThat(responseWithFilters).hasSize(1);
        assertThat(responseWithFilters.get(0)).isEqualTo(TATOOINE);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() throws Exception {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet());

        List<Planet> response = planetRepository.findAll(query);

        assertThat(response).isEmpty();
    }

    @Test
    public void removePlanet_withExistingId_ReturnsRemovesPlanetFromDatabase() {

        Planet planet = testEntityManager.persistFlushFind(PLANET);

        planetRepository.deleteById(planet.getId());

        Planet removedPlanet = testEntityManager.find(Planet.class, planet.getId());
        assertThat(removedPlanet).isNull();

    }
}
