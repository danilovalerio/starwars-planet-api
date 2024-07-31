package com.danilo.learn.starwarsplanetapi.domain;

import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.INVALID_PLANET;
import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        assertThat(sut).isEqualTo(PLANET);
    }

    // testando cenário de erro, quando dados invalidos e quando planeta existe
    @Test
    public void createPlanet_WithInvalidData_ReturnsThrowsException(){
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
       when(planetRepository.findById(1L)).thenReturn(java.util.Optional.of(PLANET));

       Optional<Planet> sut = planetService.get(1L);

       assertThat(sut).isNotEmpty();
       assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByNonExistingId_ReturnsEmpty() {
        when(planetRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Optional<Planet> sut = planetService.get(1L);

        assertThat(sut).isEmpty();
    }

}
