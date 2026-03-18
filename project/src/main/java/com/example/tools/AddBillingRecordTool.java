package com.example.tools;

import org.json.JSONObject;

import com.example.CustomerData;

public class AddBillingRecordTool implements Tool {

    private final CustomerData customerData;

    public AddBillingRecordTool(CustomerData customerData) {
        this.customerData = customerData;
    }

    @Override
    public String getName() { return "add_billing_record"; }

    @Override
    public String getDescription() { return "Add a new billing record for a customer."; }

    @Override
    public JSONObject getParametersSchema() {
        return new JSONObject()
                .put("type", "object")
                .put("properties", new JSONObject()
                        .put("customer_id", new JSONObject()
                                .put("type", "string")
                                .put("description", "The customer ID"))
                        .put("date", new JSONObject()
                                .put("type", "string")
                                .put("description", "The billing date (YYYY-MM-DD)"))
                        .put("description", new JSONObject()
                                .put("type", "string")
                                .put("description", "Description of the charge"))
                        .put("amount", new JSONObject()
                                .put("type", "number")
                                .put("description", "The amount charged")))
                .put("required", new org.json.JSONArray()
                        .put("customer_id").put("date").put("description").put("amount"));
    }

    @Override
    public String execute(JSONObject arguments) {
        String customerId = arguments.getString("customer_id");
        CustomerData.BillingRecord record = new CustomerData.BillingRecord(
                arguments.getString("date"),
                arguments.getString("description"),
                arguments.getDouble("amount")
        );
        customerData.addBillingRecord(customerId, record);
        return "Billing record added successfully for customer: " + customerId;
    }
}