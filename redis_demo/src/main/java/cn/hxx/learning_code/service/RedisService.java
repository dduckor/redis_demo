import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // ===================== String类型操作 =====================
    public void setString(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setStringWithExpire(String key, Object value, long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    public Object getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    // ===================== Hash类型操作 =====================
    public void putHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> getHashAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Boolean deleteHash(String key, String... hashKeys) {
        return redisTemplate.opsForHash().delete(key, (Object[]) hashKeys) > 0;
    }

    // ===================== List类型操作 =====================
    public void leftPushList(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public void rightPushList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public List<Object> getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Object leftPopList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    // ===================== Set类型操作 =====================
    public void addSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Boolean isMemberSet(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long removeSet(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    // ===================== ZSet类型操作 =====================
    public void addZSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> getZSet(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<ZSetOperations.TypedTuple<Object>> getZSetWithScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public Long removeZSet(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }
}