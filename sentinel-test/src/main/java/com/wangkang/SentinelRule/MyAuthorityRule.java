package com.wangkang.SentinelRule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;

import java.util.Collections;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
public class MyAuthorityRule {

    public static void initAuthorityRule() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("hello");
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("appA,appB");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }
}
