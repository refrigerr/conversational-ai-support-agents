package com.example.tools;

import org.json.JSONObject;

import com.example.CustomerData;

import java.util.HashMap;
import java.util.Map;

public class ToolExecutor {

    private final Map<String, Tool> tools = new HashMap<>();


    public ToolExecutor(CustomerData customerData){
        addTool(new GetPlanTool(customerData));
    }

    private void addTool(Tool tool) {
        tools.put(tool.getName(), tool);
    }

    public String execute(String toolName, JSONObject arguments) {
        Tool tool = tools.get(toolName);
        if (tool == null) {
            return "Unknown tool: " + toolName;
        }
        return tool.execute(arguments);
    }

    public Map<String, Tool> getTools() {
        return tools;
    }
}