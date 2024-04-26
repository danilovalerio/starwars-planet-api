package com.danilo.learn.starwarsplanetapi.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danilo.learn.starwarsplanetapi.common.PlanetConstants;

@SpringBootTest(classes = PlanetService.class) // monta o contexto do spring apenas com a classe adicionada e deixa
                                               // disponível para injecao
public class PlanetServiceTest {
    @Autowired
    private PlanetService planetService;

    // operacao_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        // system under test
        Planet sut = planetService.create(PlanetConstants.PLANET);

        Assertions.assertThat(sut).isEqualTo(PlanetConstants.PLANET);

    }

}
