package com.wangkang.service;

import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.flow.PriorityWaitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-01
 */
public class OriginSentinelTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void origin0() throws Exception {
        OriginSentinel origin = new OriginSentinel();
        origin.origin0();
    }

    //@Test
    public void Execption() {
        throwPriority();
        System.out.println(1);
    }

    private void throwPriority() {
        throw new PriorityWaitException(1);
    }

}