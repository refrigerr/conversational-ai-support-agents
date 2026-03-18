package com.example.tools;

import org.json.JSONObject;

public interface Tool {
    String getName();
    String getDescription();
    JSONObject getParametersSchema();
    String execute(JSONObject arguments);
}