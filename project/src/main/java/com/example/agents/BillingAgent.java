package com.example.agents;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.ConversationHistory;
import com.example.Message;
import com.example.OpenAiService;
import com.example.tools.Tool;
import com.example.tools.ToolExecutor;

public class BillingAgent extends Agent{

        private final ToolExecutor toolExecutor;

        public BillingAgent(OpenAiService openAiService, ConversationHistory history, ToolExecutor toolExecutor) {
        super("""
                You are a Billing Specialist. Help customers with billing questions and requests.
                You have access to tools to check plans, billing history, open refunds, and explain policies.
                The user message may contain the customer ID directly — extract it and use it in tool calls.
                Do NOT ask for the customer ID if it is already present in the user's message.
                """, openAiService, history);
        this.toolExecutor = toolExecutor;
    }


    @Override
    public String chat(String userMessage) {
        history.addUser(userMessage);
        JSONArray toolsArray = buildToolsArray();

        List<Message> messages = new ArrayList<>(buildMessages());

        while (true) {
            String rawResponse = openAiService.chatWithTools(messages, toolsArray);
            JSONObject response = new JSONObject(rawResponse);

            if (response.has("error")) {
                String errorMsg = response.getJSONObject("error").getString("message");
                System.err.println("OpenAI API error: " + errorMsg);
                return "I encountered an error processing your request. Please try again.";
            }

            JSONObject choice = response.getJSONArray("choices").getJSONObject(0);
            JSONObject message = choice.getJSONObject("message");
            String finishReason = choice.getString("finish_reason");

            if (finishReason.equals("tool_calls")) {
                JSONArray toolCalls = message.getJSONArray("tool_calls");

                // add assistant message with tool_calls to working list
                messages.add(new Message("assistant", toolCalls));

                // execute every tool call in this response
                for (int i = 0; i < toolCalls.length(); i++) {
                    JSONObject toolCall = toolCalls.getJSONObject(i);
                    String toolName = toolCall.getJSONObject("function").getString("name");
                    JSONObject arguments = new JSONObject(toolCall.getJSONObject("function").getString("arguments"));
                    String toolCallId = toolCall.getString("id");

                    String toolResult = toolExecutor.execute(toolName, arguments);
                    System.out.println("[Tool called: " + toolName + " → " + toolResult + "]");

                    messages.add(new Message("tool", toolResult, toolCallId));
                }
                // loop continues — LLM processes tool results and decides next step

            } else {
                // finish_reason is "stop" — LLM is done
                String finalResponse = message.getString("content");
                history.addAssistant(finalResponse);
                return finalResponse;
            }
        }
    }


    private JSONArray buildToolsArray() {
        JSONArray toolsArray = new JSONArray();
        for (Tool tool : toolExecutor.getTools().values()) {
            toolsArray.put(new JSONObject()
                    .put("type", "function")
                    .put("function", new JSONObject()
                            .put("name", tool.getName())
                            .put("description", tool.getDescription())
                            .put("parameters", tool.getParametersSchema())));
        }
        return toolsArray;
    }
}
