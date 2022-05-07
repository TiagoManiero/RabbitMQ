package com.example.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

    private static String NAME_QUEUE = "HELLO";

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

        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        String message = "Hello World, this is my first created program.";

        channel.basicPublish("", NAME_QUEUE, null, message.getBytes());

        System.out.println("Send: "+message);
    }
}
