package com.wangkang.apollo;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.csp.sentinel.util.HostNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wangkang.entity.ClusterGroupEntity;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-05
 */
@Configuration
public class apolloTest {
    private static final String APP_NAME = AppNameUtil.getAppName();

    String defaultRules = "[]";
    String namespaceName = "sentinel-test1";
    String flowRuleKey = "flowRules";
    String parmaRuleKey = "paramFlowRules";
    String ClientConfigKey = "clientConfig";
    String clusterMapDataKey = "clusterMapDataKey";

    @PostConstruct
    private  void loadRules() throws Exception {

        System.out.println("init apolloTest...");

        // Register client dynamic rule data source.
        // 注册客户端 限流和热点数据规则监听：都要监听
        initDynamicRuleProperty();

        // 注册请求超时时间监听：客户端监听
        initClientConfigProperty();

        // 服务端监听它和 客户端通信(token service) 的端口：客户端监听
        initClientServerAssignProperty();

        // 注册 server 对应的数据源：服务端监听
        registerClusterRuleSupplier();

        // 服务端监听自己 和 客户端通信的端口：服务端监听，这个其实可以和 initClientServerAssignProperty 共用
        initServerTransportConfigProperty();

        // 监听 是否自己是否是 token server，如果变化就会自动转换
        initStateProperty();//内嵌模式独有
    }

    private void initDynamicRuleProperty() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespaceName,
                flowRuleKey, defaultRules, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        ReadableDataSource<String, List<ParamFlowRule>> paramRuleSource = new ApolloDataSource<>(namespaceName,
                parmaRuleKey, defaultRules, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
        }));
        ParamFlowRuleManager.register2Property(paramRuleSource.getProperty());
    }

    private void initClientConfigProperty() {
        ReadableDataSource<String, ClusterClientConfig> flowRuleDataSourceCluster = new ApolloDataSource<>(namespaceName,
                ClientConfigKey, defaultRules, source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {
        }));
        ClusterClientConfigManager.registerClientConfigProperty(flowRuleDataSourceCluster.getProperty());
    }

    private void initClientServerAssignProperty() {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)

        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new ApolloDataSource<>(namespaceName,
                clusterMapDataKey, null, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
            return Optional.ofNullable(groupList)
                    .flatMap(this::extractClientAssignment)
                    .orElse(null);
        });
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }

    private void registerClusterRuleSupplier() {
        // Register cluster flow rule property supplier which creates data source by namespace.
        // Flow rule dataId format: ${namespace}-flow-rules
        //当 server 变动的时候会触发这个
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespace,
                    flowRuleKey, defaultRules, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
            }));
            return flowRuleDataSource.getProperty();
        });
        // Register cluster parameter flow rule property supplier which creates data source by namespace.
        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<ParamFlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespace,
                    parmaRuleKey, defaultRules, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
            }));
            return flowRuleDataSource.getProperty();
        });
    }

    private void initServerTransportConfigProperty() {

        ReadableDataSource<String, ServerTransportConfig> serverTransportDs = new ApolloDataSource<>(namespaceName,
                clusterMapDataKey, defaultRules, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
            return Optional.ofNullable(groupList)
                    .flatMap(this::extractServerTransportConfig)
                    .orElse(null);
        });

        ClusterServerConfigManager.registerServerTransportProperty(serverTransportDs.getProperty());
    }

    private void initStateProperty() {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)

        ReadableDataSource<String, Integer> clusterModeDs = new ApolloDataSource<>(namespaceName,
                clusterMapDataKey, defaultRules, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
            return Optional.ofNullable(groupList)
                    .map(this::extractMode)
                    .orElse(ClusterStateManager.CLUSTER_NOT_STARTED);
        });
        ClusterStateManager.registerProperty(clusterModeDs.getProperty());
    }

    private Optional<ServerTransportConfig> extractServerTransportConfig(List<ClusterGroupEntity> groupList) {
        return groupList.stream()
                .filter(this::machineEqual)
                .findAny()
                .map(e -> new ServerTransportConfig().setPort(e.getPort()).setIdleSeconds(600));
    }

    private int extractMode(List<ClusterGroupEntity> groupList) {
        // If any server group machineId matches current, then it's token server.
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return ClusterStateManager.CLUSTER_SERVER;
        }
        // If current machine belongs to any of the token server group, then it's token client.
        // Otherwise it's unassigned, should be set to NOT_STARTED.
        boolean canBeClient = groupList.stream()
                .flatMap(e -> e.getClientSet().stream())
                .filter(Objects::nonNull)
                .anyMatch(e -> e.equals(getCurrentMachineId()));
        return canBeClient ? ClusterStateManager.CLUSTER_CLIENT : ClusterStateManager.CLUSTER_NOT_STARTED;
    }


    private Optional<ClusterClientAssignConfig> extractClientAssignment(List<ClusterGroupEntity> groupList) {
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return Optional.empty();
        }
        // Build client assign config from the client set of target server group.
        for (ClusterGroupEntity group : groupList) {
            if (group.getClientSet().contains(getCurrentMachineId())) {
                String ip = group.getIp();
                Integer port = group.getPort();
                return Optional.of(new ClusterClientAssignConfig(ip, port));
            }
        }
        return Optional.empty();
    }

    private boolean machineEqual(/*@Valid*/ ClusterGroupEntity group) {
        return getCurrentMachineId().equals(group.getMachineId());
    }
    private String getCurrentMachineId() {
        // Note: this may not work well for container-based env.
        return HostNameUtil.getIp() + SEPARATOR + TransportConfig.getRuntimePort();
    }
    private static final String SEPARATOR = "@";

}
