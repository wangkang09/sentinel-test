package com.wangkang.SentinelRule;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;

import java.util.Collections;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
public class MyParaRule {

    public  static void initParaFlowRule() {
        ParamFlowRule rule = new ParamFlowRule("hello")
                .setParamIdx(0)
                .setCount(5);
        // 针对 String 类型的参数 PARAM_B，单独设置限流 QPS 阈值为 10，而不是全局的阈值 5.
        ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf("wk"))
                .setClassType(String.class.getName())
                .setCount(0);
        rule.setParamFlowItemList(Collections.singletonList(item));

        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }
}
