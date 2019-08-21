package com.wangkang.SentinelRule;

import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
public class MySystemRule {

    public  static void initSystemRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }
}
