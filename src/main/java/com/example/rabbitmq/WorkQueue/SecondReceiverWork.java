package com.example.rabbitmq.WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceiverWork {
    private static String NAME_QUEUE = "WORK";

    private static void doWork(String task) throws InterruptedException {
        for(char ch : task.toCharArray()){
            if(ch == '.'){
                Thread.sleep(1000);
            }
        }
    }

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

        DeliverCallback deliverCallback = (ConsumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Mensagem recebida: '" + message + "'");
            try{
                doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };


        boolean autoack = false;
        channel.basicConsume( NAME_QUEUE,autoack, deliverCallback, ConsumerTag -> {});

    }
}
