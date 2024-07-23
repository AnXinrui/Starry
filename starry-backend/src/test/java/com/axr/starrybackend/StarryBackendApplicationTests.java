package com.axr.starrybackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

//@SpringBootTest
class StarryBackendApplicationTests {

    @Test
    void contextLoads() {
        //输出当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
    }

}
