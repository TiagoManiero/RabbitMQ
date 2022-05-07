package com.example.rabbitmq.DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SenderDlx {
    private static String NAME_EXCHAGE = "mainExchange";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setPort(5672);

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(NAME_EXCHAGE,"topic");

            //criar a mensagem
            String message = "Hello! This is a test!";
            String routingkey = "bkConsumer.consumer";
            channel.basicPublish(NAME_EXCHAGE, routingkey, null, message.getBytes());

            System.out.print("Done'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
