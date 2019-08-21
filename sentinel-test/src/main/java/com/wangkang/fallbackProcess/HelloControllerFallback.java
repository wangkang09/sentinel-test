package com.wangkang.fallbackProcess;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
public class HelloControllerFallback {

    public static ResponseEntity helloFallback(String name, Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("hellokkkkkk","fallback--wwwwwwwwwwwwww");
        return new ResponseEntity<>("fallback--wwwwwwwwwwwwww",
                headers, HttpStatus.OK);    }
}
