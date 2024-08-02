package com.danilo.learn.starwarsplanetapi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.danilo.learn.starwarsplanetapi.common.PlanetConstants.PLANET;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void createPlanet_withValidData_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/planets")
                .content(objectMapper.writeValueAsString(PLANET)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET));
    }


}
