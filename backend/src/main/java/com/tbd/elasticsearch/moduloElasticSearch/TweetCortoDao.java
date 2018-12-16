package com.tbd.elasticsearch.moduloElasticSearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;




@Repository
public class TweetCortoDao {
	private final String INDEX = "twitter";
	private final String TYPE = "data";
	private RestHighLevelClient restHighLevelClient;
	private ObjectMapper objectMapper;

	public TweetCortoDao(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.restHighLevelClient = restHighLevelClient;
	}

	public TweetCorto insertTweetCorto(TweetCorto tweet) {
		//tweet.setId(UUID.randomUUID().toString());
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, tweet.getId()).source(objectMapper.convertValue(tweet, Map.class));
		try {
			restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return tweet;
	}
	
	//Obtener todos los tweets
	public List<TweetCorto> getTweetsCortos(String texto) {
		
		List<TweetCorto> tweetLista = new ArrayList<TweetCorto>();
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, texto).source(objectMapper.convertValue(tweetLista, Map.class));
		try {
			restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return tweetLista;
	}
	
	
	//Version de Alvaro
	/*
	 @GetMapping(value = "/tweets")
	    @CrossOrigin(origins = "*")
	    public JSONObject searchBooks() {
	        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
	        try{
	            client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
	        }catch(UnknownHostException e){
	            e.printStackTrace();
	        }

	        BufferedReader bufferedReader = null;
	        FileReader fileReader = null;
	        JSONObject result = new JSONObject();
	        ArrayList<Long> data = new ArrayList<Long>();
	        ArrayList<String> label = new ArrayList<String>();
	        try {
	            File file = ResourceUtils.getFile("classpath:bagofwords.txt");
	            bufferedReader = new BufferedReader(new FileReader(file));
	            String book;

	            while ((book = bufferedReader.readLine()) != null) {
	                SearchResponse searchResponse = client.prepareSearch("twitter")
	                        .setQuery(QueryBuilders.matchPhraseQuery("text", book)).execute().actionGet();
	                System.out.printf("Se encontraron %d coincidencias con la palabra %s\n", searchResponse.getHits().totalHits, book);
	                System.out.println(searchResponse.toString());
	                data.add(searchResponse.getHits().totalHits);
	                label.add(book);
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        result.put("labels", label);
	        result.put("data", data);
	        return result;
	    }
	    */

    

	public Map<String, Object> getTweetCortoById(String id) {
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse getResponse = null;
		try {
			getResponse = restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
		return sourceAsMap;
	}

	public Map<String, Object> updateTweetCortoById(String id, TweetCorto tweet) {
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id).fetchSource(true);
		Map<String, Object> error = new HashMap<>();
		error.put("Error", "Unable to update book");
		try {
			String bookJson = objectMapper.writeValueAsString(tweet);
			updateRequest.doc(bookJson, XContentType.JSON);
			UpdateResponse updateResponse = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
			Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
			return sourceAsMap;
		} catch (JsonProcessingException e) {
			e.getMessage();
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return error;
	}

	public void deleteTweetCortoById(String id) {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		try {
			restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
	}

}
