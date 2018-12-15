package com.tbd.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.UnknownHostException;

@SpringBootApplication
public class MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);

		Elastic es = new Elastic();
		try {
			es.prepare("elasticsearch", "127.0.0.1", 9300);
		}
		catch (UnknownHostException e ) {
			e.printStackTrace();
		}
		/*try {
			es.bulkInsert("twitter", "data");
		}
		catch (IOException e ) {
			e.printStackTrace();
		}*/

	}
}
