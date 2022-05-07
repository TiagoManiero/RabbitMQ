package com.example.rabbitmq.DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DlxConfig {
    public static final String DLX_NAME = "dlxExchange";
    public static final String DLX_QUEUE = "dlxQueue";
    public static final String DLX_BINDING_KEY = "dlxrk";
    //EXCHANGE do sistema
    private static final String EXCHANGE_NAME = "mainExchange";

    //CONSUMER
    private static final String CONSUMER_QUEUE = "queueConsumer";
    private static final String CONSUMER_BINDING_KEY = "bkConsumer";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setPort(5672);
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DLX_NAME,"topic");
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        channel.queueDeclare(DLX_QUEUE,false,false,false,null);

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("x-message-ttl",10000);
        map.put("x-dead-letter-exchange",DLX_NAME);
        map.put("x-dead-letter-routing-key",DLX_BINDING_KEY);
        channel.queueDeclare(CONSUMER_QUEUE,false,false,false,map);

        channel.queueBind(DLX_QUEUE,DLX_NAME,DLX_BINDING_KEY+".#");
        channel.queueBind(CONSUMER_QUEUE,EXCHANGE_NAME,CONSUMER_BINDING_KEY+".#");

        connection.close();

    }
}
