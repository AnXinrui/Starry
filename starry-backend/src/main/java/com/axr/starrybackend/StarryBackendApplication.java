package com.axr.starrybackend;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.Optional;

@SpringBootApplication
@MapperScan("com.axr.starrybackend.mapper")
public class StarryBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarryBackendApplication.class, args);
    }

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public CookieSerializer cookieSerializer() {
        // 解决cookiePath会多一个"/" 的问题
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        String contextPath = Optional.ofNullable(serverProperties).map(ServerProperties::getServlet)
                .map(ServerProperties.Servlet::getContextPath).orElse(null);
        // 当配置了context path 时设置下cookie path ; 防止cookie path 变成 contextPath + /
        if (!StringUtils.isEmpty(contextPath)) {
            serializer.setCookiePath(contextPath);
        }
        serializer.setUseHttpOnlyCookie(true);
        serializer.setUseSecureCookie(false);
        // 干掉 SameSite=Lax
        serializer.setSameSite(null);
        return serializer;
    }
}
