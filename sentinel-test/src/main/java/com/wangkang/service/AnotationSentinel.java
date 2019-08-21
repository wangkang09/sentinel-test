package com.wangkang.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-01
 */
public class AnotationSentinel {

    @SentinelResource(value = "resource0",blockHandler = "blockHandler0",fallback = "fallback0")
    public String resource0() {
        return "resource0";
    }

    public String blockHandler0 (Exception e) {
        return "blockHandler0";
    }

    public String fallback0 (Exception e) {
        return "fallback0";
    }
}
