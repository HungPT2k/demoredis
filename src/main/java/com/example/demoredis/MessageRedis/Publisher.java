package com.example.demoredis.MessageRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class Publisher implements MessagePublisher{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ChannelTopic topic;

    public Publisher() {
    }

    public Publisher(
            RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(Object student) {
        System.out.println("Sending Student details: " + student);
        redisTemplate.convertAndSend(topic.getTopic(), student.toString());
    }



}
