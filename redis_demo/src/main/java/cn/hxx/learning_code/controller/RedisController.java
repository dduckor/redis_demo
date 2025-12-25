import com.example.redisdemo.entity.User;
import com.example.redisdemo.service.RedisService;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    // ===================== String类型测试 =====================
    @PostMapping("/string/set")
    public String setString(@RequestParam String key, @RequestParam String value) {
        redisService.setString(key, value);
        return "String类型设置成功";
    }

    @PostMapping("/string/set/user")
    public String setUser() {
        User user = new User(1L, "张三", 20);
        redisService.setStringWithExpire("user:1", user, 60, TimeUnit.SECONDS);
        return "用户对象设置成功（60秒过期）";
    }

    @GetMapping("/string/get")
    public Object getString(@RequestParam String key) {
        return redisService.getString(key);
    }

    // ===================== Hash类型测试 =====================
    @PostMapping("/hash/put")
    public String putHash(@RequestParam String key, @RequestParam String hashKey, @RequestParam String value) {
        redisService.putHash(key, hashKey, value);
        return "Hash类型设置成功";
    }

    @GetMapping("/hash/get")
    public Object getHash(@RequestParam String key, @RequestParam String hashKey) {
        return redisService.getHash(key, hashKey);
    }

    @GetMapping("/hash/getAll")
    public Map<Object, Object> getHashAll(@RequestParam String key) {
        return redisService.getHashAll(key);
    }

    // ===================== List类型测试 =====================
    @PostMapping("/list/rightPush")
    public String rightPushList(@RequestParam String key, @RequestParam String value) {
        redisService.rightPushList(key, value);
        return "List右添加成功";
    }

    @GetMapping("/list/get")
    public List<Object> getList(@RequestParam String key, @RequestParam(defaultValue = "0") long start, @RequestParam(defaultValue = "-1") long end) {
        return redisService.getList(key, start, end);
    }

    // ===================== Set类型测试 =====================
    @PostMapping("/set/add")
    public String addSet(@RequestParam String key, @RequestParam String... values) {
        redisService.addSet(key, (Object[]) values);
        return "Set添加成功";
    }

    @GetMapping("/set/get")
    public Set<Object> getSet(@RequestParam String key) {
        return redisService.getSet(key);
    }

    // ===================== ZSet类型测试 =====================
    @PostMapping("/zset/add")
    public String addZSet(@RequestParam String key, @RequestParam String value, @RequestParam double score) {
        redisService.addZSet(key, value, score);
        return "ZSet添加成功";
    }

    @GetMapping("/zset/get")
    public Set<ZSetOperations.TypedTuple<Object>> getZSet(@RequestParam String key) {
        return redisService.getZSetWithScore(key, 0, -1);
    }

    // ===================== 通用操作 =====================
    @DeleteMapping("/key/delete")
    public String deleteKey(@RequestParam String key) {
        Boolean result = redisService.deleteKey(key);
        return result ? "Key删除成功" : "Key不存在或删除失败";
    }
}