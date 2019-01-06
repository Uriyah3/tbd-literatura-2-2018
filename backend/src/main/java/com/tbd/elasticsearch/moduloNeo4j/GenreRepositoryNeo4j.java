package com.tbd.elasticsearch.moduloNeo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;

public interface GenreRepositoryNeo4j extends Neo4jRepository<GenreNode, Long> {


    Collection<GenreNode> findAll();
    GenreNode findByName(String name);

}
