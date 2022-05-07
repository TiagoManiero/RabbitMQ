package com.example.rabbitmq.PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverPubSub {

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

        String nameQueue = channel.queueDeclare().getQueue();


        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");
        channel.queueBind(nameQueue, NAME_EXCHANGE,"");

        DeliverCallback deliverCallback = (ConsumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Mensagem recebida: '" + message + "'");
        };


        boolean autoack = true;
        channel.basicConsume( nameQueue,autoack, deliverCallback, ConsumerTag -> {});

    }
}
