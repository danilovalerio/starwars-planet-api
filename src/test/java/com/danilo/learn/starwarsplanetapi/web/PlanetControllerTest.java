package com.danilo.learn.starwarsplanetapi.web;

import com.danilo.learn.starwarsplanetapi.domain.Planet;
import com.danilo.learn.starwarsplanetapi.domain.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @WebMvcTest - configura para testar o controlador, injeta o controlador e monta um CONTEXTO WEB para interagirmos,
 * entre parenteses especificamos somente o controlador que
 * iremos testar para economizar o uso de recursos ao executar os tesets
 */
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {
    @Autowired
    private MockMvc mockMvc; //moca contexto web

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean //Injeta o mock de planetService para usarmos no test
    private PlanetService planetService;

    @Test
    public void createPlanet_withValidData_ReturnsCreated() throws Exception {
        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc
                .perform(post("/planets")
                .content(objectMapper.writeValueAsString(PLANET))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void createPlanet_withInvalidData_ReturnsBadRequest() throws Exception {

        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        mockMvc
                .perform(post("/planets")
                .content(objectMapper.writeValueAsString(emptyPlanet))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc
                .perform(post("/planets")
                        .content(objectMapper.writeValueAsString(invalidPlanet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void createPlanet_withExistingName_ReturnsConflict() throws Exception {
        //Qualquer planeta que eu passar nesse teste no create do planet service vai lancar uma excessão
        when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);
        mockMvc
                .perform(post("/planets")
                        .content(objectMapper.writeValueAsString(PLANET))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getPlanet_byExistingId_ReturnsPlanet() throws Exception {
        Planet planetComId = new Planet( "name", "climate", "terrain");
        planetComId.setId(1L);

        when(planetService.get(1L)).thenReturn(java.util.Optional.of(planetComId));

        mockMvc
                .perform(get("/planets/1")
                        .content(objectMapper.writeValueAsString(planetComId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(planetComId));
    }

    @Test
    public void getPlanet_byUnexistingId_ReturnsNotFound() throws Exception {
        mockMvc
                .perform(get("/planets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPlanet_byExistingName_ReturnsPlanet() throws Exception {
        when(planetService.getByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        mockMvc
                .perform(get("/planets/name/" + PLANET.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_byUnexistingName_ReturnsNotFound() throws Exception {
        mockMvc
                .perform(get("/planets/name/" + PLANET.getName()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listPlanets_ReturnsFilteredPlanets() throws Exception {
        when(planetService.list(null, null)).thenReturn(PLANETS);
        when(planetService.list(TATOOINE.getTerrain(), TATOOINE.getClimate())).thenReturn(List.of(TATOOINE));

        mockMvc
                .perform(get("/planets"))
                .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));

        mockMvc
                .perform(get("/planets?"+
                        String.format("terrain=%s&climate=%s", TATOOINE.getTerrain(), TATOOINE.getClimate())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value(TATOOINE));


    }

    @Test
    public void listPlanets_ReturnsNoPlanets() throws Exception {

        when(planetService.list(null, null)).thenReturn(Collections.emptyList());
        mockMvc
                .perform(get("/planets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));


    }

    @Test
    public void removePlanet_withExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/planets/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    public void removePlanet_withUnexistingId_ReturnsNotFound() throws Exception {
        final long planetId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(planetService).remove(planetId);

        mockMvc
                .perform(delete("/planets/" + planetId))
                .andExpect(status().isNotFound());

    }

}
