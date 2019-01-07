package com.tbd.elasticsearch.moduloNeo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface GenreRepositoryNeo4j extends Neo4jRepository<GenreNode, Long> {


    Collection<GenreNode> findAll();
    GenreNode findByName(String name);


    @Query("MATCH (g:GenreNode)<-[r:Tweeted_about]-(u:UserNode) RETURN g,r,u LIMIT {limit}")
    Collection<GenreNode> graph(@Param("limit") int limit);

}
