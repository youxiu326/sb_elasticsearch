package com.huarui;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
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
 * IK分词查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestIkSearch {

    @Autowired
    private TransportClient client;

    /**
     * 分词查询
     * @throws Exception
     */
    @Test
    public void search()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film2").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title", "再也不见").analyzer("ik_max_word"))
                .setFetchSource(new String[]{"title","price"}, null)
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 多字段分词查询
     * @throws Exception
     */
    @Test
    public void search2()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film2").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.multiMatchQuery("非洲星球铁拳", "title","content").analyzer("ik_smart"))
                .setFetchSource(new String[]{"title","price"}, null)
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
}
