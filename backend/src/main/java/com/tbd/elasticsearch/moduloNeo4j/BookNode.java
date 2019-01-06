package com.tbd.elasticsearch.moduloNeo4j;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class BookNode {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;


   /* @JsonIgnoreProperties("BookNode")
    @Relationship(type = "Tweeted_about", direction = Relationship.INCOMING)
    private List<String> tweets;
*/
    public BookNode(){

    }

    public BookNode(String title, String author){
        this.author = author;
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
