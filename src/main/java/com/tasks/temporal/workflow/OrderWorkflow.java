package com.tasks.temporal.workflow;

import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.QueryMethod;


@WorkflowInterface
public interface OrderWorkflow {
    @WorkflowMethod
    String processOrder(String orderId);

    @SignalMethod
    void paymentReceived(String paymentId);

    @QueryMethod
    String getStatus();


}
