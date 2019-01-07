package com.tbd.elasticsearch.moduloNeo4j;


import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.entities.Genre;
import com.tbd.elasticsearch.repository.BookRepository;
import com.tbd.elasticsearch.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class GenreServiceNeo4j {


    @Autowired
    private BookRepositoryNeo4j bookRepositoryNeo4j;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private UserServiceNeo4j userServiceNeo4j;
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

    public Collection<UserNode> insertUsersGenreTweeted(){
        Book book;
        List<BookNode> bookNodes;
        Genre genre;
        for (UserNode user:userServiceNeo4j.getAllUsers()) {
            bookNodes = user.getBooks();
            for (BookNode bookNode:bookNodes) {
                book = bookRepository.findBookByName(bookNode.getTitle());
                genre = genreRepository.findGenreById(book.getGenreId());
                user.tweetedGenre(genreRepositoryNeo4j.findByName(genre.getName()));
                this.userServiceNeo4j.updateUser(user);
            }
        }
        return userServiceNeo4j.getAllUsers();

    }


    public Collection<GenreNode> findAllGenres(){
        return genreRepositoryNeo4j.findAll();
    }

}
