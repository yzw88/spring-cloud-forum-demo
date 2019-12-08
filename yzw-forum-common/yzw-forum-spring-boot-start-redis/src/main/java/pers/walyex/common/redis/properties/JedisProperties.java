package pers.walyex.common.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "customize.cache.jedis")
public class JedisProperties {

    /**
     * 端口
     */
    private Integer port;

    /**
     * 连接地址
     */
    private String host;

    /**
     * 密码
     */
    private String password;

    /**
     * 最大空闲连接数
     */
    private Integer maxIdle;

    /**
     * 最大连接数
     */
    private Integer maxTotal;

    /**
     * 最小空闲连接数
     */
    private Integer minIdle;
    /**
     * 获取连接时的最大等待毫秒数
     */
    private Long maxWaitMillis;

    /**
     * 超时时间
     */
    private Integer timeoutSecond;

    /**
     * 需要连接的库
     */
    private Integer database;

    /**
     * 逐出连接的最小空闲时间
     */
    private Long minEvictableIdleTimeMillis;

    /**
     * 检查一次连接池中空闲的连接的间隔时间
     */
    private Long timeBetweenEvictionRunsMillis;

    /**
     * 对于“空闲链接”检测线程而言，每次检测的链接资源的个数
     */
    private Integer numTestsPerEvictionRun;

    /**
     * 向调用者输出“链接”对象时，是否检测它的空闲超时
     */
    private Boolean testWhileIdle;

    /**
     * 当调用return Object方法时，是否进行有效性检查
     */
    private Boolean testOnReturn;

    /**
     * 当调用borrow Object方法时，是否进行有效性检查
     */
    private Boolean testOnBorrow;
}

