package com.lihd.elasticsearch.highlevel;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 20:48
 **/
@Service
public class ElasticSearchSelect {


    @Resource
    private RestHighLevelClient restHighLevelClient;


    public String term(String index, String value) throws IOException {

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("desc",value);
        searchSourceBuilder.query(termQueryBuilder);
        searchRequest.searchType(SearchType.QUERY_THEN_FETCH);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return response.toString();
    }


    public String match(String index, String value) throws IOException {

        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询条件分词
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("desc",value);
        sourceBuilder.query(matchQueryBuilder);
        // 查询条件不分词
//        sourceBuilder.query(QueryBuilders.matchPhraseQuery("name","张三"));
        request.source(sourceBuilder);
        final SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return search.toString();

    }


    public String like(String index, String value) throws IOException {

        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder("desc",value);
        sourceBuilder.query(wildcardQueryBuilder);
        request.source(sourceBuilder);
        final SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        return search.toString();

    }


    public String and(String index, String value) throws IOException {

        SearchRequest request = new SearchRequest(index);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // ? 表示单个占位  * 表示多个占位
        boolQueryBuilder.must(QueryBuilders.wildcardQuery("name", "*4"));
        boolQueryBuilder.must(QueryBuilders.termQuery("age", "20"));

        sourceBuilder.query(boolQueryBuilder);
        request.searchType(SearchType.QUERY_THEN_FETCH);
        request.source(sourceBuilder);
        final SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return search.toString();
    }


    public String or(String index, String value) throws IOException {

        SearchRequest request = new SearchRequest(index);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        boolQueryBuilder.should(QueryBuilders.termQuery("score", "70.51"));
        boolQueryBuilder.should(QueryBuilders.termQuery("score", "70.57"));

        sourceBuilder.query(boolQueryBuilder);
        request.searchType(SearchType.QUERY_THEN_FETCH);
        request.source(sourceBuilder);

        final SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        return search.toString();
    }


    public String range(String index, String value) throws IOException {

        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("age");
        rangeQueryBuilder.gte(value);
        //rangeQueryBuilder.lt("31");
        sourceBuilder.query(rangeQueryBuilder);
        request.source(sourceBuilder);
        final SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        return search.toString();
    }


    /**
     * 分页
     */
    public String pagination(String index, String value) throws IOException {


        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 超过1w条会报错，深分页问题
        sourceBuilder.from(10);
        sourceBuilder.size(100);
        sourceBuilder.sort("_id");
        request.source(sourceBuilder);
        final SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        return search.toString();
    }


}
