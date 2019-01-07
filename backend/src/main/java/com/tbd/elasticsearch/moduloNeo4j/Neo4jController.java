package com.tbd.elasticsearch.moduloNeo4j;


import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge=3600)
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
    public void insertData() {
        bookServiceNeo4j.insertBooks();
        System.out.println("Libros insertados\n");
        genreServiceNeo4j.insertGenres();
        System.out.println("Generos insertados\n");
        userServiceNeo4j.insertUsers();
        System.out.println("usuarios insertados\n");
        bookServiceNeo4j.insertUsersBookTweeted();
        genreServiceNeo4j.insertUsersGenreTweeted();
    }

    @GetMapping("/insertBooks")
    public void insertBooks() {
        bookServiceNeo4j.insertBooks();
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

    @GetMapping("/users")
    public Collection<UserNode> findAllUsers(){
        return this.userServiceNeo4j.getAllUsers();
    }

    @GetMapping("/testBook")
    public List<BookNode> findBooksByUsers(@RequestParam String screenName){
        UserNode userNode = userServiceNeo4j.findByScreenName(screenName);
        if (userNode!=null)
            return userNode.getBooks();
        return null;

    }
    @GetMapping("/testGenre")
    public List<GenreNode> findGenresByUsers(@RequestParam String screenName){
        UserNode userNode = userServiceNeo4j.findByScreenName(screenName);
        if (userNode!=null)
            return userNode.getGenres();
        return null;

    }

    @GetMapping("/genres")
    public Collection<GenreNode> findAllGenres(){
        return genreServiceNeo4j.findAllGenres();
    }

    @GetMapping("/tweetedGenre")
    public Collection<UserNode> tweetedGenre(@RequestParam String name){
        return this.userServiceNeo4j.tweetedGenre(name);
    }


    @GetMapping("/tweetedBook")
    public Collection<UserNode> tweetedBook(@RequestParam String name){
        return this.userServiceNeo4j.tweetedBook(name);
    }


    @GetMapping("/book")
    public BookNode findBookByTitle(@RequestParam String title){
        return this.bookServiceNeo4j.findByTitle(title);
    }

    @GetMapping("/books")
    public Collection<BookNode> findAllBooks(){
        return this.bookServiceNeo4j.findAll();
    }

    @GetMapping("/graphBook")
    public Map<String, Object> graphBooks(@RequestParam(value = "limit",required = false) Integer limit) {
        return userServiceNeo4j.graph(limit == null ? 100 : limit,"book");
    }
    @GetMapping("/graphGenre")
    public Map<String, Object> graphGenres(@RequestParam(value = "limit",required = false) Integer limit) {
        return userServiceNeo4j.graph(limit == null ? 100 : limit,"genre");
    }
    @GetMapping("/graph")
    public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
        return userServiceNeo4j.graph(limit == null ? 100 : limit,"all");
    }



}
