package com.example.demoredis.MessageRabbitmq;

import com.rabbitmq.client.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Component
public class Receiver {
    @Autowired
    private AmqpTemplate rabbitTemplate;
//
//    @RabbitListener(queues = "${broker.queue}")
//    public void receivedMessage(Object o) {
//
//        System.out.println("Received Message From RabbitMQ: " + o.toString());
//
//    }
    public Object receivedWithChanel(String queue){
        System.out.println(rabbitTemplate.receiveAndConvert(queue));

       return  "ok";
    }
}
 //   public String receivedWithChanel(String queue) throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        factory.setUsername("guest");
//        factory.setPassword("guest1");
//        factory.setVirtualHost("/");
//        System.out.println("Create a Connection");
//        System.out.println("Create a Channel");
//        Connection connection = factory.newConnection();
//
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(queue, false, false, false, null);
//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//
//        System.out.println("Start receiving messages ... ");
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] Received: '" + message + "'");
//        };
//        CancelCallback cancelCallback = consumerTag -> { };
//        String consumerTag = channel.basicConsume(queue, true, deliverCallback, cancelCallback);
//        channel.close();
//        System.out.println("consumerTag: " + consumerTag);
//        return consumerTag;
//
//    }

