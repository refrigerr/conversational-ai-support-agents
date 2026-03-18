package com.example.tools;

import org.json.JSONObject;

import com.example.CustomerData;

public class GetPlanTool implements Tool {

    private final CustomerData customerData;

    public GetPlanTool(CustomerData customerData) {
        this.customerData = customerData;
    }

    @Override
    public String getName() { return "get_plan"; }

    @Override
    public String getDescription() { return "Get the current plan and pricing for a customer."; }

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
        return customerData.getPlan(customerId);
    }
}