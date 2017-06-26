package ua.univerpulse.webchat.mvc.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RedisDaoImpl implements RedisDao {

    private PoolFactory poolFactory;

    @Autowired
    public RedisDaoImpl(PoolFactory poolFactory) {
        this.poolFactory = poolFactory;
    }

    @Override
    public void saveDataByKey(String key, String value) {
        poolFactory.getJedis().lpush(key, value);
    }

    @Override
    public List<String> getAllDataByKey(String key) {
        //0 - c первого елемента, -1 - до последнего
//        System.out.println("IN REDIS DAO " + poolFactory.getJedis());
        List<String> strings = poolFactory.getJedis().lrange(key, 0, -1);
//        System.out.println("COLLECTION SIZE IS "+strings);
        return strings;
    }
}