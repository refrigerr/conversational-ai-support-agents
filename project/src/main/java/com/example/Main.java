package com.example;

import com.example.CustomerData.BillingRecord;
import com.example.agents.Agent;
import com.example.agents.BillingAgent;
import com.example.tools.ToolExecutor;

public class Main 
{
    public static void main( String[] args )
    {
        
        OpenAiService openAiService = new OpenAiService();
        CustomerData customerData = new CustomerData();
        ToolExecutor toolExecutor = new ToolExecutor(customerData);
        ConversationHistory conversationHistory = new ConversationHistory();
        Agent agent = new BillingAgent(openAiService, conversationHistory, toolExecutor);
        System.out.println(agent.chat("What is the billing plan for customer_002 and customer_001?"));

    }
}
