package com.xiafei.springboot.starter.autoconfig.nosql.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <P>Description: 使用时只需调用有参构造方法初始化jdedisCluster. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2017/6/7</P>
 * <P>UPDATE DATE: 2017/6/28</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.7.0
 */
public class JedisClient {

    private static final Logger log = LoggerFactory.getLogger(JedisClient.class);

    private JedisCluster jedisCluster;
    /**
     * 最大连接数.
     */
    private Integer maxTotal = 100;

    /**
     * 空闲连接数.
     */
    private Integer maxIdle = 50;

    /**
     * 等待超时时间.
     */
    private Integer maxWaitTime = 3000;

    /**
     * redis 工具类初始化构造函数
     * <p> 允许Spring 注入静态使用,要求 redis地址必输,如果当前存在jedisCluster 则使用存在的不再初始化 </p>
     * <p> redisAdress 表达格式要求 例 127.0.0.1:2080,127.0.0.1:2081</p>
     * <p> 注意：如果redisPassword为空，则构造函数尝试使用无密码创建客户端</p>
     *
     * @param redisAdress
     * @param redisPassword
     * @param maxTotal
     * @param maxIdle
     * @param maxWaitTime
     * @throws Exception <p> 如果必要参数为空 throw Exception ,如果 初始化jedisCluster失败 throw Exception</p>
     * @author hujian create 2017-09-15
     */
    public JedisClient(final String redisAdress, final String redisPassword, final Integer maxTotal, final Integer maxIdle, final Integer maxWaitTime) throws Exception {
        if (jedisCluster != null) {
            return;
        }
        if (!StringUtils.isEmpty(redisAdress)) {
            this.maxTotal = maxTotal != null ? maxTotal : this.maxTotal;
            this.maxIdle = maxIdle != null ? maxIdle : this.maxIdle;
            this.maxWaitTime = maxWaitTime != null ? maxWaitTime : this.maxWaitTime;
            String[] redisArr = redisAdress.split(",");
            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            for (String re : redisArr) {
                String[] url = re.split(":"); // host:port
                int port = Integer.parseInt(url[1]);
                jedisClusterNodes.add(new HostAndPort(url[0], port));
            }
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(this.maxTotal);//the max number of connection
            jedisPoolConfig.setMaxIdle(this.maxIdle);//the max number of free
            jedisPoolConfig.setMaxWaitMillis(this.maxWaitTime);//the longest time of waiting
            try {
                if (StringUtils.isBlank(redisPassword)) {
                    jedisCluster = new JedisCluster(jedisClusterNodes, 5000, 5000, 1, jedisPoolConfig);
                } else {
                    jedisCluster = new JedisCluster(jedisClusterNodes, 5000, 5000, 1, redisPassword, jedisPoolConfig);
                }
            } catch (Exception e) {
                log.error("********jedis fail to initialize********", e);
                throw e;
            }

        } else {
            log.error("*******redisAdress is  null*********");
            throw new Exception("redisAdress is  null");
        }
    }

    public void put(String key, String value) {
        assertPoolNotNull();
        jedisCluster.set(key, value);
    }

    public void putWithExpire(String key, String value, int seconds) {
        assertPoolNotNull();
        jedisCluster.set(key, value);
        jedisCluster.expire(key, seconds);
    }

    public void set(final String key, final String value, final String nxxx, final String expx,
                    final long time) {
        assertPoolNotNull();
        jedisCluster.set(key, value, nxxx, expx, time);
    }

    public void set(final String key, final String value) {
        assertPoolNotNull();
        jedisCluster.set(key, value);
    }

    /**
     * 设置key并附上过期时间.
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间-秒
     * @return OK-设置成功
     */
    public String setEx(final String key, final String value, final int time) {
        assertPoolNotNull();
        return jedisCluster.setex(key, time, value);
    }

    /**
     * 如果存在则设置.
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间-秒
     * @return 设置是否成功
     */
    public boolean setIfExist(final String key, final String value, final int time) {
        assertPoolNotNull();
        return "OK".equalsIgnoreCase(jedisCluster.set(key, value, JedisEnums.NxxxEnum.XX.getCode(),
                JedisEnums.EXPX.SECOND.getCode(), time));
    }


