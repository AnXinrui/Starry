package com.axr.starrybackend;

import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class StarryBackendApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        //输出当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
    }
    @Test
    void SqlTest() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", "aaa");
        long count = userService.count(queryWrapper);
        System.out.println(count);
    }
}
