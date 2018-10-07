package com.huarui;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lihui on 2018/10/7.
 * 多条件查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMultiSearch {

    @Autowired
    private TransportClient client;


    /**
     * 查询title 包含战 并且 content 包含星期的电影
     * @throws Exception
     */
    @Test
    public void searchMulti()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder= QueryBuilders.matchPhraseQuery("title", "战");
        QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .must(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 查询title 包含的 并且 content 不包含武士的电影
     * @throws Exception
     */
    @Test
    public void searchMulti2()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "的");
        QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "武士");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .mustNot(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println("\r\n"+hit.getSourceAsString());
        }
    }

    /**
     * 查询title包含战 根据content是否包含星球 publishDate 大于18-1-1 来得分
     * @throws Exception
     */
    @Test
    public void searchMulti3()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
        QueryBuilder queryBuilder3=QueryBuilders.rangeQuery("publishDate").gte("2018-01-01");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .should(queryBuilder2)
                .should(queryBuilder3))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getScore()+":"+hit.getSourceAsString());
        }
    }

    /**
     * 查询tilte包含战 并且过滤掉票价大于40的电影
     * @throws Exception
     */
    @Test
    public void searchMulti4()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        QueryBuilder queryBuilder2=QueryBuilders.rangeQuery("price").lte(40);
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .filter(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
}
