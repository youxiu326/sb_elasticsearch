package com.huarui.configuration;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by lihui on 2018/10/3.
 */
@Configuration
public class EsConfig {

    private static String host = "180.76.96.218";//地址
    private static int port = 9300;//端口

    @Bean
    public TransportClient client() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),port));
        return client;
    }

 /*    public TransportClient client33() throws UnknownHostException {
       Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .build();//设置集群名称
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));


        //Es地址 tcp端口 默认9300
        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName("localhost"),
                9300);
        Settings settings = Settings.builder()
                .put("cluster.name","elasticsearch")
                .build();
        //Es配置
        TransportClient client =
                new PreBuiltTransportClient(settings)
                .addTransportAddress(node);//添加地址
        return client;
    }
*/
}
