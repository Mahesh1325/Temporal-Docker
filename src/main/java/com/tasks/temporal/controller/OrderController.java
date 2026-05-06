package com.tasks.temporal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.temporal.workflow.OrderWorkflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.workflow.Workflow;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final WorkflowClient workflowClient;

    public OrderController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @GetMapping("/test")
    public String test() {
        return "✅ Server is running";
    }

    @GetMapping("/{id}")
    public String startOrder(@PathVariable String id) {

        OrderWorkflow workflow = workflowClient.newWorkflowStub(
                OrderWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("ORDER_TASK_QUEUE")
                        .setWorkflowId(id)
                        .build());

        WorkflowClient.start(workflow::processOrder, id);
        return "Workflow Started for order " + id;

    }

    @GetMapping("/pay/{id}")
    public String pay(@PathVariable String id) {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(
                OrderWorkflow.class, id);

                workflow.paymentReceived("PAY - " + id);
                return "Payment sent";
    }

    @GetMapping("/status/{id}")
    public String status(@PathVariable String id){
        OrderWorkflow workflow = workflowClient.newWorkflowStub(
            OrderWorkflow.class,
             id
    );

    return workflow.getStatus();

    }

}
