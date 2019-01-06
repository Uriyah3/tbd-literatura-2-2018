package com.tbd.elasticsearch.moduloNeo4j;


import com.tbd.elasticsearch.entities.Author;
import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.moduloElasticSearch.TweetCortoDao;
import com.tbd.elasticsearch.repository.AuthorRepository;
import com.tbd.elasticsearch.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceNeo4j {

    @Autowired
    private UserServiceNeo4j userServiceNeo4j;
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

        /*
        Agregar relaciones
        UserNode test;
        List<BookNode> booksNodes = (List<BookNode>) bookRepositoryNeo4j.findAll();
        for (int i = 0; i < 25 ; i++) {

            test = new UserNode("Usuario Prueba "+i,10,5);
            test.tweeted(booksNodes.get(0));
            test.tweeted(booksNodes.get(i+1));
            System.out.println(this.userServiceNeo4j.insertUser(test).getName());
        }*/


        return bookRepositoryNeo4j.findAll();
    }

    public List<Map<String, Object>> insertUsersTweeted(String book){
        List<Map<String, Object>> tweetsCorto = tweetCortoDao.getUsersTweeted(book);
        UserNode userTweeted;
        for (Map<String, Object> user:tweetsCorto) {
            System.out.println(user);
            userTweeted = userServiceNeo4j.findByName(user.get("name").toString());//= new UserNode( new Long(user.get("id").toString()),user.get("name").toString(),user.get("screenName").toString(),(Integer) user.get("followersCount"),(Integer) user.get("friendsCount"),user.get("location").toString(), (Boolean) user.get("isVerified"),(Integer) user.get("score"));
            userTweeted.tweeted(bookRepositoryNeo4j.findByTitle(book));
            this.userServiceNeo4j.updateUser(userTweeted);
        }
        return tweetsCorto;

    }


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
