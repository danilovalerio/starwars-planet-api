package com.danilo.learn.starwarsplanetapi.domain;

import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.INVALID_PLANET;
import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        // system under test (sut)
        // Act (agir, fazer a operação de fato)
        Planet sut = planetService.create(PLANET);

        // Assert (aferir se o sistema sobre teste é o que esperada)
        Assertions.assertThat(sut).isEqualTo(PLANET);
    }

    // testando cenário de erro
    @Test
    public void createPlanet_WithInvalidData_ReturnsThrowsException(){
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

}
