package pers.walyex.common.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pers.walyex.common.redis.properties.JedisProperties;
import pers.walyex.common.redis.util.RedisUtil;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@EnableConfigurationProperties(JedisProperties.class)
public class RedisConfigure {

    @Autowired
    private JedisProperties jedisProperties;

    @Bean("jedisPoolConfig")
    public JedisPoolConfig createJedisPoolConfig() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(jedisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(jedisProperties.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(jedisProperties.getTestOnBorrow());
        jedisPoolConfig.setTestWhileIdle(jedisProperties.getTestWhileIdle());

        jedisPoolConfig.setTestWhileIdle(jedisProperties.getTestWhileIdle());
        jedisPoolConfig.setNumTestsPerEvictionRun(jedisProperties.getNumTestsPerEvictionRun());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(jedisProperties.getTimeBetweenEvictionRunsMillis());

        return jedisPoolConfig;
    }

    @Bean("jedisPool")
    public JedisPool createJedisPool(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
        return new JedisPool(jedisPoolConfig, jedisProperties.getHost(), jedisProperties.getPort(), jedisProperties.getTimeoutSecond(),
                jedisProperties.getPassword(), jedisProperties.getDatabase());
    }

    @Bean
    public RedisUtil createRedisUtil(@Qualifier("jedisPool") JedisPool jedisPool) {

        return new RedisUtil(jedisPool);
    }
}
