package pers.walyex.common.redis.util;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

/**
 * redis工具类
 *
 * @author Waldron Ye
 * @date 2019/5/31 21:02
 */
@Slf4j
public class RedisUtil {

    private JedisPool jedisPool;

    public RedisUtil(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取jedis
     *
     * @return
     */
    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 关闭jedis
     *
     * @param redis
     */
    private void returnResource(Jedis redis) {
        if (redis != null) {
            redis.close();
        }
    }

    /**
     * 设置数据
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("设置数据msg=[{}],key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置数据
     *
     * @param key
     * @param value
     * @param expireSeconds 过期时间(秒)
     */
    public void setString(String key, String value, int expireSeconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("设置数据msg=[{}],key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取数据msg=[{}],key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 删除keys对应的记录,可以是多个key
     *
     * @param keys
     * @return
     */
    public long delete(String... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除keys对应的记录msg=[{}],keys=[{}]", e, keys);
        } finally {
            returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 设置对象类型数据
     *
     * @param objectKey
     * @param objectValue
     */
    public void setObject(Object objectKey, Object objectValue) {
        byte[] key = SerializeUtil.serialize(objectKey);
        byte[] value = SerializeUtil.serialize(objectValue);
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("设置对象类型数据msg=[{}], key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置对象类型数据,有过期时间
     *
     * @param objectKey
     * @param objectValue
     * @param expireSeconds
     */
    public void setObject(Object objectKey, Object objectValue, int expireSeconds) {
        byte[] key = SerializeUtil.serialize(objectKey);
        byte[] value = SerializeUtil.serialize(objectValue);
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("设置对象类型数据,有过期时间msg=[{}], key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取数据
     *
     * @param objectKey
     * @return
     */
    public Object getObject(Object objectKey) {
        byte[] key = SerializeUtil.serialize(objectKey);
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取数据msg=[{}], key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
        if (value == null) {
            return null;
        }
        return SerializeUtil.unSerialize(value);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param expireSeconds
     */
    public void setExpire(String key, int expireSeconds) {
        getJedis().expire(key, expireSeconds);
    }

    /**
     * 设置过期时间
     *
     * @param objectKey
     * @param expireSeconds
     */
    public void setExpire(Object objectKey, int expireSeconds) {
        byte[] key = SerializeUtil.serialize(objectKey);
        getJedis().expire(key, expireSeconds);
    }

    /**
     * 删除keys对应的记录,可以是多个key
     *
     * @param keys
     * @return
     */
    public long delete(byte[]... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除keys对应的记录msg=[{}], key=[{}]", e, keys);
        } finally {
            returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 查找所有匹配给定的模式的键
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set<String> set = null;
        try {
            jedis = getJedis();
            set = jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查找所有匹配给定的模式的键msg=[{}]", e);
        } finally {
            returnResource(jedis);
        }
        return set;
    }

    /**
     * 清空全部数据
     *
     * @return
     */
    public String flushAll() {

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("清空全部数据msg=[{}]", e);
        } finally {
            returnResource(jedis);
        }
        return "";
    }

    /**
     * 判断是否存在key
     *
     * @param objectKey
     * @return
     */
    public boolean existsKey(Object objectKey) {
        Jedis jedis = null;
        byte[] key = SerializeUtil.serialize(objectKey);
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("判断是否存在key的msg=[{}],key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 判断是否存在key
     *
     * @param key
     * @return
     */
    public boolean existsKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("判断是否存在key的msg=[{}], key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 保存进map
     *
     * @param key
     * @param k
     * @param v
     * @param expire
     */
    public void put(Object key, Object k, Object v, Integer expire) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(SerializeUtil.serialize(key), SerializeUtil.serialize(k), SerializeUtil.serialize(v));
            if (expire != null) {
                jedis.expire(SerializeUtil.serialize(key), expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存进map的msg=[{}],key=[{}]", e, key);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 取出map
     *
     * @param key
     * @param k
     * @return object
     */
    public Object mapGet(Object key, Object k) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] bytes = jedis.hget(SerializeUtil.serialize(key), SerializeUtil.serialize(k));
            return SerializeUtil.unSerialize(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" 取出map的msg=[{}], key=[{}]", e, key);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 取出map
     *
     * @param key
     * @return map
     */
    public Map<byte[], byte[]> mapGetAll(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(SerializeUtil.serialize(key));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" 取出map的msg=[{}], key=[{}]", e, key);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 自增长
     *
     * @param key
     * @return long
     */
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自增长msg=[{}], key=[{}]", e, key);
            return null;
        } finally {
            returnResource(jedis);
        }
    }


    /**
     * 自减
     *
     * @param key
     * @return long
     */
    public Long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.decr(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自减msg=[{}], key=[{}]", e, key);
            return null;
        } finally {
            returnResource(jedis);
        }
    }
}
