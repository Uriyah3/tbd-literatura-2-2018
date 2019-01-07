package com.tbd.elasticsearch.moduloNeo4j;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class GenreNode {


    @Id
    @GeneratedValue
    private Long id;
    private String name;


    /*@JsonIgnoreProperties("GenreNode")
    @Relationship(type = "Tweeted_about", direction = Relationship.INCOMING)
    private List<String> tweets;
*/

    public GenreNode(){

    }

    public GenreNode(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
