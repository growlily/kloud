package kloud.backend.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport{

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 程序启动时清理所有缓存
     */
    @PostConstruct
    void flushAll() {
        lettuceConnectionFactory.getConnection().flushAll();
    }

    /**
     * Redis template
     * @return redis template
     */
    @Primary
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        //设置key的序列化方式为string
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置value的序列化方式为JSON
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }


    /**
     * cache manager
     * @param redisConnectionFactory
     * @return cache manager
     */
    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(15)))
                .transactionAware()
                .build();

    }

    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(method.getName());
                sb.append('#');
                for(Object obj : objects) {
                    sb.append(objects.toString());
                    sb.append('_');
                }
                return sb.toString();
            }
        };
    }
}
