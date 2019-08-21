package com.wangkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SentinelTestApplication {

	public static void main(String[] args) {
		System.setProperty("project.name","sentinel-test1");

//		System.setProperty("csp.sentinel.dashboard.server","localhost:8888");
		SpringApplication.run(SentinelTestApplication.class, args);
	}

}
