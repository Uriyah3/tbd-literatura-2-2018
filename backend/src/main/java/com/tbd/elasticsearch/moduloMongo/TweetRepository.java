package com.tbd.elasticsearch.moduloMongo;


import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface TweetRepository extends MongoRepository<Tweet,Long>{

	//public Tweet findById(String id);
}
