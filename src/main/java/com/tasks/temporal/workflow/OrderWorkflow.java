package com.tasks.temporal.workflow;

import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.WorkflowInterface;

@WorkflowInterface
public interface OrderWorkflow {
    @WorkflowMethod
    String processOrder(String orderId);
}
