package org.wfq.wufangquan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class redisConfiguration {

    // 创建 RedisTemplate 配置
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置 Redis 键的序列化方式
        template.setKeySerializer(new StringRedisSerializer());

        // 设置 Redis 值的序列化方式
        template.setValueSerializer(RedisSerializer.string());

        return template;
    }
}