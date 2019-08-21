package com.wangkang.SentinelRule;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
//上面的规则配置，都是存在内存中的。即如果应用重启，这个规则就会失效。因此我们提供了开放的接口，您可以通过实现 DataSource 接口的方式，来自定义规则的存储数据源。通常我们的建议有
    //整合动态配置系统，如 ZooKeeper、Nacos 等，动态地实时刷新配置规则
    //结合 RDBMS、NoSQL、VCS 等来实现该规则
    //配合 Sentinel Dashboard 使用

//https://github.com/alibaba/Sentinel/wiki/%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%99%E6%89%A9%E5%B1%95
public class 持久化规则 {
}
