package com.tbd.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
public class TweetService {
	@Autowired
	TweetRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Tweet> getTweets(){
		return repository.findAll();		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<Tweet> getTweetsById(@PathVariable("id") Long id){
	
		return repository.findById(id);		
	}
	
	
}
