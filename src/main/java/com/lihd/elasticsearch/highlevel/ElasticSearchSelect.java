package com.lihd.elasticsearch.highlevel;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.searchafter.SearchAfterBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: lihd-elasticsearch
 * @description:
 * @author: li_hd
 * @create: 2020-05-01 20:48
 **/
public class ElasticSearchSelect {

    void termSearch(RestHighLevelClient client) throws IOException {

        SearchRequest request = new SearchRequest("lihd");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("name", "测试10030");
        sourceBuilder.query(termQueryBuilder);

        request.searchType(SearchType.QUERY_THEN_FETCH);
        request.source(sourceBuilder);

        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        System.out.println(search);


    }


    void matchQuery(RestHighLevelClient client) throws IOException {

        SearchRequest request = new SearchRequest("lihd");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 查询条件分词
        sourceBuilder.query(QueryBuilders.matchQuery("name", "张三"));
        // 查询条件不分词
//        sourceBuilder.query(QueryBuilders.matchPhraseQuery("name","张三"));

        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(sourceBuilder);

        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        System.out.println(search);

    }


    void like(RestHighLevelClient client) throws IOException {
        SearchRequest request = new SearchRequest("lihd");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder("name", "*99");
        sourceBuilder.query(wildcardQueryBuilder);
        request.source(sourceBuilder);

        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(search);

    }


    void and(RestHighLevelClient client) throws IOException {
        SearchRequest request = new SearchRequest("lihd");
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // ? 表示单个占位  * 表示多个占位
        boolQueryBuilder.must(QueryBuilders.wildcardQuery("name", "*4"));
        boolQueryBuilder.must(QueryBuilders.termQuery("age", "20"));

        sourceBuilder.query(boolQueryBuilder);
        request.searchType(SearchType.QUERY_THEN_FETCH);
        request.source(sourceBuilder);
        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(search);
    }


    public void or(RestHighLevelClient client) throws IOException {
        SearchRequest request = new SearchRequest("lihd");
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        boolQueryBuilder.should(QueryBuilders.termQuery("name", "测试10030"));
        boolQueryBuilder.should(QueryBuilders.termQuery("name", "测试10031"));

        sourceBuilder.query(boolQueryBuilder);
        request.searchType(SearchType.QUERY_THEN_FETCH);
        request.source(sourceBuilder);

        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(search);
    }


    public void range(RestHighLevelClient client) throws IOException {
        SearchRequest request = new SearchRequest("lihd");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("age");
        rangeQueryBuilder.gte("30");
        //rangeQueryBuilder.lt("31");
        sourceBuilder.query(rangeQueryBuilder);
        request.source(sourceBuilder);
        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(search);
    }


    /**
     * 分页
     */
    public void pagination(RestHighLevelClient client) throws IOException {
        SearchRequest request = new SearchRequest("lihd");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 超过1w条会报错，深分页问题
        sourceBuilder.from(9999);
        sourceBuilder.size(100);
        sourceBuilder.sort("_id");
        request.source(sourceBuilder);
        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(search);
    }

    /**
     * 分页
     */
    public void paginationSearchAfter(RestHighLevelClient client) throws IOException {
        SearchRequest request = new SearchRequest("lihd");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchAfterBuilder searchAfterBuilder = new SearchAfterBuilder();
        // 超过1w条会报错，深分页问题
        sourceBuilder.size(100);
        sourceBuilder.sort("_id");
        sourceBuilder.searchAfter(new Object[]{20000});
        request.source(sourceBuilder);
        final SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(search);
    }

    /**
     * 聚合  group by
     */
    public void aggregation(RestHighLevelClient client) {

        SearchRequest request = new SearchRequest("lihd");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_age")
                // 设置聚合字段、聚合后的数据量，默认10条
                .field("age").size(20);

        aggregation.subAggregation(AggregationBuilders.terms("by_name")
                // 设置聚合字段、聚合后的数据量，默认10条
                .field("name").size(20));
        aggregation.subAggregation(AggregationBuilders.sum("sum_score").field("score"));

        /*AggregationBuilder aggregation = AggregationBuilders.sum("by_age")
                .field("age");*/

        sourceBuilder.aggregation(aggregation);
        request.source(sourceBuilder);

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            Aggregations aggregations = response.getAggregations();
            aggregations.forEach(agg -> {
                System.out.println(agg.getName() + ":");
                ((ParsedLongTerms) agg).getBuckets().forEach(bk -> {
                            System.out.println("聚合doc数量：" + bk.getDocCount());
                            System.out.println("聚合key值：" + bk.getKeyAsString());
                            bk.getAggregations().getAsMap().forEach((key, value) -> {
                                System.out.println("key:" + key + "  value:" + value);
//                                value.getMetaData().keySet().forEach(k -> System.out.println(value.getMetaData().get(k)));
                            });
                        }
                );
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
