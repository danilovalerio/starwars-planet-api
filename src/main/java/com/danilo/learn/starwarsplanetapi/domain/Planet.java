package com.danilo.learn.starwarsplanetapi.domain;

import com.danilo.learn.starwarsplanetapi.jacoco.ExcludeFromJacocoGeneratedReport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "planets")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    @NotEmpty(message = "Climate cannot be empty")
    @Column(nullable = false)
    private String climate;

    @NotEmpty(message = "Terrain cannot be empty")
    @Column(nullable = false)
    private String terrain;

    public Planet() {
    }

    public Planet(String name, String climate, String terrain) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    public Planet(Long id, String name, String climate, String terrain) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }


    public Planet(String climate, String terrain) {
        this.climate = climate;
        this.terrain = terrain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    // equals para validacao de igualdade do objeto
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }

    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Planet [id=" + id + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain + "]";
    }

}
