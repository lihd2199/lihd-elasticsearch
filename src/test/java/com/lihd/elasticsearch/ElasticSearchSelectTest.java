package com.lihd.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 21:33
 **/
public class ElasticSearchSelectTest {


    @Test
    public void test() throws IOException {

        ElasticSearchSelect elasticSearchSelect = new ElasticSearchSelect();

        RestHighLevelClient client = ESClient.getClient();

//        elasticSearchSelect.termSearch(client);

//        elasticSearchSelect.matchQuery(client);

//        elasticSearchSelect.like(client);

        elasticSearchSelect.aggregation(client);

        client.close();

    }


}
