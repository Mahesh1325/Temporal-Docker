package com.tasks.temporal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.temporal.workflow.OrderWorkflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;

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
        System.out.println(" Processing order request for ID: " + id);
        try {
            OrderWorkflow workflow = workflowClient.newWorkflowStub(
                    OrderWorkflow.class,
                    WorkflowOptions.newBuilder()
                            .setTaskQueue("ORDER_TASK_QUEUE")
                            .build());

            String result = workflow.processOrder(id);
            System.out.println(" Order processed successfully: " + result);
            return result;
        } catch (Exception e) {
            System.err.println(" Error processing order: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}
