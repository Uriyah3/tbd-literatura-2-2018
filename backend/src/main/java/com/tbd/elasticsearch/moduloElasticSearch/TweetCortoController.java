package com.tbd.elasticsearch.moduloElasticSearch;

import com.tbd.elasticsearch.entities.*;
import com.tbd.elasticsearch.moduloSentimentAnalysis.SentimentAnalysisController;
import com.tbd.elasticsearch.rest.UserService;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import com.tbd.elasticsearch.moduloMongo.Tweet;
import com.tbd.elasticsearch.moduloMongo.TweetService;
import com.tbd.elasticsearch.rest.AuthorService;
import com.tbd.elasticsearch.rest.BookService;
import com.tbd.elasticsearch.rest.GenreService;


import java.time.LocalDate;
import java.util.*;
import java.text.*;

import java.time.OffsetDateTime;
import java.time.Instant;

@CrossOrigin(maxAge=3600)
@ComponentScan
@RestController
@EnableAutoConfiguration
@RequestMapping("/elasticsearch")
public class TweetCortoController {
	
	
    @Autowired
    private TweetCortoDao tweetCortoDao;

	@Autowired
	private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private GenreService genreService;
    
    @Autowired
    private TweetService tweetService;

	@Autowired
	private SentimentAnalysisController sentimentService;



    public TweetCortoController(TweetCortoDao tweetCortoDao) {
        this.tweetCortoDao = tweetCortoDao;
    }

    @GetMapping("/country/{country}")
	public Object popularityCountry(@PathVariable String country ){
    	return tweetCortoDao.getTweetsCountries(new Country(country));
    }


	//Se generan todos los datos
	@GetMapping("/country/book/{country}")
	public Object bookCountry(@PathVariable String country ){
		return tweetCortoDao.getBookCountries(new Country(country));
	}







    
    //Insertar datos de mongo en elastic
    @GetMapping("/insert")
	public void armar() throws ParseException {
		List<Tweet> listaTweets=tweetService.getTweets();
		for (Tweet tweet : listaTweets) {

			//Datos de nuevo tweet a elasticsearch
			TweetCorto tweetCorto=new TweetCorto();
			tweetCorto.setText(tweet.getText());
			tweetCorto.setText_lower(tweet.getText().toLowerCase());
			tweetCorto.setFavoriteCount(tweet.getFavoriteCount());
			tweetCorto.setRetweetCount(tweet.getRetweetCount());
			tweetCorto.setCreated_at(tweet.getCreated_at());

			//Conversion fecha
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			sdf.setTimeZone(TimeZone.getTimeZone("CET"));
			String isofechaString = sdf.format(tweet.getCreated_at());
			//System.out.println(isofechaString);

			//fecha string yyyy-MM-dd
			String fechaCorta=isofechaString.toString().replace('T',' ').substring(0,isofechaString.toString().length()-19);
			tweetCorto.setFecha(fechaCorta);
			//System.out.println(fechaCorta);

			//Fecha en date sql
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date parsed = format.parse(fechaCorta.replace("-",""));
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
			tweetCorto.setDate(sql);

			//Sentiment
			HashMap<String,Double> datos=sentimentService.classify(tweet.getText());
			Iterator<Double> lista=datos.values().iterator();

			Double negative=lista.next();
			Double positive=lista.next();

			tweetCorto.setNegative(negative);
			tweetCorto.setPositive(positive);

			//Usuario
			tweetCorto.setUser(tweet.getUser());

			//Id para elasticsearch
			Long idTweet=tweet.getId();
			String idStr= String.valueOf(idTweet);
			tweetCorto.setId(idStr);

			//Ver de que libro se hablo
			List<Book> listaLibros =(List) bookService.getAllFilms();
			tweetCorto.setBook_id(-1L);
			for (Book b:listaLibros) {

				if(tweetCorto.getText_lower().contains(b.getName().toLowerCase())){
					tweetCorto.setBook_id(b.getId());
				}
			}

			//Agregando el usuario a mysql
			List<User> listaUsuarios=(List)userService.getAllUser();
			Integer found=-1;
			for (User u:listaUsuarios) {
				if (u.getId()==tweetCorto.getUser().getId()){
					//userService.create(tweetCorto.getUser());
					found=1;
				}
			}
			if (found!=1){
				userService.create(tweetCorto.getUser());
			}


			tweetCortoDao.insertTweetCorto(tweetCorto);
		}
	  
	}
    
