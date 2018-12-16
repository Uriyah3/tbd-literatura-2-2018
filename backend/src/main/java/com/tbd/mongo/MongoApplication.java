package com.tbd.mongo;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);

		Elastic es = new Elastic();

		List<String> words = new ArrayList<String>();
		words.add("amor");
		words.add("gabriel garcía márquez");
		JSONObject jsonObject;

		try {
			es.prepare( "127.0.0.1", 9300);
			//es.putTweets(8082);
			jsonObject=es.searchBooks(words);
			System.out.println(jsonObject.toJSONString());
		}
		catch (UnknownHostException e ) {
			e.printStackTrace();
		}

	}
}
