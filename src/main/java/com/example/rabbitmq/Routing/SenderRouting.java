package com.example.rabbitmq.Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderRouting {
    private static String ROUTING_KEY = "routingKeyTest";
    private static String SECOND_ROUTING_KEY = "secondRoutingKeyTest";
    private static String NAME_EXCHANGE = "directExchange";

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

        channel.exchangeDeclare(NAME_EXCHANGE, "direct");

        String message = "Hello! This is a RabbitMQ system!";
        String message2 = "Hello! This is a second message of routing key based system!";

        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());
        channel.basicPublish(NAME_EXCHANGE, SECOND_ROUTING_KEY, null, message2.getBytes());

        System.out.println("Send: '"+message+"'");
        System.out.println("Send: '"+message2+"'");
    }
}
