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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Elastic {


    TransportClient client = new PreBuiltTransportClient( Settings.EMPTY );

    public void putTweets(int port){
        try {
            String u = "http://localhost:" + port + "/tweets";
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


    public void prepare(String clusterIp, int clusterPort ) throws UnknownHostException {

        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(clusterIp), clusterPort));

    }


    public JSONObject searchBooks(List<String> text){
        JSONObject result = new JSONObject();
        ArrayList<Long> data = new ArrayList<Long>();
        ArrayList<String> label = new ArrayList<String>();
        for (String t:text) {
            SearchResponse searchResponse = client.prepareSearch("twitter")
                    .setQuery(QueryBuilders.matchQuery("text", t)).execute().actionGet();
            System.out.printf("Se encontraron %d coincidencias con la palabra %s\n",searchResponse.getHits().totalHits,t);
            System.out.println(searchResponse.toString());
            data.add(searchResponse.getHits().totalHits);
            label.add(t);
        }
        result.put("labels",label);
        result.put("data",data);
        return result;
    }


    public boolean bulkInsert(String indexName, String indexType, JSONObject tweet ) throws IOException {


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
