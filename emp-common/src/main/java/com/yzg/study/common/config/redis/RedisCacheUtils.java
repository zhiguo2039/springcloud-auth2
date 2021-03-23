package com.yzg.study.common.config.redis;

import com.yzg.study.common.constants.GlobalConstant;
import com.yzg.study.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 来源网络的工具类 redis缓存
 */
public class RedisCacheUtils<T> {

    private final Logger logger = LoggerFactory.getLogger(RedisCacheUtils.class);

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    @Autowired
    private ValueOperations valueOperations;


    public T get(String key) {
        try {
            if (key == null) {
                return null;
            } else {
                return redisTemplate.opsForValue().get(key);
            }
        } catch (Throwable t) {
            throw new CommonException(t);
        }

    }

    public T set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return value;
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public T set(String key, T value, long timeout) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            return value;
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }
    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 134
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子大于0才满足");
        }
        return valueOperations.increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子大于0才满足");
        }
        return valueOperations.increment(key, -delta);
    }

    public void setSet(String k, T value, long time) {
        String key = GlobalConstant.KEY_SET_PREFIX + k;
        try {
            SetOperations<String, T> valueOps = redisTemplate.opsForSet();
            valueOps.add(key, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public void setSet(String k, T value) {
        setSet(k, value,  GlobalConstant.NOT_EXPIRE);
    }


    public void setSet(String k, Set<T> v, long time) {
        String key = GlobalConstant.KEY_SET_PREFIX + k;
        try {
            SetOperations<String, T> setOps = redisTemplate.opsForSet();
            setOps.add(key, (T[]) v.toArray());
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public void setSet(String k, Set<T> v) {
        setSet(k, v,  GlobalConstant.NOT_EXPIRE);
    }

    public Set<T> getSet(String k) {
        String key = GlobalConstant.KEY_SET_PREFIX + k;
        try {
            SetOperations<String, T> setOps = redisTemplate.opsForSet();
            return setOps.members(key);
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public void setList(String k, T v, long time) {
        String key =  GlobalConstant.KEY_LIST_PREFIX + k;
        try {
            ListOperations<String, T> listOps = redisTemplate.opsForList();
            listOps.rightPush(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public void setList(String k, List<T> v, long time) {
        String key =  GlobalConstant.KEY_LIST_PREFIX + k;
        try {
            ListOperations<String, T> listOps = redisTemplate.opsForList();
            listOps.rightPushAll(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public void setList(String k, List<T> v) {
        setList(k, v,  GlobalConstant.NOT_EXPIRE);
    }

    public List<T> getList(String k, long start, long end) {
        String key =  GlobalConstant.KEY_LIST_PREFIX + k;
        try {
            ListOperations<String, T> listOps = redisTemplate.opsForList();
            return listOps.range(key, start, end);
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public long getListSize(String k) {
        String key =  GlobalConstant.KEY_LIST_PREFIX + k;
        try {
            ListOperations<String, T> listOps = redisTemplate.opsForList();
            return listOps.size(key);
        } catch (Throwable t) {

            throw new CommonException(t);
        }
    }

    public long getListSize(ListOperations<String, String> listOps, String k) {
        String key =  GlobalConstant.KEY_LIST_PREFIX + k;
        try {
            return listOps.size(key);
        } catch (Throwable t) {
            throw new CommonException(t);
        }
    }

    public void setMap(String key, String mapkey, T mapValue) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.putIfAbsent(key, mapkey, mapValue);
    }

    public void deleteMap(String key, String mapkey) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, mapkey);
    }

    public T getMap(String key, String mapkey) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, mapkey);
    }

    public List<T> getMapValues(String key) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(key);
    }

    public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}