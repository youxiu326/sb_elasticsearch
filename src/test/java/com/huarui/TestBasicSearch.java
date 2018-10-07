package com.huarui;

import com.google.gson.JsonObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 基础增删改查
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBasicSearch {

	@Autowired
	private TransportClient client;

	@Test
	public void contextLoads() throws UnknownHostException {
		System.out.println(client);
	}

	/**
	 * 创建索引 添加文档
	 */
	@Test
	public void testAddIndex(){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name","老刘");
		jsonObject.addProperty("age",35);
		jsonObject.addProperty("salary",6000);

		IndexResponse indexResponse = client.prepareIndex("man", "teacher")
				.setSource(jsonObject.toString(), XContentType.JSON).get();

		System.out.println("索引名称："+indexResponse.getIndex());
		System.out.println("类型："+indexResponse.getType());
		System.out.println("文档ID："+indexResponse.getId());
		System.out.println("当前实例状态："+indexResponse.status());
	}

	/**
	 * 根据id获取文档
	 */
	@Test
	public void testGet(){
		GetResponse getResponse = client.prepareGet("man", "teacher", "AWZHdwZ15PB9pOd0G9j2").get();
		System.out.println(getResponse.getSourceAsString());
	}

	/**
	 * 根据id修改文档
	 */
	@Test
	public void testUpdate(){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name","老刘");
		jsonObject.addProperty("age",36);
		jsonObject.addProperty("salary",9000);
		UpdateResponse updateResponse = client.prepareUpdate("man", "teacher", "AWZHdwZ15PB9pOd0G9j2")
				.setDoc(jsonObject.toString(), XContentType.JSON).get();
		System.out.println("索引名称："+updateResponse.getIndex());
		System.out.println("类型："+updateResponse.getType());
		System.out.println("文档ID："+updateResponse.getId());
		System.out.println("当前实例状态："+updateResponse.status());
	}

	/**
	 * 根据id删除文档
	 */
	@Test
	public void testDelete(){
		DeleteResponse deleteResponse = client.prepareDelete("man", "teacher", "AWZHdwZ15PB9pOd0G9j2").get();
		System.out.println("索引名称："+deleteResponse.getIndex());
		System.out.println("类型："+deleteResponse.getType());
		System.out.println("文档ID："+deleteResponse.getId());
		System.out.println("当前实例状态："+deleteResponse.status());
	}


}
