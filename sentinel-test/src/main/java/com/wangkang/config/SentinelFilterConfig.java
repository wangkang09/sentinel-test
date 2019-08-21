package com.wangkang.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */

//@Configuration
public class SentinelFilterConfig {

    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        System.out.println("init FilterRegistrationBean");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);

        //可以添加 originParser

        return registration;
    }
}