package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerData {

    public record BillingRecord(String date, String description, double amount) {}

    private final Map<String, String> plans = Map.of(
            "customer_001", "Pro - $49/month",
            "customer_002", "Basic - $19/month",
            "customer_003", "Enterprise - $199/month"
    );

    private final Map<String, List<BillingRecord>> billingHistory = new java.util.HashMap<>(Map.of(
            "customer_001", new ArrayList<>(List.of(
                    new BillingRecord("2024-01-01", "Pro Plan", 49.00),
                    new BillingRecord("2024-02-01", "Pro Plan", 49.00),
                    new BillingRecord("2024-03-01", "Pro Plan", 49.00)
            )),
            "customer_002", new ArrayList<>(List.of(
                    new BillingRecord("2024-01-01", "Basic Plan", 19.00),
                    new BillingRecord("2024-02-01", "Basic Plan", 19.00)
            )),
            "customer_003", new ArrayList<>(List.of(
                    new BillingRecord("2024-01-01", "Enterprise Plan", 199.00)
            ))
    ));

    private final String refundPolicy = """
            Refund Policy:
            - Refunds are available within 30 days of purchase.
            - Partial refunds are available for unused portions of annual plans.
            - Refunds are processed within 5-10 business days.
            - One-time setup fees are non-refundable.
            """;

    public String getPlan(String customerId) {
        return plans.getOrDefault(customerId, "No plan found for customer: " + customerId);
    }

    public List<BillingRecord> getBillingHistory(String customerId) {
        return billingHistory.getOrDefault(customerId, List.of());
    }

    public void addBillingRecord(String customerId, BillingRecord record) {
        billingHistory.computeIfAbsent(customerId, k -> new ArrayList<>()).add(record);
    }

    public String getRefundPolicy() {
        return refundPolicy;
    }
}