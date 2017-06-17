package ua.univerpulse.webchat.mvc.service.redis;

import java.util.List;

public interface RedisDao {

    void saveDataByKey(String key, String value);
    List<String> getAllDataByKey(String key);

}
