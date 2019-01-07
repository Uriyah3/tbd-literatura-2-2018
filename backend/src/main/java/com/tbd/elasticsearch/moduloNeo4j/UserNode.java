package com.tbd.elasticsearch.moduloNeo4j;


import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NodeEntity
public class UserNode {


    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String screenName;
    private int followersCount;
    private int friendsCount;
    private boolean isVerified;
    private String location;
    private Integer score;
    //private List<Tweeted> tweets;
    @Relationship(type = "Tweeted_about")
    private List<BookNode> books = new ArrayList<>();

    @Relationship(type = "Tweeted_about")
    private List<GenreNode> genres = new ArrayList<>();




    public UserNode(){

    }

    public UserNode(Long id,String name, String screenName, int followersCount, int friendsCount, String location, boolean isVerified, Integer score){
        //this.id = id;
        this.name = name;
        this.screenName = screenName;
        this.followersCount = followersCount;
        this.friendsCount = friendsCount;
        this.location = location;
        this.isVerified = isVerified;
        this.score = score;
    }

    public void tweetedBook(BookNode bookNode){
        books.add(bookNode);
    }

    public void tweetedGenre(GenreNode genreNode){
        genres.add(genreNode);
    }
/*

    public void setTweets(List<Tweeted> tweets) {
        this.tweets = tweets;
    }

    public void addTweets(Tweeted tweet){
        if (this.tweets == null)
            this.tweets = new ArrayList<>();
        this.tweets.add(tweet);
    }*/
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public List<BookNode> getBooks() {
        return books;
    }


    public List<GenreNode> getGenres() {
        return genres;
    }

    public Integer getScore() {
        return score;
    }
}
