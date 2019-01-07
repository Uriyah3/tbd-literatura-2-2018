package com.tbd.elasticsearch.moduloNeo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface BookRepositoryNeo4j extends Neo4jRepository<BookNode,Long> {

    Collection<BookNode> findAll();
    BookNode findByTitle(String title);


    @Query("MATCH (b:BookNode)<-[r:Tweeted_about]-(u:UserNode) RETURN b,r,u LIMIT {limit}")
    Collection<BookNode> graphBook(@Param("limit") int limit);

}
