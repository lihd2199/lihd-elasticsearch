package com.lihd.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 21:32
 **/
public class ElasticSearchInsertTest {


    @Test
    public void test() throws IOException {

        RestHighLevelClient client = ESClient.getClient();

        ElasticSearchInsert elasticSearchInsert = new ElasticSearchInsert();

        elasticSearchInsert.insert(client);

//        elasticSearchInsert.batchInsert(client);

        client.close();
    }


}
