package com.tbd.mongo;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;


@RestController
@RequestMapping("/elastic")
public class ElasticService {

    @PostMapping("/tweet/insert/{port}")
    @CrossOrigin(origins = "*")
    public void putTweets(@PathVariable(value = "port")int port){
        try {
            String u = "http://localhost:"+port+"/tweets";
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JSONParser parser =new JSONParser();
            Object obj = parser.parse(br.readLine());
            org.json.simple.JSONArray array = (org.json.simple.JSONArray) obj;

            for (Object j:array) {
                System.out.println(j.toString());
                bulkInsert("twitter","data",(JSONObject) j);
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "/tweets")
    @CrossOrigin(origins = "*")
    public JSONObject searchBooks() {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        try{
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
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


     public boolean bulkInsert(String indexName, String indexType, JSONObject tweet ) throws IOException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        try{
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).add(
                    client.prepareIndex(indexName, indexType, null).setSource(tweet));
        BulkResponse bulkResponse = bulkRequest.get();
        if ( bulkResponse.hasFailures() ) {
            System.out.println( "Bulk insert failed!" );
            return false;
        }
        return true;
     }
}
