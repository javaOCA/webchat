package ua.univerpulse.webchat.mvc.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import javax.annotation.Resource;

@Component
public class PoolFactory {
    private JedisPool jedisPool;
    @Resource
    private Environment env;
    @Autowired
    public PoolFactory(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }

    public Jedis getJedis(){
        return jedisPool.getResource();
    }

}
