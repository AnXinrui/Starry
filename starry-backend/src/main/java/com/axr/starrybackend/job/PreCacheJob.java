package com.axr.starrybackend.job;

import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.model.vo.User.UserVO;
import com.axr.starrybackend.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PreCacheJob {
    // 重点用户
    List<Long> mainUserList = Arrays.asList(1L, 2L);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private RedissonClient redissonClient;
    @Scheduled(cron = "0 44 13 * * *")  // 每天13:40执行
    public void preCache() {
        RLock lock = redissonClient.getLock("starry:precachejob:docache:lock");
        // 30s内获取不到锁，就不执行 30s内获取到锁，就执行。只有一个线程执行
        try {
            // 只有一个线程获取锁
            if (lock.tryLock(0, 30000L, TimeUnit.MILLISECONDS)) {  // 抢到了锁
                for (Long userId : mainUserList) {
                    // System.out.println("getLock: " + Thread.currentThread().getId());
                    String redisKey = String.format("star:user:recommend:%s", userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id", userId);
                    User user = userService.getOne(queryWrapper);
                    List<UserVO> userVOList = userService.recommendUsers(user);
                    // 缓存
                    try {
                        valueOperations.set(redisKey, userVOList, 6, TimeUnit.HOURS);  // 内存淘汰
                    } catch (Exception e) {
                        log.error("redis key error: {}", e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("get lock error: {}", e);
        } finally {
            // 释放锁 --- 只能解锁自己的锁(当前线程的锁)
            if (lock.isHeldByCurrentThread()) {
                // System.out.println("unlock: " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
        // 续期： 看门狗机制
    }

}
