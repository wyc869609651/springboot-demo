package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 自定义key的生成策略
     * 如果@Cacheable、@CacheEvict指定的key值则覆盖该方法
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()).append(":");
                sb.append(method.getName()).append(":");
                for (Object param :
                        params) {
                    sb.append(param.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 缓存管理
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jsonSerializer);
        RedisCacheConfiguration defaultCacheConfig= RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(30)) //设置过期时间30s
                .disableCachingNullValues() //不缓存null值
                .serializeValuesWith(pair);
        //设置默认超过期时间是30秒
        //defaultCacheConfig.entryTtl(Duration.ofSeconds(30));//这种方式错误
        //初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }
    //1.x版的写法2.x中不能用了
    /*public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        cacheManager.setDefaultExpiration(10000);
        return cacheManager;
    }*/

    /**
     * 配置RedisTemplate的序列化策略
     * @param redisConnectionFactory
     * @param <T>
     * @return
     */
    @Bean
    public <T> RedisTemplate<String, T> redisTemplateKeyString(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
