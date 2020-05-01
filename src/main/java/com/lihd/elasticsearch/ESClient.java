package com.lihd.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 描述信息
 *
 * @author hsl
 * @date 2020/1/17 17:01
 */
public class ESClient {



    public static RestHighLevelClient getClient(){
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("192.168.160.130", 9200, "http"));
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(600000);
            requestConfigBuilder.setSocketTimeout(300000);
            requestConfigBuilder.setConnectionRequestTimeout(600000);
            return requestConfigBuilder;
        });
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(30);
            httpClientBuilder.setMaxConnPerRoute(10);
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }

}
