package com.danilo.learn.starwarsplanetapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Testes Integrados end2end e de componentes (subcutaneos)
 * SpringBootTest - inicializa o contexto da aplicação para teste
 */
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanetIT {

    //Testa o contexto da aplicação e valida as configurações dos beans
    @Test
    public void contextLoads() {
    }
}
