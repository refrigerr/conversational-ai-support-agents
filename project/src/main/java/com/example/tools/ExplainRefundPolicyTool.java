package com.example.tools;

import org.json.JSONObject;

import com.example.CustomerData;

public class ExplainRefundPolicyTool implements Tool {

    private final CustomerData customerData;

    public ExplainRefundPolicyTool(CustomerData customerData) {
        this.customerData = customerData;
    }

    @Override
    public String getName() { return "explain_refund_policy"; }

    @Override
    public String getDescription() { return "Explain the refund policy to the customer."; }

    @Override
    public JSONObject getParametersSchema() {
        return new JSONObject()
                .put("type", "object")
                .put("properties", new JSONObject())
                .put("required", new org.json.JSONArray());
    }

    @Override
    public String execute(JSONObject arguments) {
        return customerData.getRefundPolicy();
    }
}