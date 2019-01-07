package com.tbd.elasticsearch.moduloNeo4j;


import com.tbd.elasticsearch.entities.Author;
import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.moduloElasticSearch.TweetCortoDao;
import com.tbd.elasticsearch.repository.AuthorRepository;
import com.tbd.elasticsearch.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.neo4j.ogm.session.Utils.map;

@Service
public class BookServiceNeo4j {

    @Autowired
    private UserServiceNeo4j userServiceNeo4j;
    @Autowired
    private UserRepositoryNeo4j userRepositoryNeo4j;
    @Autowired
    private  TweetCortoDao tweetCortoDao;

    private BookRepositoryNeo4j bookRepositoryNeo4j;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;


    public Collection<BookNode> insertBooks(){

        //Se eliminan para evitar duplicados
        if(!(bookRepositoryNeo4j.findAll()).isEmpty())
            bookRepositoryNeo4j.deleteAll();


        List<Book> books = bookRepository.findAll();
        BookNode bookNode;
        Author author;
        //Inserta todos los libros sin relaciones
        for (Book book:books) {
            author = authorRepository.findAuthorById(book.getAuthorId());
            bookNode = new BookNode(book.getName(),author.getName());
            bookRepositoryNeo4j.save(bookNode);
        }

        return bookRepositoryNeo4j.findAll();
    }

    public Collection<UserNode> insertUsersBookTweeted(){
        UserNode userTweeted;
        for (BookNode book:bookRepositoryNeo4j.findAll()) {

            List<Map<String, Object>> users = tweetCortoDao.getUsersTweeted(book.getTitle());
            for (Map<String, Object> user:users) {
                System.out.println(user);
                userTweeted = userServiceNeo4j.findByScreenName(user.get("screenName").toString());
                userTweeted.tweetedBook(bookRepositoryNeo4j.findByTitle(book.getTitle()));
                userRepositoryNeo4j.save(userTweeted);
                //this.userServiceNeo4j.updateUser(userTweeted);
            }
        }
        return userServiceNeo4j.getAllUsers();

    }

/*
    private Map<String, Object> toD3Format(Collection<BookNode> bookNodes) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<BookNode> result = bookNodes.iterator();
        while (result.hasNext()) {
            BookNode bookNode = result.next();
            nodes.add(map("title", bookNode.getTitle(), "label", "book"));
            int target = i;
            i++;
            for (Tweeted tweeted: bookNode.getTweets()) {
                Map<String, Object> user = map("title", tweeted.getUserNode().getScreenName(), "label", "actor");
                int source = nodes.indexOf(user);
                if (source == -1) {
                    nodes.add(user);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    public Map<String, Object>  graphBookNode(int limit) {
        Collection<BookNode> result = bookRepositoryNeo4j.graphBook(limit);
        return toD3Format(result);
    }
*/
    public BookServiceNeo4j(BookRepositoryNeo4j bookRepositoryNeo4j){
        this.bookRepositoryNeo4j = bookRepositoryNeo4j;
    }

    public Collection<BookNode> findAll(){
        return this.bookRepositoryNeo4j.findAll();
    }

    public BookNode findByTitle(@Param("title") String title){
        return this.bookRepositoryNeo4j.findByTitle(title);
    }
}
