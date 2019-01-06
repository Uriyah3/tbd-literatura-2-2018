package com.tbd.elasticsearch.moduloNeo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface BookRepositoryNeo4j extends Neo4jRepository<BookNode,Long> {

    Collection<BookNode> findAll();
    BookNode findByTitle(@Param("title") String title);
}
