package com.lihd.elasticsearch;

import com.lihd.elasticsearch.highlevel.ElasticSearchCreateIndex;
import org.junit.Test;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 21:31
 **/
public class ElasticSearchCreateIndexTest {


    @Test
    public void test(){

        ElasticSearchCreateIndex elasticSearchCreateIndex = new ElasticSearchCreateIndex();

        elasticSearchCreateIndex.createIndex();


    }


}
