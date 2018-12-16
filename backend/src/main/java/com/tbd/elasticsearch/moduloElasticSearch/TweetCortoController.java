package com.tbd.elasticsearch.moduloElasticSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.moduloMongo.Tweet;
import com.tbd.elasticsearch.moduloMongo.TweetService;
import com.tbd.elasticsearch.rest.BookService;

import java.util.List;
import java.util.Map;

@ComponentScan
@RestController
@EnableAutoConfiguration
@RequestMapping("/elasticsearch")
public class TweetCortoController {
	
	
    @Autowired
    private TweetCortoDao tweetCortoDao;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private TweetService tweetService;

    public TweetCortoController(TweetCortoDao tweetCortoDao) {
        this.tweetCortoDao = tweetCortoDao;
    }

    
    
    //Insertar datos de mongo en elastic
    @GetMapping("/insert")
	public void armar(){
		List<Tweet> listaTweets=tweetService.getTweets();
		for (Tweet tweet : listaTweets) {
			TweetCorto tweetCorto=new TweetCorto();
			tweetCorto.setText(tweet.getText());
			Long idTweet=tweet.getId();
			String idStr= String.valueOf(idTweet);
			tweetCorto.setId(idStr);
			tweetCortoDao.insertTweetCorto(tweetCorto);
		}
	  
	}
    
    //Obtener datos de mongo
    @GetMapping("/datos")
	public List<TweetCorto> datos(){
    	
    	return tweetCortoDao.getTweetsCortos("navidad");
	  
	}
    
  
	@GetMapping("/{id}")
	public Map<String, Object> getBookById(@PathVariable String id){
	  return tweetCortoDao.getTweetCortoById(id);
	}
	
	
	
	@PostMapping
	public TweetCorto insertBook(@RequestBody TweetCorto tweet) throws Exception {
	  return tweetCortoDao.insertTweetCorto(tweet);
	}
	
	@PutMapping("/{id}")
	public Map<String, Object> updateBookById(@RequestBody TweetCorto tweet, @PathVariable String id) {
	  return tweetCortoDao.updateTweetCortoById(id, tweet);
	}
	
	@DeleteMapping("/{id}")
	public void deleteBookById(@PathVariable String id) {
		tweetCortoDao.deleteTweetCortoById(id);
	}

}
