package com.wangkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
public class SentinelClusterTestApplication {

	public static void main(String[] args) {
		System.setProperty("project.name","sentinel-test1");
		System.setProperty("csp.sentinel.dashboard.server","localhost:8888");
		System.setProperty("csp.sentinel.log.use.pid","true");
		SpringApplication.run(SentinelClusterTestApplication.class, args);

	}

}
