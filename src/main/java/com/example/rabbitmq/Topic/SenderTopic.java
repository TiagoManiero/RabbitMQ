package com.example.rabbitmq.Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderTopic {
    private static String ROUTING_KEY = "quick.orange.rabbit";
    private static String NAME_EXCHANGE = "topicExchange";

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

        channel.exchangeDeclare(NAME_EXCHANGE, "topic");

        String message = "Hello! This is a RabbitMQ system!";

        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());

        System.out.println("Send: '"+message+"'");
    }
}
