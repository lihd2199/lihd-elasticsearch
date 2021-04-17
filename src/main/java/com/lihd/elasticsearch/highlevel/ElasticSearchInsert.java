package com.lihd.elasticsearch.highlevel;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service
public class ElasticSearchInsert {

    @Resource(name = "restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;

    public void insert(String index) throws IOException {

        IndexRequest indexRequest = new IndexRequest(index).type("_doc");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "张三");
        jsonMap.put("age", 20);
        jsonMap.put("birthday", new Date());
        jsonMap.put("is_man", true);
        jsonMap.put("score", 70.5 + 1000 / 100.0);
        jsonMap.put("desc", "张三的测试描述一" + 1000);
        jsonMap.put("desc2", "张三的测试描述二" + 1000);

        indexRequest.source(jsonMap, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

    }


    public void batchInsert(String index) {
        BulkRequest request = new BulkRequest();
        // 插入100条数据
        for (int i = 0; i < 20; i++) {
            Map<String, Object> jsonMap = new HashMap<>();
            String id = String.valueOf(30000 + i);
            jsonMap.put("name", "弗雷尔卓德" + id);
            jsonMap.put("age", 20);
            jsonMap.put("birthday", new Date());
            jsonMap.put("is_man", true);
            jsonMap.put("score", 70.5 + i / 100.0);
            jsonMap.put("desc", "李四的测试描述一" + id);
            jsonMap.put("desc2", "李四的测试描述二" + id);
            request.add(new IndexRequest(index).type("_doc").source(jsonMap));
        }
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            System.out.println("批量处理：" + bulkResponse.status().getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
