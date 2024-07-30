package com.danilo.learn.starwarsplanetapi.domain;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.danilo.learn.starwarsplanetapi.common.PlanetConstants;

//@SpringBootTest(classes = PlanetService.class) // monta o contexto do spring apenas com a classe adicionada e deixa disponível para injecao
@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    // @Autowired
    @InjectMocks // instancia o planetservice e injeta dependencias com mock
    private PlanetService planetService;

    // @MockBean
    @Mock
    private PlanetRepository planetRepository;

    // operacao_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        // Princípio AAA dos Testes (Arrange Act Assert)
        // Arrange (prepara os dados)
        when(planetRepository.save(PlanetConstants.PLANET)).thenReturn(PlanetConstants.PLANET);

        // system under test (sut)
        // Act (agir, fazer a operação de fato)
        Planet sut = planetService.create(PlanetConstants.PLANET);

        // Assert (aferir se o sistema sobre teste é o que esperada)
        Assertions.assertThat(sut).isEqualTo(PlanetConstants.PLANET);
    }

}
