package com.lihd.elasticsearch.highlevel;


import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 20:35
 **/
public class ElasticSearchCreateIndex {


    public void createIndex() {

        RestHighLevelClient client = ESClient.getClient();

        CreateIndexRequest request = new CreateIndexRequest("lihd");

        request.mapping(
                "{\n" +
                        "\"properties\": {\n" +
                        "        \"name\": {\n" +
                        "          \"type\":   \"keyword\"\n" +
                        "        },\n" +
                        "        \"age\": {\n" +
                        "          \"type\":   \"long\"\n" +
                        "        },\n" +
                        "        \"score\": {\n" +
                        "          \"type\":   \"double\"\n" +
                        "        },\n" +
                        "        \"desc\": {\n" +
                        "          \"type\":   \"text\"\n" +
                        "        },\n" +
                        "        \"desc2\": {\n" +
                        "          \"type\":   \"text\",\n" +
                        "          \"fielddata\": true\n" +
                        "        },\n" +
                        "        \"birthday\": {\n" +
                        "          \"type\":   \"date\"\n" +
                        "        },\n" +
                        "        \"is_man\": {\n" +
                        "          \"type\":   \"boolean\"\n" +
                        "        }\n" +
                        "      }" +
                        "}",
                XContentType.JSON);


        request.setTimeout(TimeValue.timeValueMinutes(2));
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));
        try {
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println("All node acknowledged:" + response.isAcknowledged());
            System.out.println("Require node acknowledged:" + response.isShardsAcknowledged());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
