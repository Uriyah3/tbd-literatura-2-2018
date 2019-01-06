package com.tbd.elasticsearch.moduloNeo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRepositoryNeo4j extends Neo4jRepository<UserNode, Long> {

    UserNode findByName(@Param("name") String name);

    Collection<UserNode> findAll();

    @Query("MATCH (x:BookNode)<-[Tweeted_about]-(u:UserNode) RETURN u,x")
    Collection<UserNode> tweetedBook(@Param("name") String name);

    @Query("MATCH (x:GenreNode)<-[Tweeted_about]-(u:UserNode) RETURN u,x")
    Collection<UserNode> tweetedGenre(@Param("name") String name);

}