    //Obtener datos de mongo por libro
    @GetMapping("/hits")
	public Integer cantidadHits(Book book){
    	
    	return tweetCortoDao.getCantidadHits(book);
	  
	}
    
    
    //Actualizar la lista de todos los libros 
    @GetMapping("/updateBook")
    public void updateBook() {
    	List <Book> listaLibros=new ArrayList<Book>();
    	listaLibros=(List<Book>) bookService.getAllFilms();
    	
    	for (Book book : listaLibros) {
    		Integer hits=cantidadHits(book);
    		System.out.printf(book.getName() + ":" + "%d" + "\n" , hits);
  		  	System.out.println();
  		  	//
  		  	Book bookUpdated=new Book();
  		  	//Se setan los atributos
  		  	bookUpdated.setId(book.getId());
  		  	bookUpdated.setName(book.getName());
  		  	bookUpdated.setAuthorId(book.getAuthorId());
  		  	bookUpdated.setGenreId(book.getGenreId());
  		  	bookUpdated.setHits(hits);
  		  	//Se actualiza
  		  	bookService.create(bookUpdated);
  		  
    		
		}
    }
    
    
  //Actualizar un Author
    @GetMapping("/hitsAuthor")
    public Integer hitsAuthor(Author author) {
    	
    	List <Book> listaLibros=new ArrayList<Book>();
    	listaLibros=bookService.bookFromAuthor(author.getId());
    	
    	Integer counter=0;
    	for (Book book : listaLibros) {
    		counter=counter+book.getHits();
		}

		//Se actualiza el author
		Author authorUpdated=new Author();
		//Se setan los atributos
		authorUpdated.setId(author.getId());
		authorUpdated.setName(author.getName());
		authorUpdated.setGenreId(author.getGenreId());
		authorUpdated.setHits(counter);
		//Se actualiza
		authorService.create(authorUpdated);
		return counter;
    }
    
    //Actualizar todos los autores 
    @GetMapping("/updateAuthor")
    public void updateAuthor() {
    	
    	//Sacar todos los autores
    	List <Author> listaAutores=new ArrayList<Author>();
    	listaAutores=(List<Author>) authorService.getAllFilms();
    	
    	for (Author author : listaAutores) {
    		//Se actualizan todos los autores
    		hitsAuthor(author);
		}
    	
    }
    
    
    //Calcular la cantidad de tweets de un genero 
    @GetMapping("/hitsGenre")
    public Integer hitsGenre(Genre genre) {
    	
    	//Autores segun el genero
    	List <Author> listaAutores=new ArrayList<Author>();
		listaAutores = authorService.findAuthorFromGenre(genre.getId());


    	Integer counter=0;
    	for (Author author : listaAutores) {
    		counter=counter+hitsAuthor(author);
    	
		}
    	
    	//Se actualiza en genero
    	Genre updatedGenre=new Genre();
    	updatedGenre.setId(genre.getId());
    	updatedGenre.setName(genre.getName());
    	updatedGenre.setHits(counter);
		genreService.create(updatedGenre);

    	return counter;
    }
    
    
    //Actualizar todos los Generos
    @GetMapping("/updateGenre")
    public void updateGenre() {

    	//Todos los generos
    	List <Genre> listaGeneros=new ArrayList<Genre>();
    	listaGeneros=(List<Genre>) genreService.getAllFilms();

    	for (Genre genre : listaGeneros) {
    		hitsGenre(genre);
    	
		}
    	
    }
    	
    //Actualiza todas las entidades
    @GetMapping("/updateAll")
	public void updateAll(){
	  updateBook();
	  updateAuthor();
	  updateGenre();
	}






    
    //Metodos para crear JSON
    /*public JSONObject top10libros() {
    	
    	JSONObject result = new JSONObject();
        ArrayList<Long> data = new ArrayList<Long>();
        ArrayList<String> label = new ArrayList<String>();
        
        //data.add(searchResponse.getHits().totalHits);
        //label.add(book);
        
        result.put("labels", label);
        result.put("data", data);
        return result;
    }
    */


