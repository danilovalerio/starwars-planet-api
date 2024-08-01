package com.danilo.learn.starwarsplanetapi.domain;

import com.danilo.learn.starwarsplanetapi.common.PlanetConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
        Planet planet = planetRepository.save(PlanetConstants.PLANET);
    }
}
