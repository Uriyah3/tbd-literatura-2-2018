package com.tbd.elasticsearch.moduloNeo4j;


import com.tbd.elasticsearch.entities.Genre;
import com.tbd.elasticsearch.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GenreServiceNeo4j {


    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreRepositoryNeo4j genreRepositoryNeo4j;

    public Collection<GenreNode> insertGenres(){

        //Se eliminan para evitar duplicados
        if(!genreRepositoryNeo4j.findAll().isEmpty())
            genreRepositoryNeo4j.deleteAll();

        GenreNode genreNode;
        List<Genre> genres = genreRepository.findAll();

        //Se agregan los generos sin relaciones
        for (Genre genre:genres) {
            genreNode = new GenreNode(genre.getName());
            genreRepositoryNeo4j.save(genreNode);

        }

        return genreRepositoryNeo4j.findAll();
    }

}
