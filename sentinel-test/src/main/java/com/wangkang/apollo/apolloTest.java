package com.wangkang.apollo;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-05
 */
@Configuration
public class apolloTest {

    @PostConstruct
    private  void loadRules() throws Exception {
        System.out.println("init apolloTest...");
        // Set up basic information, only for demo purpose. You may adjust them based on your actual environment.
        // For more information, please refer https://github.com/ctripcorp/apollo
//        String appId = "sentinel-test";
//        String apolloMetaServerAddress = "http://106.12.25.204:8080";
//        System.setProperty("app.id", appId);
//        System.setProperty("apollo.meta", apolloMetaServerAddress);

        String namespaceName = "sentinel-test1";

        // ------------------------------------ 限流规则
        String flowRuleKey = "flowRules";
        String defaultFlowRules = "[]";
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespaceName,
                flowRuleKey, defaultFlowRules, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        // ------------------------------------ 鉴权规则
        String defaultAuthorityRule = "[]";
        String authorityRuleKey = "authorityRules";
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = new ApolloDataSource<>(namespaceName,
                authorityRuleKey, defaultAuthorityRule, source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
        }));
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());


        // ------------------------------------ 熔断规则
        String defaultDegradeRule = "[]";
        String degradeRuleRuleKey = "degradeRules";
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new ApolloDataSource<>(namespaceName,
                degradeRuleRuleKey, defaultDegradeRule, source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
        }));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());


        // ------------------------------------ 热点参数规则
        String defaultParamFlowRule = "[]";
        String paramFlowRuleKey = "paramFlowRules";
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource = new ApolloDataSource<>(namespaceName,
                paramFlowRuleKey, defaultParamFlowRule, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
        }));
        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());


        // ------------------------------------ 系统保护规则
        String defaultSystemRule = "[]";
        String systemRuleKey = "systemRules";
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new ApolloDataSource<>(namespaceName,
                systemRuleKey, defaultSystemRule, source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
        }));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());

    }
}