    public boolean setNxEX(final String key, final String value, final String nxxx, final String expx,
                           final long time) {
        assertPoolNotNull();
        return "OK".equalsIgnoreCase(jedisCluster.set(key, value, nxxx, expx, time));
    }

    public boolean expireAt(final String key, final Date date) {
        assertPoolNotNull();
        try {
            return jedisCluster.pexpireAt(key, date.getTime()) == 1 ? true : false;
        } catch (Exception e) {
            return jedisCluster.expireAt(key, date.getTime() / 1000) == 1 ? true : false;
        }
    }

    public void expire(String key, int seconds) {
        assertPoolNotNull();
        jedisCluster.expire(key, seconds);
    }

    public Long incr(String key) {
        assertPoolNotNull();
        return jedisCluster.incr(key);
    }

    public Long hset(String key, String field, String value) {
        assertPoolNotNull();
        return jedisCluster.hset(key, field, value);
    }

    public Long hset(byte[] key, byte[] field, byte[] value) {
        assertPoolNotNull();
        return jedisCluster.hset(key, field, value);
    }

    public Long hdel(String key, String... field) {
        assertPoolNotNull();
        return jedisCluster.hdel(key, field);
    }

    public Long decr(String key) {
        assertPoolNotNull();
        return jedisCluster.decr(key);
    }

    public boolean sismember(String key, String member) {
        assertPoolNotNull();
        return jedisCluster.sismember(key, member);
    }

    public void sadd(String key, String... member) {
        assertPoolNotNull();
        jedisCluster.sadd(key, member);
    }

    public Set<String> smembers(String key, String... member) {
        assertPoolNotNull();
        return jedisCluster.smembers(key);
    }

    public Long ttl(String key) {
        assertPoolNotNull();
        return jedisCluster.ttl(key);
    }

    public void batchPut(Map<String, String> map) {
        assertPoolNotNull();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jedisCluster.set(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 从redis中尝试获取一个key的值.
     *
     * @param key 这还用解释
     * @return 如果这个key在redis中不存在，返回null而不是"nil"
     */
    public String get(String key) {
        assertPoolNotNull();
        return jedisCluster.get(key);
    }

    public boolean exists(String key) {
        assertPoolNotNull();
        return jedisCluster.exists(key);
    }

    public void remove(String key) {
        assertPoolNotNull();
        jedisCluster.del(key);
    }

    //获取hashMap中所有key
    public Set<String> hkeys(String hmName) {
        try {
            return jedisCluster.hkeys(hmName);
        } catch (Exception e) {
            log.error("获取所有key失败", e);
        }
        return null;
    }

    public boolean hmset(String hmKeyname, Map<String, String> map) {
        assertPoolNotNull();
        try {
            jedisCluster.hmset(hmKeyname, map);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String hmget(String hmKeyname, String keyname) {
        assertPoolNotNull();
        return jedisCluster.hget(hmKeyname, keyname);
    }


    public int setnx(String key, String value) {
        assertPoolNotNull();
        return jedisCluster.setnx(key, value).intValue();
    }

    public void lpush(String key, String value, int seconds) {
        assertPoolNotNull();
        jedisCluster.lpush(key, value);
        jedisCluster.expire(key, seconds);
    }

    public void lpush(String key, String value) {
        assertPoolNotNull();
        jedisCluster.lpush(key, value);
    }


    public List<String> brpop(String key) {
        assertPoolNotNull();
        return jedisCluster.brpop(0, key);
    }

    public String lpop(String key) {
        assertPoolNotNull();
        return jedisCluster.lpop(key);
    }

    public Long incrBy(String key, long integer) {
        assertPoolNotNull();
        return jedisCluster.incrBy(key, integer);
    }

    public String getSet(String key, String value) {
        assertPoolNotNull();
        return jedisCluster.getSet(key, value);
    }

    private void assertPoolNotNull() {
        if (jedisCluster == null) {
            throw new NullPointerException("shardedJedisPool is null");
        }
    }

}
