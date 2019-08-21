package com.wangkang.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-01
 */
public class OriginSentinel {

    public String origin0() {
        //设置 context name
        ContextUtil.enter("contextName","originName");

        Entry entry = null;
        try {
            //设置 访问资源的 name
            entry = SphU.entry("resourceName");
            processResource0();
        } catch (BlockException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            //处理业务异常
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        ContextUtil.exit();

        return "origin0";
    }

    private void processResource0() {
        System.out.println("context name : " + ContextUtil.getContext().getName());
        System.out.println("resource name : " + ContextUtil.getContext().getCurEntry().getResourceWrapper().getName());
        System.out.println("context name : " + ContextUtil.getContext().getEntranceNode().avgRt());
    }
}
