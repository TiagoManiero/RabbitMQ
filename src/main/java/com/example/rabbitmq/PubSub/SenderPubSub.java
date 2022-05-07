package com.example.rabbitmq.PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderPubSub {

    private static String NAME_EXCHANGE = "fanoutExchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        System.out.println(connection.hashCode());

        Channel channel = connection.createChannel();
        System.out.println(channel);

        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");

        String message = "Hello! This is a pub sub system!";

        channel.basicPublish(NAME_EXCHANGE, "", null, message.getBytes());

        System.out.println("Send: '"+message+"'");
    }
}
