package com.wangkang.SentinelRule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
public class MyDegradeRule {

    public  static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("hello");
        // set threshold RT, 300 ms
        rule.setCount(300);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
}
