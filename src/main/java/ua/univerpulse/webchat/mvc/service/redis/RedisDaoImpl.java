package ua.univerpulse.webchat.mvc.service.redis;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisDaoImpl implements RedisDao{

    private PoolFactory poolFactory = PoolFactory.getInstance();

    @Override
    public void saveDataByKey(String key, String value) {
        poolFactory.getJedis().lpush(key,value);
    }

    @Override
    public List<String> getAllDataByKey(String key) {
        //0 - c первого елемента, -1 - до последнего
        return poolFactory.getJedis().lrange(key,0,-1);
    }
}
