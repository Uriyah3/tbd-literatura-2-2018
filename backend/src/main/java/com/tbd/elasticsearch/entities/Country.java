package com.tbd.elasticsearch.entities;

import javax.persistence.*;

@Entity
@Table(name = "country")
public class Country {

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


    public String getName() {
        return name;
    }
}