	@GetMapping("/updateBookSentiment")
	public void updateBookSentiment(){

		List <Book> listBook= (List) bookService.getAllFilms();

		for (Book b:listBook) {
			List<Integer> data= tweetCortoDao.getBookSentimentList(b);
				b.setPositivo(data.get(0));
				b.setNegativo(data.get(1));
				b.setNeutro(data.get(2));
				bookService.create(b);
		}

	}

	@GetMapping("/updateAuthorSentiment")
	public void updateAuthorSentiment(){

		//Todos los autores
		List<Author> listAuthor=(List) authorService.getAllFilms();

		for (Author a: listAuthor){

			//Sacar los libros por autores
			List<Book> listBook=(List) bookService.bookFromAuthor(a.getId());
			Integer countPositive=0;
			Integer countNegative=0;
			Integer countNeutral=0;

			for (Book b:listBook){
				List<Integer> data= tweetCortoDao.getBookSentimentList(b);
				countPositive=countPositive+data.get(0);
				countNegative=countNegative+data.get(1);
				countNeutral=countNeutral+data.get(2);
			}

			a.setPositivo(countPositive);
			a.setNegativo(countNegative);
			a.setNeutro(countNeutral);

			authorService.create(a);

		}

	}

	@GetMapping("/updateGenreSentiment")
	public void updateGenreSentiment(){

		//Todos los generos
		List<Genre> listGenre=(List) genreService.getAllFilms();

		for (Genre g: listGenre){

			//Sacar los autores por autor
			List<Author> listAuthor=(List) authorService.findAuthorFromGenre(g.getId());
			Integer countPositive=0;
			Integer countNegative=0;
			Integer countNeutral=0;

			for (Author a:listAuthor){

				countPositive=countPositive+a.getPositivo();
				countNegative=countNegative+a.getNegativo();
				countNeutral=countNeutral+a.getNeutro();
			}

			g.setPositivo(countPositive);
			g.setNegativo(countNegative);
			g.setNeutro(countNeutral);

			genreService.create(g);

		}

	}


	@GetMapping("/updateAllSentiment")
	public void updateAllSentiment(){

		updateBookSentiment();
		updateAuthorSentiment();
		updateGenreSentiment();
	}


    //Metodo para calcular segun analisis de sentimientos segun texto
	@GetMapping("/getBookFeelByName/{text}")
	public Map<String, ArrayList> getBookFeelText(@PathVariable String text){

		Book book=new Book();
		book.setName(text);


		return tweetCortoDao.getBookSentiment(book);
	}

	//Sentimiento de libro segun id
	@GetMapping("/getBookFeelById/{id}")
	public Map<String, ArrayList> getBookFeelId(@PathVariable Long id){

		Optional<Book> book=bookService.findOne(id);

		return tweetCortoDao.getBookSentiment(book.get());
	}




