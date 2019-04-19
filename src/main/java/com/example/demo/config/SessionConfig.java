package com.example.demo.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 300)//设置session失效时间,默认1800
public class SessionConfig {
}
