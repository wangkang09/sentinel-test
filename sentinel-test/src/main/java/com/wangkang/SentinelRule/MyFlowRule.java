package com.wangkang.SentinelRule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
public class MyFlowRule {


    public  static void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule("hello");
        // set limit qps to 50
        rule.setCount(2);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);

        //csp.sentinel.web.servlet.block.page 配置 block 之后的页面
        //如果是 sentinel 提供的 web http 拦截的话，返回的东西固定死了，我们可以重写来实现别的
        // Return the block page, or redirect to another URL. ----- 只返回特定的页面，这是不行的
//        FlowRule rule0 = new FlowRule("/hello");
//        // set limit qps to 50
//        rule0.setCount(3);
//        rule0.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule0.setLimitApp("default");
//        rules.add(rule0);

        FlowRuleManager.loadRules(rules);




    }




}
