package com.lihd.elasticsearch.highlevel;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 20:39
 **/
public class ElasticSearchInsert {

    public void insert(RestHighLevelClient client) throws IOException {


        IndexRequest indexRequest = new IndexRequest("lihd");

        indexRequest.id("1");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "张三");
        jsonMap.put("age", 20);
        jsonMap.put("birthday", new Date());
        jsonMap.put("is_man", true);
        jsonMap.put("score", 70.5 + 1000 / 100.0);
        jsonMap.put("desc", "张三的测试描述一" + 1000);
        jsonMap.put("desc2", "张三的测试描述二" + 1000);

        indexRequest.source(jsonMap, XContentType.JSON);
        client.index(indexRequest, RequestOptions.DEFAULT);

    }


    void batchInsert(RestHighLevelClient client) {
        BulkRequest request = new BulkRequest();
        // 插入100条数据
        for (int i = 0; i < 100; i++) {
            Map<String, Object> jsonMap = new HashMap<>();
            String id = String.valueOf(10000 + i);
            jsonMap.put("name", "测试" + id);
            jsonMap.put("age", 20);
            jsonMap.put("birthday", new Date());
            jsonMap.put("is_man", true);
            jsonMap.put("score", 70.5 + i / 100.0);
            jsonMap.put("desc", "张三的测试描述一" + id);
            jsonMap.put("desc2", "张三的测试描述二" + id);
            request.add(new IndexRequest("lihd").source(jsonMap).id(id));
        }
        try {
            BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
            System.out.println("批量处理：" + bulkResponse.status().getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
