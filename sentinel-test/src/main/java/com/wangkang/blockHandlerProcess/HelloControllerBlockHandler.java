package com.wangkang.blockHandlerProcess;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-02
 */
public class HelloControllerBlockHandler {


    public  ResponseEntity helloBlockHandler(String name,BlockException ex) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("hellokkkkkk","kkkkkkkkkkk");
        return new ResponseEntity<>("wwwwwwwww",
                headers, HttpStatus.OK);
    }
//    public static ResponseEntity helloBlockHandler(String name, BlockException ex) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("hellokkkkkk","wkkkkkkkkkk");
//        return new ResponseEntity<>("wwwwwwwww",
//                headers, HttpStatus.OK);    }
}
