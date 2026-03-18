package com.example.tools;
import org.json.JSONObject;
import com.example.CustomerData;
import java.util.List;

public class GetBillingHistoryTool implements Tool {

    private final CustomerData customerData;

    public GetBillingHistoryTool(CustomerData customerData) {
        this.customerData = customerData;
    }

    @Override
    public String getName() { return "get_billing_history"; }

    @Override
    public String getDescription() { return "Get the billing history for a customer."; }

    @Override
    public JSONObject getParametersSchema() {
        return new JSONObject()
                .put("type", "object")
                .put("properties", new JSONObject()
                        .put("customer_id", new JSONObject()
                                .put("type", "string")
                                .put("description", "The customer ID")))
                .put("required", new org.json.JSONArray().put("customer_id"));
    }

    @Override
    public String execute(JSONObject arguments) {
        String customerId = arguments.getString("customer_id");
        List<CustomerData.BillingRecord> records = customerData.getBillingHistory(customerId);

        if (records.isEmpty()) {
            return "No billing history found for customer: " + customerId;
        }

        StringBuilder sb = new StringBuilder("Billing history:\n");
        for (CustomerData.BillingRecord record : records) {
            sb.append(String.format("- %s: %s ($%.2f)%n", record.date(), record.description(), record.amount()));
        }
        return sb.toString();
    }
}