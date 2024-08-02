package com.danilo.learn.starwarsplanetapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @WebMvcTest - configura para testar o controlador, injeta o controlador e monta um CONTEXTO WEB para interagirmos,
 * entre parenteses especificamos somente o controlador que
 * iremos testar para economizar o uso de recursos ao executar os tesets
 */
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {
    @Autowired
    private MockMvc mockMvc; //moca contexto web

    @Test
    public void createPlanet_withValidData_ReturnsCreated() {
        mockMvc.perform(post("/planets")
                .contentType("application/json")
                .content("{\"name\": \"Tatooine\", \"climate\": \"arid\", \"terrain\": \"desert\"}"))
                .andExpect(status().isCreated());
    }


}
