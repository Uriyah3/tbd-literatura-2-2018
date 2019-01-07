/*package com.tbd.elasticsearch.moduloNeo4j;


import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RelationshipEntity(type = "Tweeted_about")
public class Tweeted {

    @Id
    @GeneratedValue
    private Long id;

    @Properties
    private Map<String, String> tweet;

    @StartNode
    private UserNode userNode;

    @EndNode
    private BookNode bookNode;


    public Tweeted(){

    }


    public Tweeted(UserNode userNode, BookNode bookNode){
        this.bookNode = bookNode;
        this.userNode = userNode;
    }


    public UserNode getUserNode() {
        return userNode;
    }

    public BookNode getBookNode() {
        return bookNode;
    }



    public void setTweet(Map<String,String> tweet) {
        this.tweet = tweet;
    }

    public Map<String, String> getTweet() {
        return tweet;
    }
}
*/