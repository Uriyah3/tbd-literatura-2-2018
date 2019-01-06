package com.tbd.elasticsearch.moduloNeo4j;


import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

    private UserServiceNeo4j userServiceNeo4j;
    private BookServiceNeo4j bookServiceNeo4j;
    private GenreServiceNeo4j genreServiceNeo4j;

    public Neo4jController(UserServiceNeo4j userServiceNeo4j, BookServiceNeo4j bookServiceNeo4j, GenreServiceNeo4j genreServiceNeo4j){
        this.userServiceNeo4j = userServiceNeo4j;
        this.bookServiceNeo4j = bookServiceNeo4j;
        this.genreServiceNeo4j = genreServiceNeo4j;
    }

    @GetMapping("/insertData")
    public void insertBooks() {
        bookServiceNeo4j.insertBooks();
        System.out.println("Libros insertados\n");
        genreServiceNeo4j.insertGenres();
        System.out.println("Generos insertados\n");
        userServiceNeo4j.insertUsers();
        System.out.println("usuarios insertados\n");
    }

    @GetMapping("/insertGenres")
    public Collection<GenreNode> insertGenres(){
        return genreServiceNeo4j.insertGenres();
    }

    @GetMapping("/insertUsers")
    public Collection<UserNode> insertUsers(){
        return userServiceNeo4j.insertUsers();
    }

    @GetMapping("/user")
    public UserNode findUserByName(@RequestParam String name){
        return this.userServiceNeo4j.findByName(name);
    }

    @GetMapping("/tweetedBook")
    public Collection<UserNode> tweetedBook(@RequestParam String name){
        return this.userServiceNeo4j.tweetedBook(name);
    }

    @GetMapping("/tweetedGenre")
    public Collection<UserNode> tweetedGenre(@RequestParam String name){
        return this.userServiceNeo4j.tweetedGenre(name);
    }

    @GetMapping("/users/book")
    public List<Map<String,Object>> getBookTweeted(@RequestParam String title){
        return this.bookServiceNeo4j.insertUsersTweeted(title);
    }

    @GetMapping("/book")
    public BookNode findBookByTitle(@RequestParam String title){
        return this.bookServiceNeo4j.findByTitle(title);
    }

    @GetMapping("/books")
    public Collection<BookNode> findAllBooks(){
        return this.bookServiceNeo4j.findAll();
    }
}
