package com.wangkang.config;

import com.wangkang.SentinelRule.MyAuthorityRule;
import com.wangkang.SentinelRule.MyDegradeRule;
import com.wangkang.SentinelRule.MyFlowRule;
import com.wangkang.SentinelRule.MyParaRule;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
//@Configuration
public class RuleConfig {

    @PostConstruct
    public void init() {
        System.out.println("init RuleConfig");
        MyAuthorityRule.initAuthorityRule();
        MyDegradeRule.initDegradeRule();
        MyFlowRule.initFlowQpsRule();
        MyParaRule.initParaFlowRule();
    }
}
