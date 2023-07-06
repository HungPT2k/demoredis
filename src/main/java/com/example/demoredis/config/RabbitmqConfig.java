package com.example.demoredis.config;



import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableRabbit
//@ConditionalOnExpression("${spring.rabbitmq.enable:false}")
public class RabbitmqConfig {
    @Value("${broker.queue}")
    private String queueName;
    @Value("${broker.exchange}")
    private String exchange;
    @Value("${broker.routingKey}")
    private String key;

    @Bean
    Queue queue() {
        return new Queue(queueName,true,true,false);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }
//    @Bean
//    TopicExchange topicExchange(){return new TopicExchange(exchange);}
//    @Bean
//    FanoutExchange fanoutExchange(){return new FanoutExchange(exchange);}

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(key);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
////        connectionFactory.setVirtualHost(virtualHost);
//        connectionFactory.setHost(host);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        return connectionFactory;
//    }
//    @Bean
//    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setDefaultReceiveQueue("adminQueue");
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        rabbitTemplate.setReplyAddress(queue().getName());
//        rabbitTemplate.setReplyTimeout(replyTimeout);
//        rabbitTemplate.setUseDirectReplyToContainer(false);
//        return rabbitTemplate;
//    }

//    @Bean
//    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter messageListenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
}
