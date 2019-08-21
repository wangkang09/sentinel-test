package com.wangkang.controll;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wangkang.blockHandlerProcess.HelloControllerBlockHandler;
import com.wangkang.fallbackProcess.HelloControllerFallback;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
@RestController
public class HelloController {

    //blockHandler 函数会在原方法被限流/降级/系统保护的时候调用
    //而 fallback 函数会针对所有类型的异常
    @GetMapping("/hello")
    //@SentinelResource(value = "hello",blockHandler = "helloBlockHandler",blockHandlerClass = HelloControllerBlockHandler.class,fallbackClass = HelloControllerFallback.class,fallback = "helloFallback")
    @SentinelResource(value = "hello")
    public ResponseEntity<String> hello(String name) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("hello","wk");
        return new ResponseEntity<>(name,
                headers, HttpStatus.OK);
    }

    public  ResponseEntity<String> helloBlockHandler(String name,BlockException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("hellokkkkkk","wkkkkkkkkkk");
        return new ResponseEntity<>("wkkkkkkkkkk",
                headers, HttpStatus.OK);
    }

}

