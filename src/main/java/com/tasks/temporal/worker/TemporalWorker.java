package com.tasks.temporal.worker;

import com.tasks.temporal.activity.OrderActivityImp;
import com.tasks.temporal.workflow.OrderWorkflowImp;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TemporalWorker {

    private final WorkflowClient client;
    private WorkerFactory factory;

    public TemporalWorker(WorkflowClient client) {
        this.client = client;
    }

    @PostConstruct
    public void startWorker() throws InterruptedException {

        System.out.println(" Starting Temporal Worker...");
        Thread.sleep(1000); 

        try {
            factory = WorkerFactory.newInstance(client);
            Worker worker = factory.newWorker("ORDER_TASK_QUEUE");

            worker.registerWorkflowImplementationTypes(OrderWorkflowImp.class);
            worker.registerActivitiesImplementations(new OrderActivityImp());

            factory.start();
            System.out.println("Temporal Worker started successfully on task queue: ORDER_TASK_QUEUE");
        } catch (Exception e) {
            System.err.println(" Failed to start Temporal Worker: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to start worker", e);
        }
    }
}