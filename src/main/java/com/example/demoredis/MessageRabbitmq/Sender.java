package com.example.demoredis.MessageRabbitmq;






import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${broker.exchange}")
    private String exchange;

    @Value("${broker.routingKey}")
    private String key;

    public void send(Object company) {
         rabbitTemplate.convertAndSend(exchange,key, company.toString());
      //  System.out.println("receive " +rabbitTemplate.receive("queue.name"));
        System.out.println("Send msg = " + company);

    }
}
