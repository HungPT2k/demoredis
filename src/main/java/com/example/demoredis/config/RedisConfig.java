package com.example.demoredis.config;

import com.example.demoredis.DTO.studentDTO;
import com.example.demoredis.MessageRedis.MessagePublisher;
import com.example.demoredis.MessageRedis.Publisher;
import com.example.demoredis.MessageRedis.Subscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@CacheConfig
public class RedisConfig {

    @Value("${redis.student.topic}")
    private String studentTopic;
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("123");
        jedisConnectionFactory.setDatabase(0);
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }
    @Bean
    public RedisMessageListenerContainer listenerContainer(MessageListenerAdapter listenerAdapter,
                                                           RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, topic());
        return container;
    }
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("messageQueue");
    }
//    @Bean
//    public MessageListenerAdapter listenerAdapter(StudentConsumer consumer) {
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(consumer);
//        messageListenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(studentDTO.class));
//        return messageListenerAdapter;
//    }


    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new Subscriber());
    }
    @Bean
    MessagePublisher redisPublisher() {
        return new Publisher(template(), topic());
    }

//    @Bean
//    RedisTemplate<String, studentDTO> redisTemplate(RedisConnectionFactory connectionFactory,
//                                                 Jackson2JsonRedisSerializer<studentDTO> serializer) {
//        RedisTemplate<String, studentDTO> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.setDefaultSerializer(serializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
@Bean
public RedisTemplate<String, Object> template() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new JdkSerializationRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;
}


    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }
    @Bean
    public Jackson2JsonRedisSerializer<studentDTO> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(studentDTO.class);
    }
}
