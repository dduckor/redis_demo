import com.example.redisdemo.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // 查询用户：缓存不存在则执行方法，结果存入缓存；缓存存在则直接返回缓存数据
    @Cacheable(value = "user", key = "#id") // value=缓存名，key=缓存键（SpEL表达式）
    public User getUserById(Long id) {
        // 模拟从数据库查询
        System.out.println("从数据库查询用户，id=" + id);
        return new User(id, "张三-" + id, 20 + id.intValue());
    }

    // 更新用户：执行方法后，将结果更新到缓存
    @CachePut(value = "user", key = "#user.id")
    public User updateUser(User user) {
        // 模拟更新数据库
        System.out.println("更新数据库用户，id=" + user.getId());
        return user;
    }

    // 删除用户：清除指定缓存
    @CacheEvict(value = "user", key = "#id")
    public void deleteUser(Long id) {
        // 模拟删除数据库用户
        System.out.println("删除数据库用户，id=" + id);
    }
}