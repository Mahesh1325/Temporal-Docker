package com.tasks.temporal.activity;

public class OrderActivityImp implements OrderActivity {

    @Override
    public String validateOrder(String orderId) {
        System.out.println("🔍 Validating order: " + orderId);
        return "✅ Order " + orderId + " validated successfully";
    }
}
