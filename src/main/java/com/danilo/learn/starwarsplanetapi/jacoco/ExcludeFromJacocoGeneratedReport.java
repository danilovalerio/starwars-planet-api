package com.danilo.learn.starwarsplanetapi.jacoco;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Irá ser executada em tempo de execução para o
 * método que tiver essa anotação
 *
 *
 *
 * @author Danilo Valerio da Silva
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcludeFromJacocoGeneratedReport {
}
