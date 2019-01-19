package com.tbd.elasticsearch.moduloElasticSearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.entities.Author;
import com.tbd.elasticsearch.entities.Country;
import com.tbd.elasticsearch.rest.AuthorService;
import com.tbd.elasticsearch.rest.BookService;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;



@Repository
public class TweetCortoDao {
	private final String INDEX = "twitter";
	private final String TYPE = "data";
	private RestHighLevelClient restHighLevelClient;
	private ObjectMapper objectMapper;

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;




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


	//Hits por pais
	public Map<String, Integer> getTweetsCountries(Country country){

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchQuery("user.location",country.getName()));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse=null;
		Map<String, Integer> result = new HashMap<>();
		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		result.put(country.getName(), (int)searchResponse.getHits().getTotalHits());


		return result;

	}


	//Libro top pais
	public Map<String, Integer> getBookCountries(Country country){

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchQuery("user.location",country.getName()));
		sourceBuilder.size(10000);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse=null;
		Map<String, Integer> result = new HashMap<>();
		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		result.put(country.getName(), (int)searchResponse.getHits().getTotalHits());


		SearchHits hits = searchResponse.getHits();
		SearchHit[] allGits=hits.getHits();

		List <Long> idLibros=new ArrayList<>();
		List <Integer> score=new ArrayList<>();

		for (SearchHit hit : allGits) {
			Map<String, Object> sourceMap = hit.getSourceAsMap();
			Integer book_id=(Integer) sourceMap.get("book_id");
			Long book_idL=book_id.longValue();

			//System.out.println("--------------------------");
			//System.out.println(book_id);

			if (book_id!=-1){

				Integer index=idLibros.indexOf(book_idL);
				//System.out.println(".........");
				//System.out.println(index);

				if (index==-1){
					idLibros.add(book_idL);
					score.add(1);
				}
				else if(index!=-1){
					score.set(index,score.get(index)+1);

				}

			}

		}

		System.out.println(idLibros);
		System.out.println(score);

		//Libro top pais
		Integer indiceLibroTop=score.indexOf(Collections.max(score));
		Long idLibroTop=idLibros.get(indiceLibroTop);

		System.out.println("Libro mas top: " + idLibroTop);
		System.out.println("Total de hits: " + Collections.max(score));
		//--------------------------------------------------------


		//El autor mas top
		List <Long> idAutores=new ArrayList<>();
		List <Integer> scoreAutores=new ArrayList<>();

		for (Long idbook :idLibros ){

			//Autor de libro
			Optional<Book> b=bookService.findOne(idbook);
			Long idAutor=b.get().getAuthorId();
			//System.out.println("id Autor: " + idAutor);

			Integer indiceAutor=idAutores.indexOf(idAutor);
			//Integer indiceScoreLibro=;

			if (indiceAutor==-1){
				idAutores.add(idAutor);
				//Mas libros comentados o comentarios en general
				//Score de este libro
				Integer indiceLibro=idLibros.indexOf(idbook);
				Integer scoreLibroAutor=score.get(indiceLibro);
				scoreAutores.add(scoreLibroAutor);
			}
			else if(indiceAutor!=-1){
				Integer indiceLibro=idLibros.indexOf(idbook);
				Integer scoreLibroAutor=score.get(indiceLibro);
				scoreAutores.set(indiceAutor,scoreAutores.get(indiceAutor)+scoreLibroAutor);
			}


		}

		System.out.println( idAutores);
		System.out.println(scoreAutores);

		//Autor top pais
		Integer indiceAutorTop=scoreAutores.indexOf(Collections.max(scoreAutores));
		Long idAutorTop=idAutores.get(indiceAutorTop);

		System.out.println("Autor mas top: " + idAutorTop);
		System.out.println("Total de hits: " + Collections.max(scoreAutores));
		//--------------------------------------------------------




		//Genero mas popular
		List<Long> idGeneros=new ArrayList<>();
		List<Integer> scoreGeneros=new ArrayList<>();


		for (Long idautor :idAutores ){

			//Genero de autor
			Optional<Author> a=authorService.findOne(idautor);
			Long idGenre=a.get().getGenreId();
			//System.out.println("id Genre: " + idGenre);

			Integer indiceGenero=idGeneros.indexOf(idGenre);


			if (indiceGenero==-1){
				idGeneros.add(idGenre);
				//Mas autores comentados o comentarios en general
				//Score de este autor
				Integer indiceAutorG=idAutores.indexOf(idautor);
				Integer scoreAutorGenero=score.get(indiceAutorG);
				scoreGeneros.add(scoreAutorGenero);
			}
			else if(indiceGenero!=-1){
				Integer indiceAutorG=idAutores.indexOf(idautor);
				Integer scoreAutorGenero=score.get(indiceAutorG);
				scoreGeneros.set(indiceGenero,scoreGeneros.get(indiceGenero)+scoreAutorGenero);
			}


		}

		System.out.println(idGeneros);
		System.out.println(scoreGeneros);

		//Genero top pais
		Integer indiceGeneroTop=scoreGeneros.indexOf(Collections.max(scoreGeneros));
		Long idGeneroTop=idGeneros.get(indiceGeneroTop);

		System.out.println("Genero mas top: " + idGeneroTop);
		System.out.println("Total de hits: " + Collections.max(scoreGeneros));
		//--------------------------------------------------------


		return result;

	}





	public List<Map<String, Object>> getUsersTweeted(String book) {

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchPhraseQuery("text_lower",book));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse=null;
		List<Map<String, Object>> result = new ArrayList<>();

		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		if (searchResponse.getHits().totalHits>0)
			for (SearchHit hits:searchResponse.getHits()) {

				Map<String, Object> hit = hits.getSourceAsMap();
				Map<String,Object> user = (Map<String,Object>) hit.get("user");
				result.add(user);
			}
		return result;
	}
	
	//Entrega cantidad de hits segun en libro a buscar
	public Integer getCantidadHits(Book book){
	
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


		//Nueva
		sourceBuilder.query(QueryBuilders.matchPhraseQuery("text_lower",book.getName().toLowerCase()));

		//Vieja
		//sourceBuilder.query(QueryBuilders.simpleQueryStringQuery(book.getName()));

		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse=null;
		  try {
			  searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
			   
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
		  SearchHits hits = searchResponse.getHits();
		  
		  //System.out.printf("Cantida de Hits: %d" , searchResponse.getHits().totalHits );
		  //System.out.println("------");
		  Integer nHits=(int) searchResponse.getHits().totalHits;
		  Map<String, ProfileShardResult> sourceAsMap = searchResponse.getProfileResults();
				   
		  return nHits;
		}



	public Map<String, ArrayList> getBookSentiment(Book book){

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


		//Nueva
		sourceBuilder.query(QueryBuilders.matchPhraseQuery("text_lower",book.getName().toLowerCase()));
		sourceBuilder.size(20000);
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse=null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		SearchHits hits = searchResponse.getHits();
		//System.out.printf("Cantida de Hits: %d" , searchResponse.getHits().totalHits );
		//System.out.println("------");
		Integer nHits=(int) searchResponse.getHits().totalHits;
		Map<String, ProfileShardResult> sourceAsMap = searchResponse.getProfileResults();

		//Sentimientos

		//Positivo-Negativo-Neutro
		Integer contadorPositivo=0;
		Integer contadorNegativo=0;
		Integer contadorNeutro=0;

		SearchHit[] allGits=hits.getHits();

		for (SearchHit hit : allGits) {
			Map<String, Object> sourceMap = hit.getSourceAsMap();
			Double positive=(Double) sourceMap.get("positive");
			Double negative=(Double) sourceMap.get("negative");
			System.out.println("--------------------------");
			System.out.println(positive);
			System.out.println(negative);

			if(positive>0.650){
				contadorPositivo++;
			}
			else if(negative>0.50){
				contadorNegativo++;
			}
			else{
				contadorNeutro++;
			}



		}



		//Se arma el Map
		HashMap<String, ArrayList> result = new HashMap<>();

		ArrayList<String> label = new ArrayList<String>();
		label.add("Positivo");
		label.add("Negativo");
		label.add("Neutro");

		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(contadorPositivo);
		data.add(contadorNegativo);
		data.add(contadorNeutro);

		result.put("labels", label);
		result.put("data", data);





		return result;
	}

	//Lista de libros por sentimientos
	public List<Integer> getBookSentimentList(Book book){

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		//Nueva

		sourceBuilder.query(QueryBuilders.matchPhraseQuery("text_lower",book.getName().toLowerCase()) );
		sourceBuilder.size(10000);
		//Donde positive sea mayor a 0.65
		//sourceBuilder.query(QueryBuilders.rangeQuery("positive").gte(0.65));

		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse=null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		SearchHits hits = searchResponse.getHits();
		//System.out.printf("Cantida de Hits: %d" , searchResponse.getHits().totalHits );
		//System.out.println("------");

		Integer nHits=(int) searchResponse.getHits().totalHits;
		Map<String, ProfileShardResult> sourceAsMap = searchResponse.getProfileResults();



		//Sentimientos

		//Positivo-Negativo-Neutro
		Integer contadorPositivo=0;
		Integer contadorNegativo=0;
		Integer contadorNeutro=0;

		SearchHit[] allGits=hits.getHits();
		if (nHits>0) {
			System.out.println("-----------------------");
			System.out.println(nHits);
			System.out.println(allGits.length);
		}

		for (SearchHit hit : allGits) {
			Map<String, Object> sourceMap = hit.getSourceAsMap();
			Double positive=(Double) sourceMap.get("positive");
			Double negative=(Double) sourceMap.get("negative");
			if(positive>0.650){
				contadorPositivo++;
			}
			else if(negative>0.50){
				contadorNegativo++;
			}
			else{
				contadorNeutro++;
			}



		}



		//Se arma el Map
		HashMap<String, ArrayList> result = new HashMap<>();

		ArrayList<String> label = new ArrayList<String>();
		label.add("Positivo");
		label.add("Negativo");
		label.add("Neutro");

		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(contadorPositivo);
		data.add(contadorNegativo);
		data.add(contadorNeutro);

		result.put("labels", label);
		result.put("data", data);





		return data;
	}


	//
	public Integer getDataDate(String fecha){

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


		//Nueva
		sourceBuilder.query(QueryBuilders.matchPhraseQuery("fecha",fecha));
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse=null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		SearchHits hits = searchResponse.getHits();
		Integer nHits=(int) searchResponse.getHits().totalHits;
		Map<String, ProfileShardResult> sourceAsMap = searchResponse.getProfileResults();



		return nHits;
	}


	public Integer getAll(){

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


		//Nueva
		sourceBuilder.query(QueryBuilders.matchPhraseQuery("_type","data"));
		//sourceBuilder.query(QueryBuilders.matchAllQuery());
		//sourceBuilder.size(100);
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse=null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		SearchHits hits = searchResponse.getHits();
		Integer nHits=(int) searchResponse.getHits().totalHits;
		Map<String, ProfileShardResult> sourceAsMap = searchResponse.getProfileResults();

		System.out.println("["+nHits+"]");

		List<Integer> listaNum=new ArrayList<>();

		SearchHit[] allGits=hits.getHits();
		System.out.println(hits.getHits().length);
		Integer counterDate=0;
		for (SearchHit hit : hits.getHits()) {

			counterDate++;
			Map<String, Object> sourceMap = hit.getSourceAsMap();
			String fecha=(String) sourceMap.get("created_at");
			String text=(String) sourceMap.get("text");
			System.out.println(text);
			String fechaCorta=fecha.replace('T',' ').substring(0,fecha.toString().length()-18);
			Integer fechaint= Integer.parseInt(fechaCorta.replace("-",""));

			//Ver si la fecha esta en la lista de fechas
			if (!listaNum.contains(fechaint)){
				listaNum.add(fechaint);
			}

			System.out.println("--------------------------");
			System.out.println(fechaCorta);


			System.out.println(counterDate);
		}
		System.out.println(listaNum);
		System.out.println(counterDate);

		return nHits;
	}


	public Integer getUserHits(Long id){

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


		//Nueva
		sourceBuilder.query(QueryBuilders.matchPhraseQuery("user.id",id));
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse=null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

		} catch (java.io.IOException e){
			e.getLocalizedMessage();
		}
		SearchHits hits = searchResponse.getHits();
		Integer nHits=(int) searchResponse.getHits().totalHits;
		Map<String, ProfileShardResult> sourceAsMap = searchResponse.getProfileResults();



		return nHits;
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
