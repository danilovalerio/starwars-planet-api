package com.danilo.learn.starwarsplanetapi.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
    @Test
    public void createPlanet_WithInvalidData_ReturnsThrowsException() {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() ->planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);;
    }

    @Test
    public void createPlanet_WithExistingName_ReturnsThrowsException() {
        //prepara as dados para teste, persistFlushFind salva o planeta no banco, busca e retorna ele
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet); //detach desconecta o objeto do entityManager, assim não é mais gerenciado,
        planet.setId(null); //limpa o id para poder inserir um novo planeta de mesmo nome

        assertThatThrownBy(() -> planetRepository.save(planet));
    }
}