	//Sentimiento autor segun sus libros (por texto)
	@GetMapping("/getAuthorFeelByName/{name}")
	public Map<String, ArrayList> getAuthorFeelName(@PathVariable String name){


		//Todos los libros del autor
		Author autor=authorService.findOneByName(name);
		List<Book> listaLibros=bookService.BookAuthor(autor.getId());


		Integer contadorPositivo=0;
		Integer contadorNegativo=0;
		Integer contadorNeutro=0;
		for (Book b:listaLibros){
			Map<String, ArrayList> dataElastic=tweetCortoDao.getBookSentiment(b);

			ArrayList<Integer> dataNum= dataElastic.get("data");
			contadorPositivo=contadorPositivo+dataNum.get(0);
			contadorNegativo=contadorNegativo+dataNum.get(1);
			contadorNeutro=contadorNeutro+dataNum.get(2);
		}

		//Armar el json
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


	//Sentimiento autor segun sus libros (por id)
	@GetMapping("/getAuthorFeelById/{id}")
	public Map<String, ArrayList> getAuthorFeelId(@PathVariable Long id){

		List<Book> listaLibros=bookService.BookAuthor(id);


		Integer contadorPositivo=0;
		Integer contadorNegativo=0;
		Integer contadorNeutro=0;
		for (Book b:listaLibros){
			Map<String, ArrayList> dataElastic=tweetCortoDao.getBookSentiment(b);

			ArrayList<Integer> dataNum= dataElastic.get("data");
			contadorPositivo=contadorPositivo+dataNum.get(0);
			contadorNegativo=contadorNegativo+dataNum.get(1);
			contadorNeutro=contadorNeutro+dataNum.get(2);
		}

		//Armar el json
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


	//Sentimiento genero segun autores
	@GetMapping("/getGenreFeelByName/{name}")
	public Map<String, ArrayList> getGenreFeelName(@PathVariable String name){

		//Todos los autores segun genero
		Genre genero=genreService.genreByName(name);
		List<Author> listaAuthor=authorService.findAuthorFromGenre(genero.getId());


		Integer contadorPositivo=0;
		Integer contadorNegativo=0;
		Integer contadorNeutro=0;
		for (Author a:listaAuthor){
			Map<String, ArrayList> dataElastic=getAuthorFeelId(a.getId());

			ArrayList<Integer> dataNum= dataElastic.get("data");
			contadorPositivo=contadorPositivo+dataNum.get(0);
			contadorNegativo=contadorNegativo+dataNum.get(1);
			contadorNeutro=contadorNeutro+dataNum.get(2);
		}

		//Armar el json
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

	//Sentimiento genero segun autores
	@GetMapping("/getGenreFeelById/{id}")
	public Map<String, ArrayList> getGenreFeelName(@PathVariable Long id){

		//Todos los autores segun genero
		List<Author> listaAuthor=authorService.findAuthorFromGenre(id);

		Integer contadorPositivo=0;
		Integer contadorNegativo=0;
		Integer contadorNeutro=0;
		for (Author a:listaAuthor){
			Map<String, ArrayList> dataElastic=getAuthorFeelId(a.getId());

			ArrayList<Integer> dataNum= dataElastic.get("data");
			contadorPositivo=contadorPositivo+dataNum.get(0);
			contadorNegativo=contadorNegativo+dataNum.get(1);
			contadorNeutro=contadorNeutro+dataNum.get(2);
		}

		//Armar el json
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

	//Para calcular la cantidad de hits por fecha
	@GetMapping("/getDate")
	public Map<String, ArrayList> getDateTweets() throws ParseException {

		//Desde el 21 de diciembre hasta hoy
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date parsedI = format.parse("20181221");
		java.sql.Date fecha = new java.sql.Date(parsedI.getTime());

		java.sql.Date hoy=new java.sql.Date(Calendar.getInstance().getTime().getTime());
		System.out.println(hoy);

		//Datos para salida
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<String> label = new ArrayList<String>();
		ArrayList<Integer> data = new ArrayList<Integer>();



		Integer i=0;
		Integer hits=0;
		while(!fecha.after(hoy)) {

			label.add(fecha.toString());
			hits=tweetCortoDao.getDataDate(fecha.toString());
			data.add(hits);
			System.out.println(hits);


			LocalDate localdate=fecha.toLocalDate();
			String sig=localdate.plusDays(1).toString();

			Date parsed = format.parse(fecha.toString());
			fecha = new java.sql.Date(parsed.getTime());

			//Sig
			Date parsed2 = format.parse(sig.replace("-",""));
			fecha= new java.sql.Date(parsed2.getTime());

		}

		result.put("labels", label);
		result.put("data", data);


		return result;

	}


	@GetMapping("/setUserScore")
	public void getUserHits(){

		//System.out.println("--------------Entra-----------------");
		List<User> listaUsuarios=(List)userService.getAllUser();
		Double score=0.0;
		Double followers=0.0;
		Double friends=0.0;
		Double hits=0.0;
		for (User u:listaUsuarios) {

			followers=u.getFollowersCount()*1.0;
			friends=u.getFriendsCount()*1.0;
			hits=tweetCortoDao.getUserHits(u.getId())*1.0;


			if ( followers!=0.0 ){
				score=Math.log(followers);
			}
			if (friends!=0.0){
				score=score+Math.log(friends)*0.1;
			}
			if (hits!=0.0){
				score=score+Math.log(hits)*2.0;
			}

			//System.out.println(score);

			u.setScore(score);
			userService.create(u);
			followers=0.0;
			friends=0.0;
			hits=0.0;
			score=0.0;
		}

	}



	@GetMapping("/getAll")
	public void getAll(){
		tweetCortoDao.getAll();
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
