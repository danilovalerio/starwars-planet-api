package com.danilo.learn.starwarsplanetapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testes end2end e de componentes (subcutaneos)
 * SpringBootTest - inicializa o contexto da aplicação para teste
 */
@SpringBootTest()
public class PlanetIT {

    //Testa o contexto da aplicação e valida as configurações dos beans
    @Test
    public void contextLoads() {
    }
}
