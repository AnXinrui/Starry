package com.axr.starrybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/axr/starrybackend/mapper")
public class StarryBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarryBackendApplication.class, args);
    }

}
