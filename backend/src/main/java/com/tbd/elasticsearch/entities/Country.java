package com.tbd.elasticsearch.entities;

import javax.persistence.*;

@Entity
@Table(name = "country")
public class Country {

    public Country(){
    }

    public Country(String name){
        this.name=name;
    }
    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "capital")
    String capital;

    @Column(name = "polygon")
    String polygon;

    @Column(name = "hits")
    int hits;

    @Column(name = "population")
    int population;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPolygon() {
        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getName() {
        return name;
    }
}
