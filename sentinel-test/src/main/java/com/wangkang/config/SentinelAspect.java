package com.wangkang.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
@Configuration
public class SentinelAspect {
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        System.out.println("init SentinelResourceAspect");
        return new SentinelResourceAspect();
    }
}
