package com.wangkang.aspect;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-05
 */
@RestControllerAdvice
@ConditionalOnWebApplication
public class ExecptionAdvice {

    @ExceptionHandler(FlowException.class)
    public ResponseEntity FlowExecptionAdvice(FlowException e, HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(e.getRule().getResource(),"FlowExecption");

        return new ResponseEntity(e.getRule().getCount() + "--FlowExecption",
                headers, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(AuthorityException.class)
    public ResponseEntity AuthorityExceptionAdvice(AuthorityException e, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(e.getRule().getResource(),"AuthorityException");
        return new ResponseEntity(e.getRule().getLimitApp() + "--AuthorityException",
                headers, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(DegradeException.class)
    public ResponseEntity DegradeExceptionAdvice(DegradeException e, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(e.getRule().getResource(),"DegradeException");
        return new ResponseEntity(e.getRule().getCount() + "--DegradeException",
                headers, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(SystemBlockException.class)
    public ResponseEntity SystemBlockExceptionAdvice(SystemBlockException e, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(e.getRule().getResource(),"SystemBlockException");
        return new ResponseEntity(e.getRule().getLimitApp() + "--SystemBlockException",
                headers, HttpStatus.TOO_MANY_REQUESTS);
    }

//    @ExceptionHandler(ParamFlowException.class)
//    public ResponseEntity ParamFlowExceptionAdvice(ParamFlowException e, HttpServletRequest request) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(e.getRule().getResource(),"ParamFlowException");
//        return new ResponseEntity(e.getRule().getParamFlowItemList().get(0).toString() + "--ParamFlowException",
//                headers, HttpStatus.TOO_MANY_REQUESTS);
//    }
}
