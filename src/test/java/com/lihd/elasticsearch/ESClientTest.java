package com.lihd.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 21:20
 **/
public class ESClientTest {


    @Test
    public void test() throws IOException {

        final RestHighLevelClient client = ESClient.getClient();

        System.out.println(client);

        client.close();
    }


}
