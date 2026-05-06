package com.tasks.temporal.workflow;

import java.time.Duration;

import com.tasks.temporal.activity.OrderActivity;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowMethod;

public class OrderWorkflowImp implements OrderWorkflow {

    private final OrderActivity activity = Workflow.newActivityStub(
            OrderActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(5))
                    .build());

    @WorkflowMethod
    @Override
    public String processOrder(String orderId) {
        System.out.println(" Processing workflow for order: " + orderId);
        return activity.validateOrder(orderId);
    }

}
