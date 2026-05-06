package com.tasks.temporal.workflow;

import java.time.Duration;

import com.tasks.temporal.activity.OrderActivity;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowMethod;

public class OrderWorkflowImp implements OrderWorkflow {

    private boolean paymentDone = false;
    private String status = "ORDER_CREATED";

    private final OrderActivity activity = Workflow.newActivityStub(
            OrderActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(5))
                    .build());

    @WorkflowMethod
    @Override
    public String processOrder(String orderId) {

        status = "WAITING_FOR_PAYMENT";

        Workflow.await(() -> paymentDone);

        status = "PAYMENT_RECEIVED";

        System.out.println(" Payment Successful Order Completed");
        
        String result = activity.validateOrder(orderId);

        status = "ORDER_COMPLETED";

        return result;
    }

    @Override
    public void paymentReceived(String paymentId){
        this.paymentDone = true;
    }

    @Override
    public String getStatus(){
        return status;
    }

}
