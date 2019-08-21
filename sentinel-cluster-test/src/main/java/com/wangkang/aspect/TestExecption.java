package com.wangkang.aspect;

import com.alibaba.csp.sentinel.node.Node;
import com.alibaba.csp.sentinel.node.OccupyTimeoutProperty;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.PriorityWaitException;
import com.alibaba.csp.sentinel.util.TimeUtil;

/**
 * Description:
 *
 * @author wangkang
 * @date: 2019-08-05
 */
public class TestExecption {
    public boolean canPass(Node node, int acquireCount, boolean prioritized) {
        int curCount = avgUsedTokens(node);
        if (curCount + acquireCount > 1) {
            if (prioritized && 12 == RuleConstant.FLOW_GRADE_QPS) {
                long currentTime;
                long waitInMs;
                currentTime = TimeUtil.currentTimeMillis();
                waitInMs = node.tryOccupyNext(currentTime, acquireCount, 1);
                if (waitInMs < OccupyTimeoutProperty.getOccupyTimeout()) {
                    node.addWaitingRequest(currentTime + waitInMs, acquireCount);
                    node.addOccupiedPass(acquireCount);
                    sleeps(waitInMs);

                    // PriorityWaitException indicates that the request will pass after waiting for {@link @waitInMs}.
                    throw new PriorityWaitException(waitInMs);
                }
            }
            return false;
        }
        return true;
    }

    private void sleeps(long waitInMs) {
    }

    private int avgUsedTokens(Node node) {
        return 0;
    }
}
