package com.tbd.mongo;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
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
import java.util.List;

public class Elastic {


    TransportClient client = new PreBuiltTransportClient( Settings.EMPTY );
    RestHighLevelClient restHighLevelClient;
    private TweetService tweetService = new TweetService();

    public void getTweets(int port){
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


    public void prepare(String clusterName, String clusterIp, int clusterPort ) throws UnknownHostException {

        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.ignore_cluster_name", true)
                .put("client.transport.sniff", true)
                .build();

        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(clusterIp), clusterPort));
        getTweets(8082);
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
