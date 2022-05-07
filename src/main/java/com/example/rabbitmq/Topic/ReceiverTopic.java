package com.example.rabbitmq.Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverTopic {
    private static String BINDKEY_NAME = "*.*.rabbit";
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

        String nameQueue = channel.queueDeclare().getQueue();


        channel.exchangeDeclare(NAME_EXCHANGE, "topic");
        channel.queueBind(nameQueue, NAME_EXCHANGE,BINDKEY_NAME);

        DeliverCallback deliverCallback = (ConsumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Mensagem recebida: '" + message + "'");
        };

        channel.basicConsume( nameQueue, true, deliverCallback, ConsumerTag -> {});

    }
}
